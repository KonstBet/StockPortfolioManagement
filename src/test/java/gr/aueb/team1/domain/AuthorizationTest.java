package gr.aueb.team1.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorizationTest {
	
	public Investor investor;
	public StockHolding stockHolding;
	public Broker broker;
	public LocalDateTime date1;
	public LocalDateTime date2;
	public Authorization auth;
	
	
	@BeforeEach
    void setUpTests() {
		investor = new Investor();
		investor.setName("Giannhs");
		
		stockHolding = new StockHolding();
		
		broker = new Broker();
		broker.setName("Kwstas");
		
		date1 = LocalDateTime.now();
		date2 = LocalDateTime.of(2021,12,31,0,0,0);
		
		auth = new Authorization(investor, broker, date1, date2);
    }
	
	@Test
	void giveNewAuthorizationTest1() {
		boolean actual = auth.giveNewAuthorization(investor, 60.00, broker);
		assertFalse(actual);
	}
	
	@Test
	void giveNewAuthorizationTest2() {
		boolean actual = auth.giveNewAuthorization(investor, 1, broker, stockHolding);
		assertFalse(actual);
	}
	
	
	@Test
	void giveToExistedAuthorizationTest1() {
		boolean actual = auth.giveToExistedAuthorization(1);
		assertFalse(actual);
	}
	
	@Test
	void giveToExistedAuthorizationTest2() {
		boolean actual = auth.giveToExistedAuthorization(1.0);
		assertFalse(actual);
	}
	
	@Test
	void existsAuthorizationToEqualTest1() {
		boolean actual = auth.isBetween(investor, broker);
		assertFalse(actual);
	}
	
	@Test
	void existsAuthorizationToEqualTest2() {
		boolean actual = auth.isBetween(investor, broker, stockHolding);
		assertFalse(actual);
	}
	
	@Test
	void removeAuthTest() {
		boolean actual = auth.removeAuth();
		assertFalse(actual);
	}
	
	@Test
	void testGetters() {
		AuthCapital authcapital = new AuthCapital(investor, broker, date1, date2, 10.0);
		
		boolean flag = false;
		if (authcapital.getAmount() == 10 && authcapital.getStartdate().isEqual(date1)
				&& authcapital.getEnddate().isEqual(date2) && authcapital.getId() == null
				&& authcapital.getInvestor().getName().equals("Giannhs") && authcapital.getBroker().getName().equals("Kwstas"))
			flag = true;
		
		Assertions.assertTrue(flag);
	}
	
	@Test
	void testSetters() {
		AuthStock authstocks = new AuthStock(null, null, null, date1, date2, 10);
		authstocks.setAmount(20);
		authstocks.setId(7);
		authstocks.setInvestor(investor);
		authstocks.setBroker(broker);
		authstocks.setStockholding(stockHolding);
		
		LocalDateTime date3 = LocalDateTime.now();
		authstocks.setStartdate(date3);
		LocalDateTime date4 = LocalDateTime.now();
		authstocks.setEnddate(date4);
		
		boolean flag = false;
		if (authstocks.getAmount() == 20 && authstocks.getStartdate().isEqual(date3)
				&& authstocks.getEnddate().isEqual(date4) && authstocks.getId() == 7 && authstocks.getStockHolding() != null
				&& authstocks.getInvestor().getName().equals("Giannhs") && authstocks.getBroker().getName().equals("Kwstas") )
			flag = true;
		
		Assertions.assertTrue(flag);
	}
}
