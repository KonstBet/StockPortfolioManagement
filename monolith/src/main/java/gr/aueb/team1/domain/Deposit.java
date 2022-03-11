package gr.aueb.team1.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@DiscriminatorValue("deposit")
public class Deposit extends Transaction {

	public Deposit() {super();}
	public Deposit(User user, Double amount, LocalDateTime date) {
		super(user, amount, date);
	}
}
