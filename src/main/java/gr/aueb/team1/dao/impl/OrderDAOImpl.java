package gr.aueb.team1.dao.impl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import gr.aueb.team1.dao.OrderDAO;
import gr.aueb.team1.domain.Order;
import gr.aueb.team1.domain.User;
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

	@Override
	public Order findById(Integer id) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Order t = em.find(Order.class, id);

		tx.commit();
		return t;
	}

	@Override
	public Order save(Order order) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Order savedOrder = order;
		if (order.getId() != null) {
			savedOrder = em.merge(order);
		}
		else {
			em.persist(order);
		}
		
		tx.commit();
		
		return savedOrder;
	}
	
	@Override
	public Order delete(Order order) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		order.remove();
		em.remove(order);

		tx.commit();
		return order;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findByUser(User user) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Query q = em.createQuery("select o from Order o where Userid = :id");
        q.setParameter("id",user.getId());
		List<Order> result = q.getResultList();
		
		tx.commit();
		return result;
	}
}
