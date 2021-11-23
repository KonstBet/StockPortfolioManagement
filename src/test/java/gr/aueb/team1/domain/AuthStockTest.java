package gr.aueb.team1.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthStockTest {
	private Investor investor1;
	private Investor investor2;
    private Broker broker1;
    private Broker broker2;
    private Stock PeiraiosStock;
    private Integer amount;
    private StockHolding sh1;
    private StockHolding sh2;
    private LocalDateTime date1;
    private AuthStock as1;
    private AuthStock as2;
    
    
    @BeforeEach
    public void setUpTests() {
        investor1 = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
        investor1.setBalance(500.00);
        investor2 = new Investor("Akakios", "Charalampidis", "mcharal@gmail.com", "697891030100");
        broker1 = new Broker("Giorgos", "Charalampopoulos", "mcharal@gmail.com", "697891030100",0.0);
        broker2 = new Broker("Nikolaos", "Charalampous", "mcharal@gmail.com", "697891030100",0.0);

        PeiraiosStock = new Stock("P200", "PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
        amount = 10;
        sh1 = new StockHolding(amount, PeiraiosStock, investor1);
        sh2 = new StockHolding(amount, new Stock("P210", "VASILOPOULOS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00), investor1);
        investor1.addStockHolding(PeiraiosStock, sh1);

        date1 = LocalDateTime.of(2021,12,31,0,0,0);
        
        as1 = new AuthStock(investor1, sh1 ,broker1, LocalDateTime.now(), date1, 10);
        as2 = new AuthStock(investor1, sh1 ,broker2, LocalDateTime.now(), date1, 0);
    }
    
    @Test // Case 1: Not enough amount
    public void giveNewAuthorizationTest1() {
    	boolean actual = as1.giveNewAuthorization(investor1, 11, broker1, sh1);
    	assertFalse(actual);
    }
    
    @Test // Case 2: Enough Amount
    public void giveNewAuthorizationTest2() {
    	Boolean actual = as1.giveNewAuthorization(investor1, 9, broker1, sh1);
    	assertTrue(actual);
    }
    
    @Test // Case 1: Not enough amount
    public void giveToExistedAuthorizationTest1() {
    	Boolean actual = as1.giveToExistedAuthorization(11);
    	assertFalse(actual);
    }
    
    @Test // Case 2: Enough amount
    public void giveToExistedAuthorizationTest2() {
    	boolean actual = as2.giveToExistedAuthorization(9);
    	assertTrue(actual);
    }
    
    @Test // Case 1: Authorization is between Investor and Broker
    public void isBetweenTest1() {
    	as2.setAmount(5);
    	boolean actual = as2.isBetween(investor1, broker2, sh1);
    	assertTrue(actual);
    }
    
    @Test // Case 2: Authorization is not between Investor and Broker | Wrong Broker
    public void isBetweenTest2() {
    	boolean actual = as1.isBetween(investor1, broker2, sh1);
    	assertFalse(actual);
    }
    
    @Test // Case 3: Authorization is not between Investor and Broker | Wrong Investor
    public void isBetweenTest3() {
    	as2.setAmount(5);
    	boolean actual = as2.isBetween(investor2, broker2, sh1);
    	assertFalse(actual);
    }
    
    @Test // Case 4: Authorization is not between Investor and Broker | Wrong StockHolding
    public void isBetweenTest4() {
    	as2.setAmount(5);
    	boolean actual = as2.isBetween(investor1, broker2, sh2);
    	assertFalse(actual);
    }
    
    @Test // Case 1: Zero Committed Amount
    public void removeAuthTest1() {
    	boolean actual = as1.removeAuth();
    	assertFalse(actual);
    }
    
    @Test // Case 2: Committed Amount returns to Investor
    public void removeAuthTest2() {
    	as1.setStockholding(sh1);
    	as1.getStockHolding().setCommittedAmount(10);
    	as1.removeAuth();
    	int actual = sh1.getAmount();
    	assertEquals(20, actual);
    }
    
    @Test // Case 3: Authorization gets deleted for investor
    public void removeAuthTest3() {
    	boolean actual = investor1.getAuthorizations().contains(as1);
    	assertFalse(actual);
    }
    
    @Test // Case 4: Authorization gets deleted for broker
    public void removeAuthTest4() {
    	boolean actual = broker1.getAuthorizations().contains(as1);
    	assertFalse(actual);
    }
    
}
