package gr.aueb.team1.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StockHoldingTest {

    @Test
    void GettersSetters() {
        StockHolding sh = new StockHolding();
        sh.setStock(null);
        sh.setAmount(10);
        sh.setUser(null);
        sh.setCommittedAmount(10);
        sh.setAuthStock(null);

        Assertions.assertTrue(sh.getAmount() == 10 && sh.getCommittedAmount() == 10);

        Assertions.assertNull(sh.getStock());
        Assertions.assertNull(sh.getAuthStock());
        Assertions.assertNull(sh.getUser());
    }
    
    @Test
    void removeStockHolding() {
    	User u = new User("Alejandro", "Lamprineiro", "alamp@gmail.com", "6947307032", "pass");
        Stock PeiraiosStock = new Stock("PIRAEUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
    	StockHolding sh = new StockHolding(5, PeiraiosStock, u);
    	sh.remove();
    	Assertions.assertEquals(u.getStockHoldings().size(), 0);
    }
    
    @Test
    void removeStockHolding2() {
    	Investor i = new Investor("Alejandro", "Lamprineiro", "alamp@gmail.com", "6947307032", "pass");
    	Broker b = new Broker("Sakis", "Kanavopoulos", "sakan@gmail.com", "6956079475", 0.1, "1234");
        Stock PeiraiosStock = new Stock("PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
    	StockHolding sh = new StockHolding(5, PeiraiosStock, i, 0);
    	i.giveAuthorization(3, sh, b, LocalDateTime.now());
    	sh.remove();
    	Assertions.assertEquals(i.getStockHoldings().size(), 0);
    	
    	
    }
    
    @Test
    void getIdTest() {
    	User u = new User("Alejandro", "Lamprineiro", "alamp@gmail.com", "6947307032", "pass");
        Stock PeiraiosStock = new Stock("PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
    	StockHolding sh = new StockHolding(5, PeiraiosStock, u, 0);
    	Integer k = sh.getId();
    	Assertions.assertNull(k);
    }
}
