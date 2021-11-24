package gr.aueb.team1.dao.impl;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import gr.aueb.team1.dao.DAO;
import gr.aueb.team1.domain.Order;
import gr.aueb.team1.domain.User;
import gr.aueb.team1.persistence.JPAUtil;

public class UserDAOImpl implements DAO<User> {

	private EntityManager em;
	
	public UserDAOImpl() {
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

	public User findById(Integer id) {
		return null;
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
	
	@Override
	public User delete(User user) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		
		int id = user.getId();
		Query q = em.createQuery("delete from User u where u.id = :id");
		q.setParameter("id", id);
		q.executeUpdate();

		tx.commit();
		return user;
	}


	public User findByEmail(String email) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Query query = em.createQuery("select u from User u where email = :email");
		query.setParameter("email",email);

		User user = null;

		try {
			user = (User) query.getSingleResult();
		} catch(NoResultException e) {
			tx.rollback();
			return null;
		}

		tx.commit();
		return user;
	}

	public User findByID(Integer id) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Query query = em.createQuery("select u from User u where id = :id");
		query.setParameter("id",id);

		User user = null;

		try {
			user = (User) query.getSingleResult();
		} catch(NoResultException e) {
			tx.rollback();
			return null;
		}

		tx.commit();
		return user;
	}
}
