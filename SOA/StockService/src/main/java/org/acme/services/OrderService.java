package org.acme.services;

import org.acme.domain.Order;
import org.acme.repositories.OrderRepository;
import org.acme.resources.OrderDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    @RestClient
    AuthorizationService authorizationService;


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

    public Boolean purchaseStock(OrderDTO orderDTO){

        Order order = new Order();

        if(orderDTO.getInvestorId() != null){

            Response response = authorizationService.verifyLink(orderDTO.getInvestorId(), orderDTO.getBrokerId());

            // return false to unauthorized or failed requests
            if(response.getStatus() != 200) return false;


        }

        return orderRepository.saveOrder(order);
    }

}
