package com.example.app.domain;

import java.time.LocalDateTime;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TransactionTest {
	
	public static User user;
	public static User user2;
	public static LocalDateTime date1;
	public static LocalDateTime date2;
	
	@BeforeClass
	public static void setUpAllTests() {
		user = new User();
		user.setName("Giannhs");
		
		user2 = new User();
		user2.setName("Kwstas");
		
		date1 = LocalDateTime.now();
		date2 = LocalDateTime.now();
	}
	
	@Test
	public void testGetters() {
		Withdrawal withdrawal = new Withdrawal(user, 10, date1);
		
		boolean flag = false;
		if (withdrawal.getAmount() == 10 & withdrawal.getDate().isEqual(date1)
				& withdrawal.getId() == null & withdrawal.getUser().getName().equals("Giannhs"))
			flag = true;
		
		Assertions.assertTrue(flag);
	}
	
	@Test
	public void testSetters() {
		Deposit deposit = new Deposit(user, 10, date1);
		
		deposit.setAmount(20);
		deposit.setId(7);
		deposit.setUser(user2);
		deposit.setDate(date2);
		
		boolean flag = false;
		if (deposit.getAmount() == 20 & deposit.getDate().isEqual(date2)
				& deposit.getId() == 7 & deposit.getUser().getName().equals("Kwstas"))
			flag = true;
		
		Assertions.assertTrue(flag);
	}
}