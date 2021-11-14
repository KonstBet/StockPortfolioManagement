package com.example.app.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@DiscriminatorValue("transaction")
public class Withdrawal extends Transaction {

	public Withdrawal(User user,Integer amount, LocalDateTime date) {
		super(user, amount, date);
	}
}
