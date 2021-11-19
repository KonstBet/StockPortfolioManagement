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
    private Stock PeiraiosStock;
    private Stock AlphaStock;
    private Stock CosmoteStock;
    private Integer amount;
    private StockHolding sh;
    private LocalDateTime date1;
    private LocalDateTime date2;

    @BeforeEach
    public void setUpTests() {
        investor = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
        investor.setBalance(500.00);
        broker = new Broker("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100",0.0);

        PeiraiosStock = new Stock("P200", "PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
        AlphaStock = new Stock("P224", "ALPHA", LocalDateTime.now(), 100.00, 200.99, 1000.00, 10.00, 2460.00);
        CosmoteStock = new Stock("P104", "COSMOTE", LocalDateTime.now(), 29.00, 200.99, 1000.00, 10.00, 2460.00);
        amount = 10;
        sh = new StockHolding(amount, PeiraiosStock, investor);
        investor.addStockHolding(PeiraiosStock, sh);

        date1 = LocalDateTime.of(2021,12,31,0,0,0);
        date2 = LocalDateTime.of(2021,12,31,0,0,0);
    }

    @Test
    public void giveCapitalAuthorizationTest() {
        boolean flag = investor.giveCapitalAuthorization(500.00,broker,date1);

        Assertions.assertTrue(flag & investor.getCommittedBalance() == 500);
    }

    @Test
    public void BrokengiveCapitalAuthorizationTest() {
        boolean flag = investor.giveCapitalAuthorization(501.00,broker,date1);

        Assertions.assertFalse(flag);
    }

    @Test
    public void removeCapitalAuthorizationTest() {
        boolean flag = investor.giveCapitalAuthorization(500.00,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthCapital ac = (AuthCapital) auths.iterator().next();

        boolean flag2 = investor.removeCapitalAuthorization(ac);

        Assertions.assertTrue(flag & flag2 & investor.getCommittedBalance() == 0
                & investor.getAuthorizations().size() == 0 & broker.getAuthorizations().size() == 0);
    }

    @Test
    public void BrokenremoveCapitalAuthorizationTest() {
        AuthCapital ac = new AuthCapital(investor, broker, date1, date2, 10.0);

        boolean flag = investor.removeCapitalAuthorization(ac);

        Assertions.assertFalse(flag);
    }

    @Test
    public void giveStocksAuthorizationTest() {
        boolean flag = investor.giveStockAuthorization(10,sh,broker,date1);

        Assertions.assertTrue(flag & sh.getCommittedAmount() == 10);
    }

    @Test
    public void BrokengiveStocksAuthorizationTest() {
        boolean flag = investor.giveStockAuthorization(11,sh,broker,date1); //investor has 10 amount of stocks

        Assertions.assertFalse(flag);
    }

    @Test
    public void removeStockAuthorizationTest() {
        boolean flag = investor.giveStockAuthorization(10,sh,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthStocks ac = (AuthStocks) auths.iterator().next();

        boolean flag2 = investor.removeStockAuthorization(ac);

        Assertions.assertTrue(flag & flag2 & sh.getCommittedAmount() == 0);
    }

    @Test
    public void BrokenremoveStockAuthorizationTest() {
        AuthStocks ac = new AuthStocks(investor, sh, broker, date1, date2, 10);

        boolean flag = investor.removeStockAuthorization(ac);

        Assertions.assertFalse(flag);
    }
}
