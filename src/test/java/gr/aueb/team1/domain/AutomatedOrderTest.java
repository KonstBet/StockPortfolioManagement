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
    public void setUpTests() {
        investor1 = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
        investor1.setBalance(500.00);
        PeiraiosStock = new Stock("P200", "PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
        AlphaStock = new Stock("P224", "ALPHA", LocalDateTime.now(), 100.00, 200.99, 1000.00, 10.00, 2460.00);
        amount = 10;
        sh = new StockHolding(amount, PeiraiosStock, investor1);
        investor1.addStockHolding(PeiraiosStock, sh);
        date1 = LocalDateTime.of(2021,12,31,0,0,0);
    }
    
    @Test // Already Completed
    public void applyOrderTest1() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 9, fee, date1, Action.SELL, 0.1);
    	ao.setStatus(Status.COMPLETED);
    	boolean actual = ao.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Apply buy order with not enough balance
    public void applyOrderTest2() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 1000, fee, date1, Action.BUY, 0.1);
    	boolean actual = ao.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Apply buy order with with enough balance
    public void applyOrderTest3() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 1, fee, date1, Action.BUY, 0.1);
    	boolean actual = ao.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Close price is less than limit
    public void applyOrderTest4() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 1, fee, date1, Action.BUY, 100.00);
    	boolean actual = ao.applyOrder();
    	assertTrue(actual);
    }
    
    @Test // Apply sell order without the stock
    public void applyOrderTest5() {
    	ao = new AutomatedOrder(investor1, AlphaStock, 1, fee, date1, Action.SELL, 0.1);
    	boolean actual = ao.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Apply sell order without enough amount
    public void applyOrderTest6() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 11, fee, date1, Action.SELL, 0.1);
    	boolean actual = ao.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Close price more than limit
    public void applyOrderTest7() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 9, fee, date1, Action.SELL, 101.00);
    	boolean actual = ao.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Close price less than limit
    public void applyOrderTest8() {
    	ao = new AutomatedOrder(investor1, PeiraiosStock, 9, fee, date1, Action.SELL, 0.1);
    	boolean actual = ao.applyOrder();
    	assertTrue(actual);
    }
    
    @Test // Setter Getter 
    public void test() {
    	ao = new AutomatedOrder();
    	ao.setLimit(15.0);
    	double actual = ao.getLimit();
    	assertEquals(15.0, actual);
    }
    
    @Test
    public void toStringTest() {
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
