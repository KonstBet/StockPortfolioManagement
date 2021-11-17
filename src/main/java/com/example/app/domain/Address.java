package com.example.app.domain;
import javax.persistence.*;

@Embeddable
public class Address {
	
	@Column(name="street")
	private String street;
	
	@Column(name="number")
	private String number;
	
	@Column(name="zipCode")
	private String zipCode;

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
}
