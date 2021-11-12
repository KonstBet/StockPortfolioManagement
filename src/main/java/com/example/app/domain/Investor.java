package com.example.app.domain;

public class Investor extends User {
	private double committedBalance;
	public Investor(){
		super();
		this.committedBalance=0;
	}
	public Investor(String name, String surname, String email, String phoneNo, double committedBalance) {
		super();
		this.committedBalance=0;
	}
	public double getCommittedBalance() {
		return this.committedBalance;
	}
	public void setCommittedBalance(double committedBalance) {
		this.committedBalance=committedBalance;
	}
	
}
