package gr.aueb.team1.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class InvestorTest {
    private Investor investor;
    private Broker broker;
    private Broker broker2;
    private Stock PeiraiosStock;
    private Stock AlphaStock;
    private Integer amount;
    private StockHolding sh;
    private StockHolding sh2;
    private LocalDateTime date1;
    private LocalDateTime date2;

    @BeforeEach
    void setUpTests() {
        investor = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100", "b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");
        investor.setBalance(500.00);
        broker = new Broker("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100", 0.0, "b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");
        broker2 = new Broker("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100", 0.0, "b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");

        PeiraiosStock = new Stock("PIRAEUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
        AlphaStock = new Stock("ALPHA", LocalDateTime.now(), 100.00, 200.99, 1000.00, 10.00, 2460.00);
        amount = 20;
        sh = new StockHolding(amount, PeiraiosStock, investor);
        investor.addStockHolding(PeiraiosStock, sh);
        sh2 = new StockHolding(amount, AlphaStock, investor);
        investor.addStockHolding(AlphaStock, sh);

        date1 = LocalDateTime.of(2021,12,31,0,0,0);
        date2 = LocalDateTime.of(2021,12,31,0,0,0);
    }

    @Test
    void giveCapitalAuthorizationTest() {
        AuthCapital flag = investor.giveAuthorization(500.00,broker,date1);

        Assertions.assertTrue(flag!=null && investor.getCommittedBalance() == 500);
    }

    @Test
    void BrokengiveCapitalAuthorizationTest() {
        AuthCapital flag = investor.giveAuthorization(501.00,broker,date1);

        Assertions.assertNull(flag);
    }

    @Test
    void removeCapitalAuthorizationTest() {
        AuthCapital flag = investor.giveAuthorization(500.00,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthCapital ac = (AuthCapital) auths.iterator().next();

        boolean flag2 = investor.removeAuthorization(ac);

        Assertions.assertTrue(flag!=null && flag2 && investor.getCommittedBalance() == 0
                && investor.getAuthorizations().size() == 0 && broker.getAuthorizations().size() == 0);
    }

    @Test
    void removeCapitalAuthorizationTest2() {
        AuthCapital ac = new AuthCapital(investor, broker, date1, date2, 10.0);

        boolean flag = investor.removeAuthorization(ac);

        Assertions.assertFalse(flag);
    }

    @Test
    void giveStocksAuthorizationTest() {
        AuthStock flag = investor.giveAuthorization(10,sh,broker,date1);

        Assertions.assertTrue(flag!=null && sh.getCommittedAmount() == 10);
    }

    @Test
    void BrokengiveStocksAuthorizationTest() {
        AuthStock flag = investor.giveAuthorization(21,sh,broker,date1); //investor has 10 amount of stocks

        Assertions.assertNull(flag);
    }

    @Test
    void removeStockAuthorizationTest() {
        AuthStock flag = investor.giveAuthorization(10,sh,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthStock ac = (AuthStock) auths.iterator().next();

        boolean flag2 = investor.removeAuthorization(ac);

        Assertions.assertTrue(flag!=null && flag2 && sh.getCommittedAmount() == 0);
    }

    @Test
    void removeStockAuthorizationTest2() {
        AuthStock ac = new AuthStock(investor, sh, broker, date1, date2, 10);

        boolean flag = investor.removeAuthorization(ac);

        Assertions.assertFalse(flag);
    }

    @Test
    void addTwiceToSameStock() {
        investor.giveAuthorization(10,sh,broker,date1);
        investor.giveAuthorization(10,sh,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthStock ac = (AuthStock) auths.iterator().next();

        Assertions.assertEquals(20, ac.getAmount());
    }

    @Test
    void BrokenaddTwiceToSameStock() {
        investor.giveAuthorization(10,sh,broker,date1);
        investor.giveAuthorization(11,sh,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthStock ac = (AuthStock) auths.iterator().next();

        Assertions.assertNotEquals(21, ac.getAmount());
    }

    @Test
    void addTwiceBalanceToSameBroker() {
        investor.giveAuthorization(250.00,broker,date1);
        investor.giveAuthorization(250.00,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthCapital ac = (AuthCapital) auths.iterator().next();

        Assertions.assertEquals(500, ac.getAmount());
    }

    @Test
    void BrokenaddTwiceBalanceToSameBroker() {
        investor.giveAuthorization(250.00,broker,date1);
        investor.giveAuthorization(251.00,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthCapital ac = (AuthCapital) auths.iterator().next();

        Assertions.assertNotEquals(501, ac.getAmount());
    }

    @Test
    void addDifferentBalanceAndStocks() {
        AuthCapital flag1 = investor.giveAuthorization(250.00,broker,date1);
        AuthCapital flag2 = investor.giveAuthorization(100.00,broker,date1);
        AuthCapital flag3 = investor.giveAuthorization(150.00,broker2,date1);


        AuthStock flag4 = investor.giveAuthorization(10,sh,broker,date1);
        AuthStock flag5 = investor.giveAuthorization(10,sh,broker,date1);
        AuthStock flag6 = investor.giveAuthorization(10,sh2,broker2,date1);


        Assertions.assertTrue(flag1!=null&&flag2!=null&&flag3!=null&&flag4!=null&&flag5!=null&&flag6!=null);
    }

    @Test
    void expectedFalseFromAuthorizationsFunctions() {
        Authorization auth = new Authorization(investor,broker,date1,date2);


        AuthStock flag1 = auth.giveNewAuthorization(investor,0,broker,sh);
        AuthCapital flag2 = auth.giveNewAuthorization(investor,0.0,broker);
        AuthCapital flag3 = auth.updateAuthorization(10.0);
        boolean flag4 = auth.isBetween(investor, broker);
        AuthStock flag5 = auth.updateAuthorization(10);
        boolean flag6 = auth.isBetween(investor, broker,sh);
        boolean flag7 = auth.removeAuth();



        Assertions.assertFalse(flag1!=null||flag2!=null||flag3!=null||flag4||flag5!=null||flag6||flag7);
    }
    
    @Test
    void setAuthorizationTest() {
    	Investor inv = new Investor();
    	Set<Authorization> auths = new HashSet<Authorization>();
    	inv.setAuthorizations(auths);
    	assertEquals(auths, investor.getAuthorizations());
    }
}
