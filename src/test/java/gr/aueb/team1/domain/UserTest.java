package gr.aueb.team1.domain;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.*;
import gr.aueb.team1.domain.Order.Action;
import gr.aueb.team1.domain.Order.Status;

public class UserTest {

	private User user;
	private Stock stock;
	private Stock stock2;
	private Stock stock3;
	private Integer amount;
	private StockHolding sh;
	private Order order;

	@BeforeEach
	void setUpTests() {
		user = new User("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
		user.setBalance(500.00);
		stock = new Stock("P200", "PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
		stock2 = new Stock("P224", "ALPHA", LocalDateTime.now(), 100.00, 200.99, 1000.00, 10.00, 2460.00);
		stock3 = new Stock("P104", "COSMOTE", LocalDateTime.now(), 29.00, 200.99, 1000.00, 10.00, 2460.00);
		amount = 10;
		sh = new StockHolding(amount, stock, user);
		user.addStockHolding(stock, sh);
		order = new Order();
		
	}
	
	@Test // More than current balance
	void withdrawTest1() {
		boolean expected = user.withdraw(500.01);
		assertFalse(expected);
	}
	
	@Test // Less than current balance
	void withdrawTest2() {
		user.withdraw(499.99);
		double actual = user.getBalance();
		double expected = 0.01;
		assertEquals(actual, expected);
	}
	
	@Test // Withdraw 0
	void withdrawTest3() {
		boolean actual = user.withdraw(0.0);
		assertFalse(actual);
	}
	

	
	@Test // Deposit to balance
	void depositTest1() {
		user.deposit(0.01);
		double actual = user.getBalance();
		double expected = 500.01;
		assertEquals(actual, expected);
	}
	
	@Test // Deposit to balance
	void depositTest2() {
		boolean expected = user.deposit(0.0);
		assertFalse(expected);
	}
	
	@Test // Not enough balance
	void buyStockTest1() {
		boolean balance = user.buyStock(stock2, 6);
		assertFalse(balance);
	}
	
	@Test // Buy new asset
	void buyStockTest2() {
		user.buyStock(stock3, 15);
		int actual = user.getStockHoldings().get(stock3).getAmount();
		int expected = 15;
		assertEquals(actual, expected);
	}
	
	@Test // Buy more of an asset
	void buyStockTest3() {
		user.buyStock(stock, 1);
		int actual = user.getStockHoldings().get(stock).getAmount();
		int expected = 11;
		assertEquals(actual, expected);
	}
	
	@Test // Not having the asset
	void sellStockTest1() {
		boolean asset = user.sellStock(stock2, 1);
		assertFalse(asset);
	}
	
	
	@Test // Not enough amount of the asset
	void sellStockTest2() {
		boolean amount = user.sellStock(stock, 11);
		assertFalse(amount);
	}
	
	@Test // Sell full asset
	void sellStockTest3() {
		Assertions.assertThrows(NullPointerException.class, ()-> { 
				user.sellStock(stock, 10);
				user.getStockHoldings().get(stock).getAmount();
			});	
	}
	
	@Test // Sell partial asset
	void sellStockTest4() {
		user.sellStock(stock, 9);
		int actual = user.getStockHoldings().get(stock).getAmount();
		int expected = 1;
		assertEquals(actual, expected);
	}

	@Test // Limit Order Buy with not enough balance
	void limitOrderTest1() {
		boolean actual = user.limitOrder(0.1, stock2, 100, Action.BUY);
		assertFalse(actual);
	}
	
	@Test // Limit Order Buy
	void limitOrderTest2() {
		boolean actual = user.limitOrder(0.1, stock, 10, Action.BUY);
		assertTrue(actual);
	}
	
	@Test // Limit Order Sell without having the stock
	void limitOrderTest3() {
		boolean actual = user.limitOrder(0.1, stock2, 11, Action.SELL);
		assertFalse(actual);
	}
	
	@Test // Limit Order Sell without enough amount
	void limitOrderTest4() {
		boolean actual = user.limitOrder(0.1, stock, 11, Action.SELL);
		assertFalse(actual);
	}
	
	@Test // Limit Order Sell 
	void limitOrderTest5() {
		boolean actual = user.limitOrder(0.1, stock, 9, Action.SELL);
		assertTrue(actual);
	}
	
	@Test 
	void addOrderTest() {
		user.addOrder(order);
		boolean actual = user.getOrders().contains(order);
		assertTrue(actual);
	}
	
	@Test 
	void toStringTest() {
		String s = user.toString();
		assertEquals("ID: " + user.getId() +
				"\nName: " + user.getName() + 
				"\nSurname: " + user.getSurname() + 
				"\nEmail: " + user.getEmail() + 
				"\nPhone: " + user.getPhoneNo(),s);
	}
	
	@Test 
	void getTransactionTest() {
		User u = new User();
		Set<Transaction> s = u.getTransactions();
		assertNotNull(s);
	}
	
	@Test
	void portfolioReportTest() {
		user.buyStock(stock2, 1);
		System.out.println(user.portfolioReport());
		Assertions.assertNotNull(user.portfolioReport());
	}
	
	@Test
	void orderReportTest() {
		user.deposit(100.0);
		user.withdraw(50.0);
		user.buyStock(stock2, 1);
		user.buyStock(stock3, 2);
		System.out.println(user.orderReport());
		Assertions.assertNotNull(user.orderReport());
	}
	
	@Test
	void orderReportTest2() {
		Order or = new Order(user, stock, 1, 0.1, LocalDateTime.of(2021, LocalDateTime.now().getMonthValue(), 12, 0, 0), Action.BUY, Status.COMPLETED);
		Order or1 = new Order(user, stock, 1, 0.1, LocalDateTime.of(2021, LocalDateTime.now().getMonthValue() - 1, LocalDateTime.now().getDayOfMonth(), 0, 0), Action.BUY, Status.COMPLETED);
		Order or2 = new Order(user, stock, 1, 0.1, LocalDateTime.of(2021, LocalDateTime.now().getMonthValue() - 1, 1, 0, 0), Action.BUY, Status.COMPLETED);
		user.addOrder(or);
		user.addOrder(or1);
		user.addOrder(or2);
		System.out.println(user.orderReport());
		Assertions.assertNotNull(user.orderReport());
	}

	@Test
	void GettersSetters() {
		User usr = new User();
		usr.setName("Nikos");
		usr.setSurname("Papadopoulos");
		usr.setEmail("nikosme@mailbox.gr");
		usr.setPhoneNo("3213456879");
		usr.setStockHoldings(null);
		usr.setBalance(1.0);
		usr.setTransactions(null);
		usr.setOrders(null);

		Assertions.assertTrue(usr.getName().equals("Nikos") && usr.getSurname().equals("Papadopoulos")
			&& usr.getEmail().equals("nikosme@mailbox.gr") && usr.getPhoneNo().equals("3213456879")
			&& usr.getBalance() == 1.0);

		Assertions.assertNull(usr.getStockHoldings());
		Assertions.assertNull(usr.getOrders());
		Assertions.assertNull(usr.getTransactions());
	}
}
	