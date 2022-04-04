package org.acme.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("withdraw")
public class Withdrawal extends Transaction {

	public Withdrawal() {}
	public Withdrawal(Wallet wallet, Double amount, LocalDateTime date) {
		super(wallet, amount, date);
	}
}
