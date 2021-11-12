package com.example.app.domain;

public class Broker extends User {
	
	private double brokerageFee;
	
	public Broker() {
		super();
		this.brokerageFee=0;
	}
	
	public Broker(String name, String surname, String email, String phoneNo, Double brokerageFee) {
		super(name, surname, email, phoneNo);
		this.brokerageFee=0;
	}
	
	public Double getBrokerageFee() {
		return this.brokerageFee;
	}
	
	public void setBrokerageFee(Double brokerageFee) {
		this.brokerageFee = brokerageFee;
	}
	
}
