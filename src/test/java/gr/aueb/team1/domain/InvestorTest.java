package gr.aueb.team1.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gr.aueb.team1.domain.AuthCapital;
import gr.aueb.team1.domain.AuthStocks;
import gr.aueb.team1.domain.Authorization;
import gr.aueb.team1.domain.Broker;
import gr.aueb.team1.domain.Investor;
import gr.aueb.team1.domain.Stock;
import gr.aueb.team1.domain.StockHolding;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class InvestorTest {
    private Investor investor;
    private Broker broker;
    private Broker broker2;
    private Stock PeiraiosStock;
    private Stock AlphaStock;
    private Stock CosmoteStock;
    private Integer amount;
    private StockHolding sh;
    private StockHolding sh2;
    private LocalDateTime date1;
    private LocalDateTime date2;

    @BeforeEach
    public void setUpTests() {
        investor = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
        investor.setBalance(500.00);
        broker = new Broker("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100",0.0);
        broker2 = new Broker("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100",0.0);

        PeiraiosStock = new Stock("P200", "PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
        AlphaStock = new Stock("P224", "ALPHA", LocalDateTime.now(), 100.00, 200.99, 1000.00, 10.00, 2460.00);
        CosmoteStock = new Stock("P104", "COSMOTE", LocalDateTime.now(), 29.00, 200.99, 1000.00, 10.00, 2460.00);
        amount = 20;
        sh = new StockHolding(amount, PeiraiosStock, investor);
        investor.addStockHolding(PeiraiosStock, sh);
        sh2 = new StockHolding(amount, AlphaStock, investor);
        investor.addStockHolding(AlphaStock, sh);

        date1 = LocalDateTime.of(2021,12,31,0,0,0);
        date2 = LocalDateTime.of(2021,12,31,0,0,0);
    }

    @Test
    public void giveCapitalAuthorizationTest() {
        boolean flag = investor.giveAuthorization(500.00,broker,date1);

        Assertions.assertTrue(flag && investor.getCommittedBalance() == 500);
    }

    @Test
    public void BrokengiveCapitalAuthorizationTest() {
        boolean flag = investor.giveAuthorization(501.00,broker,date1);

        Assertions.assertFalse(flag);
    }

    @Test
    public void removeCapitalAuthorizationTest() {
        boolean flag = investor.giveAuthorization(500.00,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthCapital ac = (AuthCapital) auths.iterator().next();

        boolean flag2 = investor.removeAuthorization(ac);

        Assertions.assertTrue(flag && flag2 && investor.getCommittedBalance() == 0
                && investor.getAuthorizations().size() == 0 && broker.getAuthorizations().size() == 0);
    }

    @Test
    public void removeCapitalAuthorizationTest2() {
        AuthCapital ac = new AuthCapital(investor, broker, date1, date2, 10.0);

        boolean flag = investor.removeAuthorization(ac);

        Assertions.assertFalse(flag);
    }

    @Test
    public void giveStocksAuthorizationTest() {
        boolean flag = investor.giveAuthorization(10,sh,broker,date1);

        Assertions.assertTrue(flag && sh.getCommittedAmount() == 10);
    }

    @Test
    public void BrokengiveStocksAuthorizationTest() {
        boolean flag = investor.giveAuthorization(21,sh,broker,date1); //investor has 10 amount of stocks

        Assertions.assertFalse(flag);
    }

    @Test
    public void removeStockAuthorizationTest() {
        boolean flag = investor.giveAuthorization(10,sh,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthStocks ac = (AuthStocks) auths.iterator().next();

        boolean flag2 = investor.removeAuthorization(ac);

        Assertions.assertTrue(flag && flag2 && sh.getCommittedAmount() == 0);
    }

    @Test
    public void removeStockAuthorizationTest2() {
        AuthStocks ac = new AuthStocks(investor, sh, broker, date1, date2, 10);

        boolean flag = investor.removeAuthorization(ac);

        Assertions.assertFalse(flag);
    }

    @Test
    public void addTwiceToSameStock() {
        investor.giveAuthorization(10,sh,broker,date1);
        investor.giveAuthorization(10,sh,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthStocks ac = (AuthStocks) auths.iterator().next();

        Assertions.assertEquals(20, ac.getAmount());
    }

    @Test
    public void BrokenaddTwiceToSameStock() {
        investor.giveAuthorization(10,sh,broker,date1);
        investor.giveAuthorization(11,sh,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthStocks ac = (AuthStocks) auths.iterator().next();

        Assertions.assertNotEquals(21, ac.getAmount());
    }

    @Test
    public void addTwiceBalanceToSameBroker() {
        investor.giveAuthorization(250.00,broker,date1);
        investor.giveAuthorization(250.00,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthCapital ac = (AuthCapital) auths.iterator().next();

        Assertions.assertEquals(500, ac.getAmount());
    }

    @Test
    public void BrokenaddTwiceBalanceToSameBroker() {
        investor.giveAuthorization(250.00,broker,date1);
        investor.giveAuthorization(251.00,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthCapital ac = (AuthCapital) auths.iterator().next();

        Assertions.assertNotEquals(501, ac.getAmount());
    }

    @Test
    public void addDifferentBalanceAndStocks() {
        boolean flag1 = investor.giveAuthorization(250.00,broker,date1);
        boolean flag2 = investor.giveAuthorization(100.00,broker,date1);
        boolean flag3 = investor.giveAuthorization(150.00,broker2,date1);


        boolean flag4 = investor.giveAuthorization(10,sh,broker,date1);
        boolean flag5 = investor.giveAuthorization(10,sh,broker,date1);
        boolean flag6 = investor.giveAuthorization(10,sh2,broker2,date1);


        Assertions.assertTrue(flag1&&flag2&&flag3&&flag4&&flag5&&flag6);
    }

    @Test
    public void expectedFalseFromAuthorizationsFunctions() {
        Authorization auth = new Authorization(investor,broker,date1,date2);
        AuthStocks as = new AuthStocks(investor, sh, broker, date1, date2, 10);
        AuthCapital ac = new AuthCapital(investor, broker, date1, date2, 10.0);

        boolean flag1 = auth.giveNewAuthorization(investor,0,broker,sh);
        boolean flag2 = auth.giveNewAuthorization(investor,0.0,broker);
        boolean flag3 = auth.giveToExistedAuthorization(10.0);
        boolean flag4 = auth.existsAuthorizationToEqual(investor, broker);
        boolean flag5 = auth.giveToExistedAuthorization(10);
        boolean flag6 = auth.existsAuthorizationToEqual(investor, broker,sh);
        boolean flag7 = auth.removeAuth();



        Assertions.assertFalse(flag1||flag2||flag3||flag4||flag5||flag6||flag7);
    }
}
