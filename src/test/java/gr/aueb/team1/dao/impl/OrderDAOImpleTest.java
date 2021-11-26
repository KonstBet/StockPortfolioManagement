package gr.aueb.team1.dao.impl;


import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.dao.OrderDAO;
import gr.aueb.team1.domain.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDAOImpleTest {
    
    private OrderDAO orderDAO;
    Initializer init;

    @BeforeEach
    void Initialize() {
        init = new Initializer();
        init.prepareData();

        orderDAO = new OrderDAOImpl();
    }

    @Test
    void findAll() {
        List<Order> orders = orderDAO.findAll();
        Assertions.assertEquals(4,orders.size());
    }

    @Test
    void findById() {
        Order order = orderDAO.findById(init.investor.getOrders().iterator().next().getId());
        Assertions.assertNotNull(order);
    }

    @Test
    void save() {
        Order order = orderDAO.findById(init.investor.getOrders().iterator().next().getId());
        LocalDateTime date = LocalDateTime.now();
        order.setDate(date);

        orderDAO.save(order);
        order = orderDAO.findById(init.investor.getOrders().iterator().next().getId());

        Assertions.assertTrue(order.getDate().isEqual(date));
    }

    @Test
    void delete() {
        Order order = orderDAO.findById(init.investor.getOrders().iterator().next().getId());

        orderDAO.delete(order);
        List<Order> orders = orderDAO.findAll();
        Assertions.assertEquals(3,orders.size());
    }
    
    @Test
    void findByUser() {
    	List<Order> s = orderDAO.findByUser(init.investor);
    	assertEquals(init.investor.getOrders().size(),s.size());
    }
}
