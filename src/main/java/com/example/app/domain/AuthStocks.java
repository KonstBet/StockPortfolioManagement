package com.example.app.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name="AuthStocks")
public class AuthStocks extends Authorization {

	public AuthStocks(LocalDateTime startdate, LocalDateTime enddate, Integer amount) {
		super(startdate, enddate, amount);
	}
	
	@ManyToOne
	@JoinColumn(name="StockHolding", nullable = false)
	private StockHolding user;
}
