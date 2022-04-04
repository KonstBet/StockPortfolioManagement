package org.acme.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("deposit")
public class Deposit extends Transaction {

	public Deposit() {super();}
	public Deposit(Wallet wallet, Double amount, LocalDateTime date) {
		super(wallet, amount, date);
	}
}
