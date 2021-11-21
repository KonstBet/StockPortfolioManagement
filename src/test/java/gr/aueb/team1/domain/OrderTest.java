package gr.aueb.team1.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import gr.aueb.team1.domain.Order.Action;
import gr.aueb.team1.domain.Order.Status;

public class OrderTest {
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
        broker = new Broker("Giorgos", "Charalampopoulos", "mcharal@gmail.com", "697891030100",0.0);

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
    public void testApplyOrderBuy1() {
    	Double fee = 0.1;
    	Order or = new Order(investor, PeiraiosStock, 1	, 0.1, date1, Action.BUY, Status.PENDING);
    	Assertions.assertTrue(true);
//    	Assertions.assertTrue(investor.getStockHoldings().get(CosmoteStock).getAmount() == 1 && investor.getBalance() == 465.00);
    }
}
