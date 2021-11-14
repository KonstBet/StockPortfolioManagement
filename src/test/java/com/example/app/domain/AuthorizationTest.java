package com.example.app.domain;

import java.time.LocalDateTime;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class AuthorizationTest {
	
	public static Investor investor;
	public static StockHolding stockHolding;
	public static Broker broker;
	public static LocalDateTime date1;
	public static LocalDateTime date2;
	
	@BeforeClass
	public static void setUpAllTests() {
		investor = new Investor();
		investor.setName("Giannhs");
		
		stockHolding = new StockHolding();
		
		broker = new Broker();
		broker.setName("Kwstas");
		
		date1 = LocalDateTime.now();
		date2 = LocalDateTime.now();
	}
	
	@Test
	public void testGetters() {
		AuthCapital authcapital = new AuthCapital(investor, broker, date1, date2, 10);
		
		boolean flag = false;
		if (authcapital.getAmount() == 10 & authcapital.getStartdate().isEqual(date1)
				& authcapital.getEnddate().isEqual(date2) & authcapital.getId() == null
				& authcapital.getInvestor().getName().equals("Giannhs") & authcapital.getBroker().getName().equals("Kwstas"))
			flag = true;
		
		Assertions.assertTrue(flag);
	}
	
	@Test
	public void testSetters() {
		AuthStocks authstocks = new AuthStocks(null, null, null, date1, date2, 10);
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
		if (authstocks.getAmount() == 20 & authstocks.getStartdate().isEqual(date3)
				& authstocks.getEnddate().isEqual(date4) & authstocks.getId() == 7 & authstocks.getStockholding() != null
				& authstocks.getInvestor().getName().equals("Giannhs") & authstocks.getBroker().getName().equals("Kwstas") )
			flag = true;
		
		Assertions.assertTrue(flag);
	}
}