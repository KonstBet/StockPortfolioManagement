package gr.aueb.team1.service;

import java.util.List;

import gr.aueb.team1.dao.OrderDAO;
import gr.aueb.team1.dao.impl.UserDAOImpl;
import gr.aueb.team1.dao.impl.AuthorizationDAOImpl;
import gr.aueb.team1.domain.Order;
import gr.aueb.team1.domain.Stock;
import gr.aueb.team1.domain.User;
import gr.aueb.team1.domain.Broker;
import gr.aueb.team1.domain.Investor;
import gr.aueb.team1.domain.AuthCapital;
import gr.aueb.team1.domain.AuthStock;

public class OrderService {
	
    private OrderDAO od;
    private UserService us = new UserService(new UserDAOImpl());
    private AuthorizationService as = new AuthorizationService(new AuthorizationDAOImpl());
    
    public OrderService(OrderDAO od) {
        this.od = od;
    }
    
    public List<Order> getOrders(Integer userid){

        User u = us.findUserById(userid);

        List<Order> results = null;
        results = od.findByUser(u);

        return results;
    }
	    
    public Order buyStock(Integer userid, Stock stock, Integer amount) {

    	User u = us.findUserById(userid);
        Order o = u.buyStock(stock, amount);

        return od.save(o);
    }
    
    public Order sellStock(Integer userid, Stock stock, Integer amount) {

    	User u = us.findUserById(userid);
        Order o = u.sellStock(stock, amount);

        return od.save(o);
    }

    public Order limitOrder(Integer userid, Double limit, Stock stock, Integer amount, Order.Action action) {
    	User u = us.findUserById(userid);
        Order o = u.limitOrder(limit, stock, amount, action);
        
    	return od.save(o);
    }
    
    public Order buyForInvestor(Integer brokerid, Integer authid, Stock stock, Integer amount) {
    	Broker b = us.findBrokerById(brokerid);
    	AuthCapital ac = as.getAuthCapital(brokerid, authid);
    	Order o = b.buyForInvestor(ac, stock, amount);
    	
    	return od.save(o);
    }
    
    public Order sellForInvestor(Integer brokerid, Integer authid, Integer amount) {
    	Broker b = us.findBrokerById(brokerid);
    	AuthStock ast = as.getAuthStock(brokerid, authid);
    	Order o = b.sellForInvestor(ast, amount);
    	
    	return od.save(o);
    }

    
}
