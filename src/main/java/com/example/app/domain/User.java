package com.example.app.domain;

public class User {
	
	private String name;
	private String surname;
	private String email;
	private String phoneNo;
//	private double balance;
//	List<Transaction> Transactions=new ArrayList<Transaction>();
	
	public User() {
		this.name = null;
		this.surname = null;
		this.email = null;
		this.phoneNo = null;
	}
	
	public User(String name, String surname, String email, String phoneNo) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phoneNo = phoneNo;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPhoneNo() {
		return this.phoneNo;
	}
	
//	public double getBalance() {

	public void setName(String name) {
		this.name = name;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	
}
