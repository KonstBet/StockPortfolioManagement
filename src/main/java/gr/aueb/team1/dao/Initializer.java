package gr.aueb.team1.dao;

import gr.aueb.team1.dao.impl.*;
import gr.aueb.team1.domain.Broker;
import gr.aueb.team1.domain.Investor;
import gr.aueb.team1.domain.Stock;
import gr.aueb.team1.domain.StockHolding;
import gr.aueb.team1.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;

public class Initializer {

    private void eraseData() {
        EntityManager em = JPAUtil.getCurrentEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.createNativeQuery("delete from Users").executeUpdate();
        em.createNativeQuery("delete from Transactions").executeUpdate();
        em.createNativeQuery("delete from orders").executeUpdate();
        em.createNativeQuery("delete from stocks").executeUpdate();
        em.createNativeQuery("delete from StockHolding").executeUpdate();
        em.createNativeQuery("delete from Users").executeUpdate();

        tx.commit();
        em.close();
    }

    public void prepareData() {
        eraseData();

        Investor investor = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
        investor.setBalance(500.00);
        Broker broker = new Broker("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100",0.0);
        Broker broker2 = new Broker("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100",0.0);

        Stock PeiraiosStock = new Stock("P200", "PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
        Stock AlphaStock = new Stock("P224", "ALPHA", LocalDateTime.now(), 100.00, 200.99, 1000.00, 10.00, 2460.00);
        Stock CosmoteStock = new Stock("P104", "COSMOTE", LocalDateTime.now(), 29.00, 200.99, 1000.00, 10.00, 2460.00);
        Integer amount = 20;
        StockHolding sh = new StockHolding(amount, PeiraiosStock, investor);
        investor.addStockHolding(PeiraiosStock, sh);
        StockHolding sh2 = new StockHolding(amount, AlphaStock, investor);
        investor.addStockHolding(AlphaStock, sh);

        LocalDateTime date1 = LocalDateTime.of(2021,12,31,0,0,0);
        LocalDateTime date2 = LocalDateTime.of(2021,12,31,0,0,0);


    }

    private UserDAO getUserDAO() {
        return new UserDAOImpl();
    }
    private TransactionDAO getTransactionDAO() {
        return new TransactionDAOImpl();
    }
    private AuthorizationDAO getAuthorizationDAO() {
        return new AuthorizationDAOImpl();
    }
    private OrderDAO getOrderDAO() {
        return new OrderDAOImpl();
    }
    private StockHoldingDAO getStockHoldingDAO() {
        return new StockHoldingDAOImpl();
    }
    private StockDAO getStockDAO() {
        return new StockDAOImpl();
    }
}
