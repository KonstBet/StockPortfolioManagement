package org.acme.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("withdraw")
public class Withdrawal extends Transaction {

	public Withdrawal() {}
	public Withdrawal(Integer userid, Double amount, LocalDateTime date) {
		super(userid, amount, date);
	}
}
