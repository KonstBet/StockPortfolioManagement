package com.example.app.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@DiscriminatorValue("deposit")
public class Deposit extends Transaction {

	public Deposit(User user, Double amount, LocalDateTime date) {
		super(user, amount, date);
	}
}
