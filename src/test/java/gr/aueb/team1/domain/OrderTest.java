package gr.aueb.team1.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import gr.aueb.team1.domain.Order.Action;
import gr.aueb.team1.domain.Order.Status;

public class OrderTest {
    private Investor investor;
    private Broker broker;
    private Stock PeiraiosStock;
    private Stock AlphaStock;

    private Integer amount;
    private StockHolding sh;
    private LocalDateTime date1;
    private final Double fee = 0.1;
    private AuthCapital ac1;
    private AuthStock as1;
    private AuthStock as2;

    @BeforeEach
    void setUpTests() {
        investor = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100", "b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");
        investor.setBalance(500.00);
        broker = new Broker("Giorgos", "Charalampopoulos", "mcharal@gmail.com", "697891030100", 0.0, "b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");

        PeiraiosStock = new Stock("PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
        AlphaStock = new Stock("ALPHA", LocalDateTime.now(), 100.00, 200.99, 1000.00, 10.00, 2460.00);
        amount = 10;
        sh = new StockHolding(amount, PeiraiosStock, investor);
        investor.addStockHolding(PeiraiosStock, sh);

        date1 = LocalDateTime.of(2021,12,31,0,0,0);
        
        ac1 = new AuthCapital(investor, broker, LocalDateTime.now(), date1, 600.00);
        as1 = new AuthStock(investor, sh ,broker, LocalDateTime.now(), date1, 9);
        as2 = new AuthStock(investor, sh ,broker, LocalDateTime.now(), date1, 10);
    }
    
    @Test //Setters & Getters
    void setGetTest() {
    	Order ord = new Order();
    	ord.setAction(Action.BUY);
    	ord.setAmount(10);
    	ord.setDate(date1);
    	ord.setFee(1.0);
    	ord.setOrderPrice(100.0);
    	ord.setStatus(Status.COMPLETED);
    	ord.setStock(AlphaStock);
    	ord.setUser(investor);
    	assertTrue(ord.getAction() == Action.BUY && ord.getAmount() == 10 && ord.getDate() == date1 && ord.getFee() == 1.0 && 
    			ord.getOrderPrice() == 100.0 && ord.getStatus() == Status.COMPLETED && ord.getStock() == AlphaStock && ord.getUser() == investor);
    }
    
    
    @Test // User already owns the StockHolding
    void buyTest1() {
    	Double fee = 0.1;
    	Order or = new Order(investor, PeiraiosStock, 1	, fee, date1, Action.BUY, Status.PENDING);
    	investor.addStockHolding(PeiraiosStock, sh);
    	or.buy();
    	int actual = investor.getStockHoldings().get(PeiraiosStock).getAmount();
    	assertEquals(11, actual);
    }
    
    @Test // User doesn't own the StockHolding
    void buyTest2() {
    	Order or = new Order(investor, AlphaStock, 1, fee, date1, Action.BUY, Status.PENDING);
    	or.buy();
    	int actual = investor.getStockHoldings().get(AlphaStock).getAmount();
    	assertEquals(1, actual);
    }
    
    @Test // User sells all of his stock
    void sellTest1() {
    	Order or = new Order(investor, PeiraiosStock, 10, fee, date1, Action.SELL, Status.PENDING);
    	or.sell(sh);
    	boolean actual = investor.getStockHoldings().containsKey(PeiraiosStock);
    	assertFalse(actual);
    }
    
    @Test // User sells part of his stock
    void sellTest2() {
    	Order or = new Order(investor, PeiraiosStock, 9, fee, date1, Action.SELL, Status.PENDING);
    	or.sell(sh);
    	int actual = investor.getStockHoldings().get(PeiraiosStock).getAmount();
    	assertEquals(1, actual);
    }
    
    @Test // Apply Completed order
    void applyOrderTest1() {
    	Order or = new Order(investor, PeiraiosStock, 9, fee, date1, Action.BUY, Status.COMPLETED);
    	boolean actual = or.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Apply buy order with not enough balance
    void applyOrderTest2() {
    	Order or = new Order(investor, AlphaStock, 100, fee, date1, Action.BUY, Status.PENDING);
    	boolean actual = or.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Apply buy order with enough balance
    void applyOrderTest3() {
    	Order or = new Order(investor, PeiraiosStock, 1, fee, date1, Action.BUY, Status.PENDING);
    	boolean actual = or.applyOrder();
    	assertTrue(actual);
    }
    
    @Test // Apply sell order without the stock
    void applyOrderTest4() {
    	Order or = new Order(investor, AlphaStock, 1, fee, date1, Action.SELL, Status.PENDING);
    	boolean actual = or.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Apply sell order without enough amount
    void applyOrderTest5() {
    	Order or = new Order(investor, PeiraiosStock, 11, fee, date1, Action.SELL, Status.PENDING);
    	boolean actual = or.applyOrder();
    	assertFalse(actual);
    }
    
    @Test // Apply sell order
    void applyOrderTest6() {
    	Order or = new Order(investor, PeiraiosStock, 10, fee, date1, Action.SELL, Status.PENDING);
    	boolean actual = or.applyOrder();
    	assertTrue(actual);
    }
    
    // CAPITAL AUTHORIZATION ORDER
    @Test // Apply Completed Order
    void applyBrokerOrderTest1() {
    	Order or = new Order(investor, PeiraiosStock, 10, fee, date1, Action.BUY, Status.COMPLETED);
    	boolean actual = or.applyBrokerOrder(ac1);
    	assertFalse(actual);
    }
    
    @Test // Apply order, not enough capital
    void applyBrokerOrderTest2() {
    	Order or = new Order(investor, AlphaStock, 10, fee, date1, Action.BUY, Status.PENDING);
    	boolean actual = or.applyBrokerOrder(ac1);
    	assertFalse(actual);
    }
    
    @Test // Apply order, Investor already owns the stock
    void applyBrokerOrderTest3() {
    	Order or = new Order(investor, PeiraiosStock, 10, fee, date1, Action.BUY, Status.PENDING);
    	boolean actual = or.applyBrokerOrder(ac1);
    	assertTrue(actual);
    }
    
    @Test // Apply order, Investor doesn't own the stock
    void applyBrokerOrderTest4() {
    	Order or = new Order(investor, AlphaStock, 1, fee, date1, Action.BUY, Status.PENDING);
    	boolean actual = or.applyBrokerOrder(ac1);
    	assertTrue(actual);
    }
    
    
    // STOCK AUTHORIZATION ORDER
    @Test // Apply Completed Order
    void applyBrokerOrderTest5() {
    	Order or = new Order(investor, PeiraiosStock, 10, fee, date1, Action.SELL, Status.COMPLETED);
    	boolean actual = or.applyBrokerOrder(as1);
    	assertFalse(actual);
    }

   
    @Test // Authorization does not contain the stock
    void applyBrokerOrderTest6() {
    	investor.giveAuthorization(9, sh, broker, date1);
    	Order or = new Order(investor, AlphaStock, 1, fee, date1, Action.SELL, Status.PENDING);
    	boolean actual = or.applyBrokerOrder(as1);
    	assertFalse(actual);
    }
    
    @Test // Authorization does not contain enough of the stock
    void applyBrokerOrderTest7() {
    	investor.giveAuthorization(9, sh, broker, date1);
    	Order or = new Order(investor, PeiraiosStock, 11, fee, date1, Action.SELL, Status.PENDING);
    	boolean actual = or.applyBrokerOrder(as1);
    	assertFalse(actual);
    }   
    
    @Test // Successful order
    void applyBrokerOrderTest8() {
    	investor.giveAuthorization(9, sh, broker, date1);
    	Order or = new Order(investor, PeiraiosStock, 6, fee, date1, Action.SELL, Status.PENDING);
    	boolean actual = or.applyBrokerOrder(as1);
    	assertTrue(actual);
    }
    
    @Test // Successful all depleting order
    void applyBrokerOrderTest9() {
    	investor.giveAuthorization(9, sh, broker, date1);
    	Order or = new Order(investor, PeiraiosStock, 9, fee, date1, Action.SELL, Status.PENDING);
    	Order or1 = new Order(investor, PeiraiosStock, 1, fee, date1, Action.SELL, Status.PENDING);
    	System.out.println(investor.getStockHoldings().get(PeiraiosStock).getAmount());
    	System.out.println(investor.getStockHoldings().get(PeiraiosStock).getCommittedAmount());
    	or1.applyOrder(); 
    	
    	or.applyBrokerOrder(as1);
   	


    	assertFalse(investor.getStockHoldings().containsKey(PeiraiosStock));
    }
    
    @Test // Successful committed depleting order
    void applyBrokerOrderTest10() {
    	investor.giveAuthorization(9, sh, broker, date1);
    	Order or = new Order(investor, PeiraiosStock, 9, fee, date1, Action.SELL, Status.PENDING);
    	or.applyBrokerOrder(as1);
    	assertTrue(investor.getStockHoldings().get(PeiraiosStock).getCommittedAmount() == 0);
    }
    
    @Test // Successful amount depleting order
    void applyBrokerOrderTest11() {
    	investor.giveAuthorization(10, sh, broker, date1);
    	Order or = new Order(investor, PeiraiosStock, 8, fee, date1, Action.SELL, Status.PENDING);
    	or.applyBrokerOrder(as2);
    	assertTrue(investor.getStockHoldings().get(PeiraiosStock).getAmount() == 0);
    }

    @Test 
	void toStringTest() {
    	Order o = new Order();
    	o.setDate(date1);
		String s = o.toString();
		assertEquals("ID: " + o.getId() +
				"\nAmount: " + o.getAmount() + 
				"\nFee: " + o.getFee() + "%" +
				"\nDate: " + o.getDate().toString() + 
				"\nAction: " + o.getAction() + 
				"\nOrder Price: " + o.getOrderPrice() + "€", s);
	}
    
    @Test 
	void actionToStringTest() {
    	Order o = new Order();
    	o.setAction(Action.BUY);
		String s = o.actionToString();
		assertEquals("BUY", s);
	}
    
    @Test 
	void statusToStringTest() {
    	Order o = new Order();
    	o.setStatus(Status.COMPLETED);
		String s = o.statusToString();
		assertEquals("COMPLETED", s);
	}
}
