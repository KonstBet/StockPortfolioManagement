package gr.aueb.team1.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gr.aueb.team1.domain.Order.Action;
import gr.aueb.team1.domain.Order.Status;

public class AutomatedOrderTest {
	
	private AutomatedOrder ao;
	private Investor investor1;
    private Stock AlphaStock;
    private Stock PeiraiosStock;
    private Integer amount;
    private StockHolding sh;
    private LocalDateTime date1;
    private final Double fee = 0.1;


	
    @BeforeEach
    void setUpTests() {
        investor1 = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100", "b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");
        investor1.setBalance(500.00);
        PeiraiosStock = new Stock("PIRAEUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
        AlphaStock = new Stock( "ALPHA", LocalDateTime.now(), 100.00, 80.00, 1000.00, 10.00, 2460.00);
        amount = 10;
        sh = new StockHolding(amount, PeiraiosStock, investor1);
        investor1.addStockHolding(PeiraiosStock, sh);
        date1 = LocalDateTime.of(2021,12,31,0,0,0);
    }
    
    @Test // Already Completed
    void applyOrderTest1() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 9, fee, date1, Action.SELL, 0.1);
    	ao.setStatus(Status.COMPLETED);
    	boolean actual = ao.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Apply buy order with not enough balance
    void applyOrderTest2() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 1000, fee, date1, Action.BUY, 1.0);
    	boolean actual = ao.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Apply buy order with with enough balance
    void applyOrderTest3() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 1, fee, date1, Action.BUY, -2.0);
    	boolean actual = ao.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Close price is less than limit
    void applyOrderTest4() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 1, fee, date1, Action.BUY, 2000.0);
    	boolean actual = ao.applyOrder();
    	assertTrue(actual);
    }
    
    @Test // Apply sell order without the stock
    void applyOrderTest5() {
    	ao = new AutomatedOrder(investor1, AlphaStock, 1, fee, date1, Action.SELL, 0.1);
    	boolean actual = ao.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Apply sell order without enough amount
    void applyOrderTest6() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 11, fee, date1, Action.SELL, 0.1);
    	boolean actual = ao.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Close price more than limit
    void applyOrderTest7() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 9, fee, date1, Action.SELL, -26.00);
    	boolean actual = ao.applyOrder();
    	assertTrue(actual);
    }
    
    @Test // Close price less than limit
    void applyOrderTest8() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 9, fee, date1, Action.SELL, 10000.00);
    	boolean actual = ao.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Setter Getter 
    void test() {
    	ao = new AutomatedOrder();
    	ao.setLimit(15.0);
    	double actual = ao.getLimit();
    	assertEquals(15.0, actual);
    }
    
    @Test
    void toStringTest() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 9, fee, date1, Action.SELL, 0.1);
		String s = ao.toString();
		assertEquals("ID: " + ao.getId() +
				"\nAmount: " + ao.getAmount() + 
				"\nFee: " + ao.getFee() + "%" +
				"\nDate: " + ao.getDate().toString() + 
				"\nAction: " + ao.getAction() + 
				"\nOrder Price: " + ao.getOrderPrice() + "€" + "\nLimit: " + ao.getLimit() + "€", s);
    }
}
