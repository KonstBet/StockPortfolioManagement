package com.example.app.domain;

import java.time.LocalDateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class BrokerTest {
	
	private User user;
	private Stock stock;
	private Stock stock2;
	private Stock stock3;
	private Integer amount;
	private StockHolding sh;
	
	@BeforeEach
	public void setUpTests() {
		user = new User("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
		user.setBalance(500.00);
		stock = new Stock("P200", "PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
		stock2 = new Stock("P224", "ALPHA", LocalDateTime.now(), 100.00, 200.99, 1000.00, 10.00, 2460.00);
		stock3 = new Stock("P104", "COSMOTE", LocalDateTime.now(), 29.00, 200.99, 1000.00, 10.00, 2460.00);
		amount = 10;
		sh = new StockHolding(amount, stock, user);
		user.addStockHolding(stock, sh);
	}
	
	@Test
	public void buyStocksForInvestorTest(){
		
	}
	
	
	
	
}
