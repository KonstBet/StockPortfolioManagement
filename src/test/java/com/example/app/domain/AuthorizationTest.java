package com.example.app.domain;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class AuthorizationTest {
	
	@Test
	public void testGetters() {
		LocalDateTime date1 = LocalDateTime.now();
		LocalDateTime date2 = LocalDateTime.now();
		AuthCapital authcapital = new AuthCapital(date1, date2, 10);
		
		boolean flag = false;
		if (authcapital.getAmount() == 10 & authcapital.getStartdate().isEqual(date1)
				& authcapital.getEnddate().isEqual(date2) & authcapital.getId() == null
				& authcapital.getInvestor() == null& authcapital.getBroker() == null)
			flag = true;
		
		Assertions.assertTrue(flag);
	}
	
	@Test
	public void testSetters() {
		LocalDateTime date1 = LocalDateTime.now();
		LocalDateTime date2 = LocalDateTime.now();
		AuthStocks authstocks = new AuthStocks(date1, date2, 10);
		authstocks.setAmount(20);
		authstocks.setId(7);
		
		Investor investor = new Investor();
		investor.setName("Giannhs");
		authstocks.setInvestor(investor);
		
		Broker broker = new Broker();
		broker.setName("Kwstas");
		authstocks.setBroker(broker);
		
		LocalDateTime date3 = LocalDateTime.now();
		authstocks.setStartdate(date3);
		LocalDateTime date4 = LocalDateTime.now();
		authstocks.setEnddate(date4);
		
		boolean flag = false;
		if (authstocks.getAmount() == 20 & authstocks.getStartdate().isEqual(date3)
				& authstocks.getEnddate().isEqual(date4) & authstocks.getId() == 7 
				& authstocks.getInvestor().getName().equals("Giannhs") & authstocks.getBroker().getName().equals("Kwstas") )
			flag = true;
		
		Assertions.assertTrue(flag);
	}
}