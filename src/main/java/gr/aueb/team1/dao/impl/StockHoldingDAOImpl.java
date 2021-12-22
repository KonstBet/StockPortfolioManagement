package gr.aueb.team1.dao.impl;

import java.util.List;
import gr.aueb.team1.dao.StockHoldingDAO;
import gr.aueb.team1.domain.StockHolding;
import gr.aueb.team1.domain.User;
import gr.aueb.team1.persistence.JPAUtil;
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
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		StockHolding t = em.find(StockHolding.class,id);

		tx.commit();
		return t;
    }
    
    @Override
    public StockHolding save(StockHolding sh) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        StockHolding savedStockHolding = sh;
        if (sh.getId() != null) {
        	savedStockHolding = em.merge(sh);
        }
//		else {
//            em.persist(sh);
//        }

        tx.commit();

        return savedStockHolding;
    }
    
	@Override
	public StockHolding delete(StockHolding sh) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		sh.remove();
		em.remove(sh);

		tx.commit();
		
		return sh;
	}
	
    @SuppressWarnings("unchecked")
	@Override
	public List<StockHolding> findByUser(User user) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Query q = em.createQuery("select sh from StockHolding sh where Userid = :id");
        q.setParameter("id",user.getId());
		List<StockHolding> result = q.getResultList();
		
		tx.commit();
		return result;
	}
}
