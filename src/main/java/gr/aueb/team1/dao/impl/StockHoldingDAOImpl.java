package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.StockHoldingDAO;
import gr.aueb.team1.domain.Stock;
import gr.aueb.team1.domain.StockHolding;
import gr.aueb.team1.persistence.JPAUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class StockHoldingDAOImpl implements StockHoldingDAO {

	private EntityManager em;
	
	public StockHoldingDAOImpl() {
		em = JPAUtil.getCurrentEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockHolding> findAll() {
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Query q = em.createQuery("select sh from StockHolding sh");
		List<StockHolding> result = q.getResultList();
		
		tx.commit();
		
		return result;
	}
	
    public StockHolding findById(Integer id) {
        return null;
    }
    
    @Override
    public StockHolding save(StockHolding sh) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        StockHolding savedStockHolding = sh;
        if (sh.getId() != null) {
        	savedStockHolding = em.merge(sh);
        } else {
            em.persist(sh);
        }

        tx.commit();

        return savedStockHolding;
    }
    
	@Override
	public StockHolding delete(StockHolding sh) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		
		int id = sh.getId();
		Query q = em.createQuery("delete from StockHolding sh where sh.id = :id");
		q.setParameter("id", id);
		q.executeUpdate();

		tx.commit();
		
		return sh;
	}
}
