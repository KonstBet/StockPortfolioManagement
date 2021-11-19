package gr.aueb.team1.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gr.aueb.team1.domain.AuthCapital;
import gr.aueb.team1.domain.Authorization;
import gr.aueb.team1.domain.Broker;
import gr.aueb.team1.domain.Investor;
import gr.aueb.team1.domain.Stock;
import gr.aueb.team1.domain.StockHolding;

import java.time.LocalDateTime;
import java.util.HashSet;

public class BrokerTest {
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
    public void buyStocksForInvestorTest() {
        investor.giveCapitalAuthorization(500.00,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthCapital ac = (AuthCapital) auths.iterator().next();

        boolean flag = broker.buyStocksForInvestor(ac, PeiraiosStock, 10);

        Assertions.assertTrue(flag);
    }

    @Test
    public void BrokenbuyStocksForInvestorTest() {
        investor.giveCapitalAuthorization(500.00,broker,date1);

        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
        AuthCapital ac = (AuthCapital) auths.iterator().next();

        boolean flag = broker.buyStocksForInvestor(ac, PeiraiosStock, 500);

        Assertions.assertFalse(flag);
    }

//TODO CANT RUN CAUSE NEED sellStocksForInvestor IMPLEMENTATION
//    @Test
//    public void sellStocksForInvestorTest() {
//        investor.giveStocksAuthorization(10,sh,broker,date1);
//
//        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
//        AuthStocks ac = (AuthStocks) auths.iterator().next();
//
//        boolean flag = broker.sellStocksForInvestor(ac, 10);
//
//        Assertions.assertTrue(flag);
//    }
//
//    @Test
//    public void BrokensellStocksForInvestorTest() {
//        investor.giveStocksAuthorization(10,sh,broker,date1);
//
//        HashSet<Authorization> auths = (HashSet<Authorization>) investor.getAuthorizations();
//        AuthStocks ac = (AuthStocks) auths.iterator().next();
//
//        boolean flag = broker.sellStocksForInvestor(ac, 500);
//
//        Assertions.assertFalse(flag);
//    }
}
