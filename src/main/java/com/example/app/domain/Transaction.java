package com.example.app.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name="Transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "amount")
	private Integer amount;
	
	@Column(name = "date")
	private LocalDateTime date;
	
	@ManyToOne
	@JoinColumn(name="Userid", nullable = false)
	private User user;

	
	public Transaction(Integer amount, LocalDateTime date) {
		super();
		this.amount = amount;
		this.date = date;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
