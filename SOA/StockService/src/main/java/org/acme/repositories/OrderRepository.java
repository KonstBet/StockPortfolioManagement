package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.Order;
import org.acme.domain.OrderType;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public Order findByPk(Long id) {
        return findById(id);
    }

    public List<Order> listUserOrders(Long userId){
        return list("userId", userId);
    }

    public List<Order> listUserPurchaseOrders(Long userId){
        return list("userId = ?1 and type = ?2", userId, OrderType.PURCHASE);
    }

    public List<Order> listUserSaleOrders(Long userId){
        return list("userId = ?1 and type = ?2", userId, OrderType.SALE);
    }

    public Boolean saveOrder(Order order) {
        persist(order);
        return isPersistent(order);
    }
}
