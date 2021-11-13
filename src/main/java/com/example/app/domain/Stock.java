package com.example.app.domain;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="stocks")
public class Stock 
{
	@Id
	@Column(name="id", length=6, unique = true, nullable = false)
	private String id;
	
	@Column(name="company", length=40, nullable=false)
	private String companyName;
	
    @Temporal(TemporalType.DATE)
	private Date date;
	
    @Column(name ="open", precision = 10, scale = 4)
	private Float open;
	
	@Column(name="close", precision = 10, scale = 4)
	private Float close;
	
	@Column(name="high", precision = 10, scale = 4)
	private Float high;
	
	@Column(name="low", precision = 10, scale = 4)
	private Float low;
	
	@Column(name="vol", precision = 10, scale = 4)
	private Float vol;
	
	public Stock() {
		
	}
	
	public Stock(String id, String companyName, Date date,
			Float open, Float close, Float high, Float low, Float vol) 
	{
		this.id = id;
		this.companyName = companyName;
		this.date = date;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.vol = vol;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getOpen() {
		return open;
	}

	public void setOpen(Float open) {
		this.open = open;
	}

	public Float getClose() {
		return close;
	}

	public void setClose(Float close) {
		this.close = close;
	}

	public Float getHigh() {
		return high;
	}

	public void setHigh(Float high) {
		this.high = high;
	}

	public Float getLow() {
		return low;
	}

	public void setLow(Float low) {
		this.low = low;
	}

	public Float getVol() {
		return vol;
	}

	public void setVol(Float vol) {
		this.vol = vol;
	}
	
	public String toString() {
		return "ID: " + getId() + 
			   "\nCompany Name: " + getCompanyName() + 
			   "\nDate: " + getDate().toString() +
			   "\nOpen: " + getOpen() + "€" +
			   "\nClose: " + getClose() + "€" +
			   "\nHigh: " + getHigh() + "€" +
			   "\nLow: " + getLow() + "€" +
			   "\nVol: " + getVol() + "€";
	}
}

