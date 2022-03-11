package gr.aueb.team1.dao;

import gr.aueb.team1.dao.impl.*;
import gr.aueb.team1.domain.*;
import gr.aueb.team1.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;

public class Initializer {

    public Stock PeiraiosStock;
    public Stock AlphaStock;
    public Stock CosmoteStock;
    public Investor investor;
    public Broker broker;
    public Broker broker2;
    public AuthCapital ac;
    public AuthStock as;

    private void eraseData() {
        EntityManager em = JPAUtil.getCurrentEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.createNativeQuery("delete from Transactions").executeUpdate();
        em.createNativeQuery("delete from orders").executeUpdate();
        em.createNativeQuery("delete from Authorizations").executeUpdate();
        em.createNativeQuery("delete from StockHolding").executeUpdate();
        em.createNativeQuery("delete from Users").executeUpdate();
        em.createNativeQuery("delete from stocks").executeUpdate();

        tx.commit();
        em.close();
    }

    public void prepareData() {
        eraseData();

        PeiraiosStock = new Stock("PIRAEUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
        AlphaStock = new Stock("ALPHA", LocalDateTime.now(), 100.00, 200.99, 1000.00, 10.00, 2460.00);
        CosmoteStock = new Stock("COSMOTE", LocalDateTime.now(), 29.00, 200.99, 1000.00, 10.00, 2460.00);

        investor = new Investor("Mitsos", "Charalampidis", "mitcharal@gmail.com", "6978910301", "b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");
        investor.deposit(10000.00);

        broker = new Broker("Stefanos", "Daglis", "macharal@gmail.com", "6978910301", 0.0, "b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");
        broker2 = new Broker("John", "Charalampous", "mincharal@gmail.com", "6978910301", 0.0, "b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");

        Integer amount = 20;
        investor.buyStock(PeiraiosStock,amount);
        investor.buyStock(AlphaStock,amount);

        as = investor.giveAuthorization(20,investor.getStockHoldings().get(PeiraiosStock),broker,LocalDateTime.now());
        ac = investor.giveAuthorization(5000.0,broker2, LocalDateTime.now());

        broker.sellForInvestor((AuthStock) broker.getAuthorizations().iterator().next(),10);
        broker2.buyForInvestor((AuthCapital) broker2.getAuthorizations().iterator().next(),PeiraiosStock,10);


        StockDAO stockDAO = getStockDAO();
        stockDAO.save(PeiraiosStock);
        stockDAO.save(AlphaStock);
        stockDAO.save(CosmoteStock);
        UserDAO userDAO = getUserDAO();
        userDAO.save(investor);
        userDAO.save(broker);
        userDAO.save(broker2);
    }

    private UserDAO getUserDAO() {
        return new UserDAOImpl();
    }
    private StockDAO getStockDAO() {
        return new StockDAOImpl();
    }
//    private TransactionDAO getTransactionDAO() {
//        return new TransactionDAOImpl();
//    }
//    private AuthorizationDAO getAuthorizationDAO() {
//        return new AuthorizationDAOImpl();
//    }
//    private OrderDAO getOrderDAO() {
//        return new OrderDAOImpl();
//    }
//    private StockHoldingDAO getStockHoldingDAO() {
//        return new StockHoldingDAOImpl();
//    }
}
