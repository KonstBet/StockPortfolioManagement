package gr.aueb.team1.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gr.aueb.team1.domain.Order.Action;
import gr.aueb.team1.domain.Order.Status;
import java.time.LocalDateTime;
import java.util.HashSet;

public class BrokerTest {
    private Investor investor;
    private Broker broker;
    private Broker broker1;
    private Stock PeiraiosStock;
    private Stock CosmoteStock;
    private Integer amount;
    private StockHolding sh;
    private LocalDateTime date1;

    @BeforeEach
    void setUpTests() {
        investor = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
        investor.setBalance(500.00);
        broker = new Broker("Giorgos", "Charalampopoulos", "mcharal@gmail.com", "697891030100",0.0);
        broker1 = new Broker();
        

        PeiraiosStock = new Stock("P200", "PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
        CosmoteStock = new Stock("P104", "COSMOTE", LocalDateTime.now(), 29.00, 200.99, 1000.00, 10.00, 2460.00);
        amount = 10;
        sh = new StockHolding(amount, PeiraiosStock, investor);
        investor.addStockHolding(PeiraiosStock, sh);

        date1 = LocalDateTime.of(2021,12,31,0,0,0);
    }

    @Test //set Authorization, remove Authorization
    void setRemoveAuthorization() {     
        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthCapital ac = new AuthCapital(investor, broker, LocalDateTime.now(), date1, 100.0);
        auths.add(ac);
        broker.setAuthorizations(auths);
        Boolean flag1 = investor.getAuthorizations().size() == 1;
        broker.removeAuthorization(ac);
        Boolean flag2 = investor.getAuthorizations().size() == 0;
        Assertions.assertTrue(flag1 && flag2);
    }
    
    
    @Test //Valid order
    void BuyStocksForInvestorTest() {
        investor.giveAuthorization(500.00,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthCapital ac = (AuthCapital) auths.iterator().next();

        boolean flag = broker.buyForInvestor(ac, PeiraiosStock, 10);
       
        Assertions.assertTrue(flag);
    }

    @Test //Not enough committed balance
    void BuyStocksForInvestorTest2() {
        investor.giveAuthorization(100.00,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthCapital ac = (AuthCapital) auths.iterator().next();

        boolean flag = broker.buyForInvestor(ac, PeiraiosStock, 10);

        Assertions.assertFalse(flag);
    }
    
    @Test //Valid sell order
    void SellStockForInvestorTest() {
    	investor.giveAuthorization(5, sh, broker, date1);
    	HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
    	AuthStock as = (AuthStock) auths.iterator().next();
    	
    	boolean flag = broker.sellForInvestor(as, 4);
    	Assertions.assertTrue(flag);    	
    }
    
    @Test //Not enough committed stock amount
    void SellStockForInvestorTest2() {
    	investor.giveAuthorization(5, sh, broker, date1);
    	HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
    	AuthStock as = (AuthStock) auths.iterator().next();
    	
    	boolean flag = broker.sellForInvestor(as, 6);
    	Assertions.assertFalse(flag);    	
    }
    
    @Test //Check brokerage fee
    void SellStockForInvestorTest3() {
    	investor.giveAuthorization(5, sh, broker, date1);
    	broker.setBrokerageFee(5.0);
    	HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
    	AuthStock as = (AuthStock) auths.iterator().next();
    	broker.sellForInvestor(as, 1);
    	boolean flag = broker.getBalance() == 0.2;
    	Assertions.assertTrue(flag);    	

    }
    

}
