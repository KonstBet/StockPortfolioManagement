package gr.aueb.team1.domain;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthCapitalTest {

	private Investor investor1;
	private Investor investor2;
    private Broker broker1;
    private Broker broker2;
    private Stock PeiraiosStock;
    private Integer amount;
    private StockHolding sh;
    private LocalDateTime date1;
    private AuthCapital ac1;
    private AuthCapital ac2;


	
    @BeforeEach
    public void setUpTests() {
        investor1 = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
        investor1.setBalance(500.00);
        investor2 = new Investor("Akakios", "Charalampidis", "mcharal@gmail.com", "697891030100");
        broker1 = new Broker("Giorgos", "Charalampopoulos", "mcharal@gmail.com", "697891030100",0.0);
        broker2 = new Broker("Nikolaos", "Charalampous", "mcharal@gmail.com", "697891030100",0.0);

        PeiraiosStock = new Stock("P200", "PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
        amount = 10;
        sh = new StockHolding(amount, PeiraiosStock, investor1);
        investor1.addStockHolding(PeiraiosStock, sh);

        date1 = LocalDateTime.of(2021,12,31,0,0,0);
        
        ac1 = new AuthCapital(investor1, broker1, LocalDateTime.now(), date1, 600.00);
        ac2 = new AuthCapital(investor1, broker2, LocalDateTime.now(), date1, 0.0);
    }
    
    
    @Test // Case 1: Not enough balance
    public void giveNewAuthorizationTest1() {
    	boolean actual = ac1.giveNewAuthorization(investor1, 500.99, broker1);
    	assertFalse(actual);
    }
    
    @Test // Case 2: Enough Balance
    public void giveNewAuthorizationTest2() {
    	boolean actual = ac1.giveNewAuthorization(investor1, 499.99, broker1);
    	assertTrue(actual);
    }
    
    @Test // Case 1: Not enough balance
    public void giveToExistedAuthorizationTest1() {
    	boolean actual = ac1.giveToExistedAuthorization(500.01);
    	assertFalse(actual);
    }
    
    @Test // Case 2: Enough Balance
    public void giveToExistedAuthorizationTest2() {
    	boolean actual = ac1.giveToExistedAuthorization(499.99);
    	assertTrue(actual);
    }
    
    
    @Test // Case 1: Authorization is between Investor and Broker
    public void isBetweenTest1() {
    	ac2.setAmount(400.00);
    	boolean actual = ac2.isBetween(investor1, broker2);
    	assertTrue(actual);
    }
    
    @Test // Case 2: Authorization is not between Investor and Broker | Wrong Broker
    public void isBetweenTest2() {
    	boolean actual = ac1.isBetween(investor1, broker2);
    	assertFalse(actual);
    }
    
    @Test // Case 2: Authorization is not between Investor and Broker | Wrong Investor
    public void isBetweenTest3() {
    	boolean actual = ac1.isBetween(investor2, broker2);
    	assertFalse(actual);
    }


    
    @Test // Case 1: Zero Committed Balance
    public void removeAuthTest1() {
    	boolean actual = ac1.removeAuth();
    	assertFalse(actual);
    }
    
    @Test // Case 2: Committed Balance returns to Investor
    public void removeAuthTest2() {

    	investor1.setCommittedBalance(600.00);
    	ac1.removeAuth();
    	double actual = ac1.getInvestor().getBalance();
    	
    	assertEquals(1100.00, actual);
    }
    
    @Test // Case 3: Authorization gets deleted for investor
    public void removeAuthTest3() {
    	boolean actual = investor1.getAuthorizations().contains(ac1);
    	assertFalse(actual);
    }
    
    @Test // Case 4: Authorization gets deleted for broker
    public void removeAuthTest4() {
    	boolean actual = broker1.getAuthorizations().contains(ac1);
    	assertFalse(actual);
    }
    
}
