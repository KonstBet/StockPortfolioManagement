package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.StockDAO;
import gr.aueb.team1.domain.Stock;
import gr.aueb.team1.domain.Transaction;
import gr.aueb.team1.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class StockDAOImpl implements StockDAO {

    private EntityManager em;

    public StockDAOImpl() {
        em = JPAUtil.getCurrentEntityManager();
    }

    public List<Stock>  findAll() {
        return null;
    }
    public Stock findById(Integer id) {
        return null;
    }
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
    public Stock delete(Stock t) {
        return null;
    }
}
