package gr.aueb.team1.domain;

import java.time.LocalDateTime;
import org.junit.jupiter.api.*;

import javax.validation.constraints.AssertTrue;

public class TransactionTest {

	public User user;
	public User user2;
	public LocalDateTime date1;
	public LocalDateTime date2;
	
	@BeforeEach
	void setUpTests() {
		user = new User();
		user.setName("Giannhs");

		user2 = new User();
		user2.setName("Kwstas");

		date1 = LocalDateTime.now();
		date2 = LocalDateTime.now();
	}
	
	@Test
	void testGetters() {
		Withdrawal withdrawal = new Withdrawal(user, 10.00, date1);
		
		boolean flag = false;
		if (withdrawal.getAmount() == 10.00 && withdrawal.getDate().isEqual(date1)
				&& withdrawal.getId() == null && withdrawal.getUser().getName().equals("Giannhs"))
			flag = true;
		
		Assertions.assertTrue(flag);
	}
	
	@Test
	void testSetters() {
		Deposit deposit = new Deposit(user, 10.00, date1);
		
		deposit.setAmount(20.00);
		deposit.setId(7);
		deposit.setUser(user2);
		deposit.setDate(date2);
		
		boolean flag = false;
		if (deposit.getAmount() == 20.00 && deposit.getDate().isEqual(date2)
				&& deposit.getId() == 7 && deposit.getUser().getName().equals("Kwstas"))
			flag = true;
		
		Assertions.assertTrue(flag);
	}

	@Test
	void removeTest() {
		Deposit deposit = new Deposit(user, 10.00, date1);

		deposit.setAmount(20.00);
		deposit.setId(7);
		deposit.setUser(user2);
		deposit.setDate(date2);

		deposit.remove();
		Assertions.assertTrue(deposit.getUser() == null);
	}
}