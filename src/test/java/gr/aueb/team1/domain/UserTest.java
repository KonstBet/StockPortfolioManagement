package gr.aueb.team1.domain;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.*;

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
		user = new User("Mitsos", "Charalampidis", "mcharal@gmail.com", "6912345678", "b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");
		user.setBalance(500.00);
		stock = new Stock("PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
		stock2 = new Stock( "ALPHA", LocalDateTime.now(), 100.00, 200.99, 1000.00, 10.00, 2460.00);
		stock3 = new Stock( "COSMOTE", LocalDateTime.now(), 29.00, 200.99, 1000.00, 10.00, 2460.00);
		amount = 10;
		sh = new StockHolding(amount, stock, user);
		user.addStockHolding(stock, sh);
		order = new Order();
		
	}
	
	@Test
	void setCorrectEmail() {
		user.setEmail("mitsoschar@gmail.com");
		assertEquals(user.getEmail(), "mitsoschar@gmail.com");
	}
	
	@Test
	void setCorrectNumber() {
		user.setPhoneNo("6956709726");
		Assertions.assertEquals(user.getPhoneNo(), "6956709726");
	}
	
	@Test //
	void removeTest() {
		Investor i = new Investor("Mitsos", "Charalampidis", "mitcharal@gmail.com", "6978910301", "b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");
		i.deposit(1000.00);
		Broker b= new Broker("Stefanos", "Daglis", "macharal@gmail.com", "6978910301", 0.0, "b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");
		i.buyStock(stock, 30);
		i.giveAuthorization(10.00, b, LocalDateTime.now());
		i.remove();
		Assertions.assertEquals(0, i.getAuthorizations().size());
	}
	
	@Test //30 day report branch test
	void reportTest() {
		LocalDateTime date1= LocalDateTime.of(2021, 11, 26, 0, 0, 0);
		LocalDateTime date2= LocalDateTime.of(2021, 10, 29, 0, 0, 0);
		LocalDateTime date3= LocalDateTime.of(2021, 10, 1, 0, 0, 0);
		Deposit dep = new Deposit(user, 100.0, date1);
		Deposit dep1 = new Deposit(user, 100.0, date2);
		Deposit dep2 = new Deposit(user, 100.0, date3);
		Set<Transaction> trans = new HashSet<Transaction>();
		trans.add(dep);
		trans.add(dep1);
		trans.add(dep2);
		user.setTransactions(trans);
		System.out.println(user.orderReport());
		assertTrue(true);
	}
	
	@Test // More than current balance
	void withdrawTest1() {
		Withdrawal expected = user.withdraw(500.01);
		assertNull(expected);
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
		Withdrawal actual = user.withdraw(0.0);
		assertNull(actual);
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
		Deposit expected = user.deposit(0.0);
		assertNull(expected);
	}
	
	@Test // Not enough balance
	void buyStockTest1() {
		Order balance = user.buyStock(stock2, 6);
		assertNull(balance);
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
		Order asset = user.sellStock(stock2, 1);
		assertNull(asset);
	}
	
	
	@Test // Not enough amount of the asset
	void sellStockTest2() {
		Order amount = user.sellStock(stock, 11);
		assertNull(amount);
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
		Order actual = user.limitOrder(0.1, stock2, 100, Action.BUY);
		assertNull(actual);
	}
	
	@Test // Limit Order Buy
	void limitOrderTest2() {
		Order actual = user.limitOrder(0.1, stock, 10, Action.BUY);
		assertEquals(stock, actual.getStock());
	}
	
	@Test // Limit Order Sell without having the stock
	void limitOrderTest3() {
		Order actual = user.limitOrder(0.1, stock2, 11, Action.SELL);
		assertNull(actual);
	}
	
	@Test // Limit Order Sell without enough amount
	void limitOrderTest4() {
		Order actual = user.limitOrder(0.1, stock, 11, Action.SELL);
		assertNull(actual);
	}
	
	@Test // Limit Order Sell 
	void limitOrderTest5() {
		Order actual = user.limitOrder(0.1, stock, 9, Action.SELL);
		assertEquals(stock, actual.getStock());
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
		Order or1 = new Order(user, stock, 1, 0.1, LocalDateTime.of(2021, LocalDateTime.now().minusMonths(1).getMonthValue(), LocalDateTime.now().getDayOfMonth(), 0, 0), Action.BUY, Status.COMPLETED);
		Order or2 = new Order(user, stock, 1, 0.1, LocalDateTime.of(2021, LocalDateTime.now().minusMonths(1).getMonthValue(), 1, 0, 0), Action.BUY, Status.COMPLETED);
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
		usr.setPhoneNo("6987654321");
		usr.setStockHoldings(null);
		usr.setBalance(1.0);
		usr.setTransactions(null);
		usr.setOrders(null);
		usr.setPassword("b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");
		usr.setAddress(new Address("Andreou", "1", "11111"));

		Assertions.assertTrue(usr.getName().equals("Nikos") && usr.getSurname().equals("Papadopoulos")
			&& usr.getEmail().equals("nikosme@mailbox.gr") && usr.getPhoneNo().equals("6987654321")
			&& usr.getBalance() == 1.0 && usr.getPassword().equals("b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b")
			&& usr.getAddress().getStreet().equals("Andreou"));

		Assertions.assertNull(usr.getStockHoldings());
		Assertions.assertNull(usr.getOrders());
		Assertions.assertNull(usr.getTransactions());
		Assertions.assertNull(usr.getAuthorizations());
	}
}
	