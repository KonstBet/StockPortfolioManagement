package com.example.app.domain;

public class Broker {
	private double brokerageFee;
	public Broker(){
		super();
		this.brokerageFee=0;
	}
	public Broker(String name, String surname, String email, String phoneNo, double brokerageFee) {
		super();
		this.brokerageFee=0;
	}
	public double getBrokerageFee() {
		return this.brokerageFee;
	}
	public void setBrokerageFee(double brokerageFee) {
		this.brokerageFee=brokerageFee;
	}
	
}
