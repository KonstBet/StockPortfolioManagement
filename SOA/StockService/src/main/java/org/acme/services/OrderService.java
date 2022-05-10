package org.acme.services;

import org.acme.domain.*;
import org.acme.repositories.OrderRepository;
import org.acme.repositories.StockHoldingRepository;
import org.acme.repositories.StockRepository;
import org.acme.resources.OrderDTO;
import org.acme.resources.WalletDTO;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    @Inject
    StockHoldingRepository stockHoldingRepository;

    @Inject
    StockRepository stockRepository;

    @Inject @RestClient AuthorizationService authorizationService;
    @Inject @RestClient WalletService walletService;


    public List<OrderDTO> getOrders(Long userId){

        List<Order> orders;
        orders = orderRepository.listUserOrders(userId);
        if(orders == null) return null;

        return OrderDTO.listToDTOList(orders);
    }

    public List<OrderDTO> getPurchaseOrders(Long userId){

        List<Order> orders;

        orders = orderRepository.listUserPurchaseOrders(userId);

        if(orders == null) return null;

        return OrderDTO.listToDTOList(orders);
    }

    public List<OrderDTO> getSaleOrders(Long userId){

        List<Order> orders;

        orders = orderRepository.listUserSaleOrders(userId);

        if(orders == null) return null;

        return OrderDTO.listToDTOList(orders);
    }

    public OrderDTO getOrderById(Long id){
        Order order = orderRepository.findByPk(id);
        if(order == null) return null;
        return new OrderDTO(order);
    }

    @CircuitBreaker(requestVolumeThreshold = 4, delay = 5000,
            successThreshold = 2)
    public Boolean createOrder(OrderDTO orderDTO){

        System.out.println("started order");
        Order order = new Order();
        double fee = 0.0;
        boolean executingAsBroker = verifyBrokerAuthorization(orderDTO);
        System.out.println("executing as broker?" + executingAsBroker);

        if(orderDTO.getBrokerId() != null && !executingAsBroker) return false;

        System.out.println("Finding stock" + orderDTO.getStockId());
        // find stock by its id
        Stock stock = stockRepository.findByPk(orderDTO.getStockId());


        // if stock is invalid, return false
        if( stock == null ) return false;
        System.out.println("stock is: " + stock.getCompanyName());


        // calculate Cost of order.
        double cost = calculateCost(orderDTO, stock);


        System.out.println("cost is: " + cost);
        // if we are selling stock, verify we have enough quantity to sell!
        if(orderDTO.getType() == OrderType.SALE && !hasEnoughStockToSell(orderDTO)) return false;

        WalletDTO investorWalletDTO = getUserWallet(orderDTO.getInvestorId());
        WalletDTO brokerWalletDTO = getUserWallet(orderDTO.getBrokerId());

        if(executingAsBroker){
            // Calculate order fee.
            fee = cost * 1 / 100;
            brokerWalletDTO.setBalance(brokerWalletDTO.getBalance() + fee);

            // update order details
            order.setBrokerId(orderDTO.getBrokerId());
            order.setFee(fee);
        }

        // not enough wallet balance to make a purchase order
        if(orderDTO.getType() == OrderType.PURCHASE && cost > investorWalletDTO.getBalance()) return false;

        // update investor's wallet DTO (lower money when purchasing, add when selling)
        if(orderDTO.getType() == OrderType.PURCHASE){
            investorWalletDTO.setBalance(investorWalletDTO.getBalance() - (cost + fee));
        }else{
            investorWalletDTO.setBalance(investorWalletDTO.getBalance() + (cost - fee));
        }
        // create order details
        order.setOrderPrice(cost);
        order.setInvestorId(orderDTO.getInvestorId());
        order.setDate(LocalDateTime.now());
        order.setStock(stock);
        order.setStatus(OrderStatus.COMPLETED);
        order.setStockAmount(orderDTO.getStockAmount());
        order.setType(orderDTO.getType());

        // save order and update wallet balances on micro-service
        if(orderRepository.saveOrder(order)){
            Response response = walletService.update(investorWalletDTO);
            // if purchasing as broker, give broker the transaction fee!
            if(executingAsBroker) walletService.update(brokerWalletDTO);
        }
        System.out.println("updating stockholdings...");
        return updateStockHoldings(orderDTO, stock);
    }


    /**
     * PRIVATE HELPERS FOR ORDERS
     */


    private WalletDTO getUserWallet(Long userId){
        if(userId == null) return null;
        Response response = walletService.getUserWallet(userId);
        return response.readEntity(WalletDTO.class);
    }

    private double calculateCost(OrderDTO orderDTO, Stock stock){
        // calculate cost depending on order type
        if(orderDTO.getType() == OrderType.PURCHASE){
            return  orderDTO.getStockAmount() * stock.getHigh();
        }

        return orderDTO.getStockAmount() * stock.getLow();

    }

    private boolean hasEnoughStockToSell(OrderDTO orderDTO){

        StockHolding stockHolding =
                stockHoldingRepository.findByUserAndStockId(orderDTO.getInvestorId(), orderDTO.getStockId());

        // if no stockholding, we definitely do not have enough stock to sell
        if(stockHolding == null) return false;


        // if we have more (or equal) amount, we can sell!
        return stockHolding.getAmount() >= orderDTO.getStockAmount();
    }

    private boolean updateStockHoldings(OrderDTO orderDTO, Stock stock){

        StockHolding stockHolding =
                stockHoldingRepository.findByUserAndStockId(orderDTO.getInvestorId(), orderDTO.getStockId());

        // if we are selling, remove specified amount of stockholdings for user
        if(orderDTO.getType() == OrderType.SALE){
            stockHolding.setAmount(stockHolding.getAmount() - orderDTO.getStockAmount());
            return stockHoldingRepository.saveStockHolding(stockHolding);
        }
        // purchasing stock
        // first case: we didn't have any stockholdings for this particular stock id
        // second case: we already  have some stockholdings, add more to the amount we have!
        if(stockHolding == null){
            stockHolding = new StockHolding(orderDTO.getStockAmount(), stock, orderDTO.getInvestorId());
        }else{
            stockHolding.setAmount(stockHolding.getAmount() + orderDTO.getStockAmount());
        }
        return stockHoldingRepository.saveStockHolding(stockHolding);
    }

    // Private helper to authorize broker!
    private boolean verifyBrokerAuthorization(OrderDTO orderDTO){
        // verify broker has the rights to do this action
        if(orderDTO.getBrokerId() != null){
            Response response = authorizationService.verifyLink(orderDTO.getInvestorId(), orderDTO.getBrokerId());
            // return false to unauthorized or failed requests
            if(response.getStatus() != 200) return false;
            return true;
        }
        return false;
    }

}
