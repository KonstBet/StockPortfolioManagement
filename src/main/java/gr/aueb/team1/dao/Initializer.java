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

        PeiraiosStock = new Stock("PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
        AlphaStock = new Stock("ALPHA", LocalDateTime.now(), 100.00, 200.99, 1000.00, 10.00, 2460.00);
        CosmoteStock = new Stock("COSMOTE", LocalDateTime.now(), 29.00, 200.99, 1000.00, 10.00, 2460.00);

        investor = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
        investor.deposit(10000.00);

        broker = new Broker("Manos", "Charalampidis", "mcharal@gmail.com", "697891030100",0.0);
        broker2 = new Broker("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100",0.0);

        Integer amount = 20;
        investor.buyStock(PeiraiosStock,amount);
        investor.buyStock(AlphaStock,amount);
//        StockHolding sh = new StockHolding(amount, PeiraiosStock, investor);
//        investor.addStockHolding(PeiraiosStock, sh);
//        StockHolding sh2 = new StockHolding(amount, AlphaStock, investor);
//        investor.addStockHolding(AlphaStock, sh);

//        LocalDateTime date1 = LocalDateTime.of(2021,12,31,0,0,0);
//        LocalDateTime date2 = LocalDateTime.of(2021,12,31,0,0,0);

        investor.giveAuthorization(20,investor.getStockHoldings().get(PeiraiosStock),broker,LocalDateTime.now());
        investor.giveAuthorization(5000.0,broker2, LocalDateTime.now());

//        broker2.buyForInvestor((AuthCapital) broker2.getAuthorizations().iterator().next(),PeiraiosStock,10);
        broker.sellForInvestor((AuthStock) broker.getAuthorizations().iterator().next(),10);


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
