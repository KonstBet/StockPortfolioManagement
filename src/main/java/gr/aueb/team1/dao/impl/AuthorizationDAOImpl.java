package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.DAO;
import gr.aueb.team1.domain.*;
import gr.aueb.team1.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationDAOImpl implements DAO<Authorization> {

    private EntityManager em;

    public AuthorizationDAOImpl() {
        em = JPAUtil.getCurrentEntityManager();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Authorization> findAll() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createQuery("select a from Authorization a");
        List<Authorization> result = q.getResultList();

        tx.commit();

        return result;
    }

    @Override
    public Authorization save(Authorization auth) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Authorization savedAuth = auth;
        if (auth.getId() != null) {
            savedAuth = em.merge(auth);
        } else {
            em.persist(auth);
        }

        tx.commit();

        return savedAuth;
    }
    
	@Override
	public Authorization delete(Authorization auth) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		
		int id = auth.getId();
		Query q = em.createQuery("delete from User u where u.id = :id");
		q.setParameter("id", id);
		q.executeUpdate();

		tx.commit();
		return auth;
	}

    public List<Authorization> findAllByBrokerID(Integer id) {
        UserDAOImpl userDAO = new UserDAOImpl();
        User user = userDAO.findByID(id);

        if (user instanceof Broker) {
            List<Authorization> list = new ArrayList<Authorization>();
            list.addAll(((Broker) user).getAuthorizations());
            return list;
        }

        return null;
    }

    public List<Authorization> findAllByInvestorID(Integer id) {
        UserDAOImpl userDAO = new UserDAOImpl();
        User user = userDAO.findByID(id);

        if (user instanceof Investor) {
            List<Authorization> list = new ArrayList<Authorization>();
            list.addAll(((Investor) user).getAuthorizations());
            return list;
        }

        return null;
    }
}
