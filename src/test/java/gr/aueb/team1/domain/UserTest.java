package gr.aueb.team1.domain;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.*;

import gr.aueb.team1.domain.Stock;
import gr.aueb.team1.domain.StockHolding;
import gr.aueb.team1.domain.User;

public class UserTest {

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
	
	@Test // More than current balance
	public void withdrawTest1() {
		Boolean expected = user.withdraw(500.01);
		assertFalse(expected);
	}
	
	@Test // Less than current balance
	public void withdrawTest2() {
		user.withdraw(499.99);
		double actual = user.getBalance();
		double expected = 0.01;
		assertEquals(actual, expected);
	}
	
	@Test // Deposit to balance
	public void depositTest1() {
		user.deposit(0.01);
		double actual = user.getBalance();
		double expected = 500.01;
		assertEquals(actual, expected);
	}
	
	@Test // Deposit to balance
	public void depositTest2() {
		Boolean expected = user.deposit(0.0);
		assertFalse(expected);
	}
	
	@Test // Not enough balance
	public void buyStockCase1() {
		Boolean balance = user.buyStock(stock2, 6);
		assertFalse(balance);
	}
	
	@Test // Buy new asset
	public void buyStockCase2() {
		user.buyStock(stock3, 15);
		int actual = user.getStockHoldings().get(stock3).getAmount();
		int expected = 15;
		assertEquals(actual, expected);
	}
	
	@Test // Buy more of an asset
	public void buyStockCase3() {
		user.buyStock(stock, 1);
		int actual = user.getStockHoldings().get(stock).getAmount();
		int expected = 11;
		assertEquals(actual, expected);
	}
	
	@Test // Not having the asset
	public void sellStockCase1() {
		Boolean asset = user.sellStock(stock2, 1);
		assertFalse(asset);
	}
	
	
	@Test // Not enough amount of the asset
	public void sellStockCase2() {
		Boolean amount = user.sellStock(stock, 11);
		assertFalse(amount);
	}
	
	@Test // Sell full asset
	public void sellStockCase3() {
		Assertions.assertThrows(NullPointerException.class, ()-> { 
				user.sellStock(stock, 10);
				user.getStockHoldings().get(stock).getAmount();
			});	
	}
	
	@Test // Sell partial asset
	public void sellStockCase4() {
		user.sellStock(stock, 9);
		int actual = user.getStockHoldings().get(stock).getAmount();
		int expected = 1;
		assertEquals(actual, expected);
	}

}
	