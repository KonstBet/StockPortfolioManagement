package gr.aueb.team1.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@DiscriminatorValue("withdraw")
public class Withdrawal extends Transaction {

	public Withdrawal() {}
	public Withdrawal(User user, Double amount, LocalDateTime date) {
		super(user, amount, date);
	}
}
