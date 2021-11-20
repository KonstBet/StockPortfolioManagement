package gr.aueb.team1.dao.impl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import gr.aueb.team1.dao.DAO;
import gr.aueb.team1.domain.Order;
import gr.aueb.team1.domain.User;
import gr.aueb.team1.persistence.JPAUtil;

public class OrderDAO implements DAO{

	private EntityManager em;
	
	public OrderDAO() {
		em = JPAUtil.getCurrentEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findAll() {
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Query q = em.createQuery("select o from Order o");
		List<Order> result = q.getResultList();
		
		tx.commit();
		
		return result;
	}
	
	
	public Order save(Order order) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Order savedOrder = order;
		if (order.getId() != null) {
			savedOrder = em.merge(order);
		} else {
			em.persist(order);
		}
		
		tx.commit();
		
		return savedOrder;
	}
}
