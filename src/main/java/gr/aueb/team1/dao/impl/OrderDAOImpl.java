package gr.aueb.team1.dao.impl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import gr.aueb.team1.dao.DAO;
import gr.aueb.team1.dao.OrderDAO;
import gr.aueb.team1.domain.Authorization;
import gr.aueb.team1.domain.Order;
import gr.aueb.team1.persistence.JPAUtil;

public class OrderDAOImpl implements OrderDAO {

	private EntityManager em;
	
	public OrderDAOImpl() {
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

	public Order findById(Integer id) {
		return null;
	}

	@Override
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
	
	@Override
	public Order delete(Order order) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		
		int id = order.getId();
		Query q = em.createQuery("delete from User u where u.id = :id");
		q.setParameter("id", id);
		q.executeUpdate();

		tx.commit();
		return order;
	}
}
