package com.example.app.domain;
import javax.persistence.*;

@Entity
@DiscriminatorValue("I")
public class Investor extends User {
	
	@Column(name="committedBalance", length=100, nullable=false)
	private Double committedBalance;
	
	public Investor() {
		super();
		this.committedBalance = 0.0;
	}
	
	public Investor(String name, String surname, String email, String phoneNo, Double committedBalance) {
		super(name, surname, email, phoneNo);
		this.committedBalance = 0.0;
	}
	
	public Double getCommittedBalance() {
		return this.committedBalance;
	}
	
	public void setCommittedBalance(Double committedBalance) {
		this.committedBalance = committedBalance;
	}
	
}
