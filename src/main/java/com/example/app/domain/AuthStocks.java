package com.example.app.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@DiscriminatorValue("AuthStocks")
public class AuthStocks extends Authorization {

	@Column(name = "amount")
	private Integer amount;

	public AuthStocks(Investor investor, StockHolding stockholding, Broker broker, 
			LocalDateTime startdate, LocalDateTime enddate, Integer amount) {
		
		super(investor, broker, startdate, enddate);
		this.amount = amount;
		this.stockholding = stockholding;
	}
	
	@ManyToOne
	@JoinColumn(name="StockHolding", nullable = false)
	private StockHolding stockholding;

	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public StockHolding getStockholding() {
		return this.stockholding;
	}
	public void setStockholding(StockHolding stockholding) {
		this.stockholding = stockholding;
	}
}
