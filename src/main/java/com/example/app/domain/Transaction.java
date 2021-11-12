package com.example.app.domain;

import java.time.LocalDateTime;

public abstract class Transaction {
	
	private Integer id;
	private Integer amount;
	private LocalDateTime date;
	
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
}
