package com.example.app.domain;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TransactionTest {
	
	@Test
	public void testGetters() {
		LocalDateTime date = LocalDateTime.now();
		Withdrawal withdrawal = new Withdrawal(10, date);
		
		boolean flag = false;
		if (withdrawal.getAmount() == 10 & withdrawal.getDate().isEqual(date)
				& withdrawal.getId() == null & withdrawal.getUser() == null)
			flag = true;
		
		Assertions.assertTrue(flag);
	}
	
	@Test
	public void testSetters() {
		LocalDateTime date = LocalDateTime.now();
		Deposit deposit = new Deposit(10, date);
		deposit.setAmount(20);
		deposit.setId(7);
		
		Investor investor = new Investor();
		investor.setName("Giannhs");
		deposit.setUser(investor);
		
		LocalDateTime date2 = LocalDateTime.now();
		deposit.setDate(date2);
		
		boolean flag = false;
		if (deposit.getAmount() == 20 & deposit.getDate().isEqual(date2)
				& deposit.getId() == 7 & deposit.getUser().getName().equals("Giannhs"))
			flag = true;
		
		Assertions.assertTrue(flag);
	}
}