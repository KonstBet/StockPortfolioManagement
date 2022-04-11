package org.acme.services;

import org.acme.domain.Order;
import org.acme.domain.Stock;
import org.acme.repositories.OrderRepository;
import org.acme.repositories.StockRepository;
import org.acme.resources.OrderDTO;
import org.acme.resources.StockDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;


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
}
