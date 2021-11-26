package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.StockDAO;
import gr.aueb.team1.domain.Authorization;
import gr.aueb.team1.domain.Stock;
import gr.aueb.team1.domain.Transaction;
import gr.aueb.team1.domain.User;
import gr.aueb.team1.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import java.util.List;

public class StockDAOImpl implements StockDAO {

    private EntityManager em;

    public StockDAOImpl() {
        em = JPAUtil.getCurrentEntityManager();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Stock> findAll() {
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Query q = em.createQuery("select s from Stock s");
		List<Stock> result = q.getResultList();
		
		tx.commit();
		
		return result;
	}
	
    public Stock findById(Integer id) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Stock t = em.find(Stock.class,id);

		tx.commit();
		return t;
    }
    
    @Override
    public Stock save(Stock t) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Stock savedStock = t;
        if (t.getId() != null) {
            savedStock = em.merge(t);
        } else {
            em.persist(t);
        }

        tx.commit();

        return savedStock;
    }
    
	@Override
	public Stock delete(Stock stock) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		stock.remove();
		em.remove(stock);

		tx.commit();
		return stock;
	}

}
