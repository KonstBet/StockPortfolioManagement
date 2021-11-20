package gr.aueb.team1.dao.impl;
import java.util.List;
import java.util.Objects;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import gr.aueb.team1.dao.DAO;
import gr.aueb.team1.domain.User;
import gr.aueb.team1.persistence.JPAUtil;

public abstract class UserDAO implements DAO<User> {

	private EntityManager em;
	
	public UserDAO() {
		em = JPAUtil.getCurrentEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Query q = em.createQuery("select u from User u");
		List<User> result = q.getResultList();
		
		tx.commit();
		
		return result;
	}
	
	@Override
	public User save(User user) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		User savedUser = user;
		if (user.getId() != null) {
			savedUser = em.merge(user);
		} else {
			em.persist(user);
		}
		
		tx.commit();
		
		return savedUser;
	}
	
	public void update(User user, String[] params) {
//		EntityTransaction tx = em.getTransaction();
//		tx.begin();
//		
//		User savedUser = user;
//		if (user.getId() != null) {
//			
//		} else {
//			user.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
//			user.setSurname(params[1]);
//			user.
//			savedUser = em.merge(user);
//		}
//		
//		tx.commit();
//		
//		return savedUser;
	}
	
	public void delete(User user) {
//		EntityTransaction tx = em.getTransaction();
//		tx.begin();
//		
//		int id = user.getId();
//		Query q = em.createQuery("delete from User u where u.id = :id");
//		q.executeUpdate();
//
//		tx.commit();
//		
	}
}
