package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.AuthorizationDAO;
import gr.aueb.team1.domain.*;
import gr.aueb.team1.persistence.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class AuthorizationDAOImpl implements AuthorizationDAO {

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
    public Authorization findById(Integer id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Authorization t = em.find(Authorization.class,id);

        tx.commit();
        return t;
    }

    @Override
    public AuthStock findStockAuthById(Integer id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        AuthStock t = em.find(AuthStock.class,id);

        tx.commit();
        return t;
    }

    @Override
    public AuthCapital findCapitalAuthById(Integer id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        AuthCapital t = em.find(AuthCapital.class,id);

        tx.commit();
        return t;
    }

    @Override
    public Authorization save(Authorization auth) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Authorization savedAuth = auth;
        if (auth.getId() != null) {
            savedAuth = em.merge(auth);
        }
        else {
            em.persist(auth);
        }

        tx.commit();

        return savedAuth;
    }
    
	@Override
	public Authorization delete(Authorization auth) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

        auth.removeAuth();
		em.remove(auth);

		tx.commit();
		return auth;
	}

    @SuppressWarnings("unchecked")
    @Override
	public List<Authorization> findAllByBrokerID(Integer id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createQuery("select a from Authorization a where Brokerid = :brokerid");
        q.setParameter("brokerid",id);
        List<Authorization> result = q.getResultList();


        tx.commit();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
	public List<Authorization> findAllByInvestorID(Integer id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createQuery("select a from Authorization a where Investorid = :investorid");
        q.setParameter("investorid",id);
        List<Authorization> result = q.getResultList();


        tx.commit();
        return result;
    }
}
