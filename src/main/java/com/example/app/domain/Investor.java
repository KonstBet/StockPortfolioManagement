package com.example.app.domain;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;

@Entity
@DiscriminatorValue("I")
public class Investor extends User {
	
	@Column(name="committedBalance", length=100, nullable=false)
	private Double committedBalance;
	
	@OneToMany(mappedBy="investor")
	private Set<Authorization> authorizations=new HashSet<Authorization>();
	
	public Investor() {
		super();
		this.committedBalance = 0.0;
	}
	
	public Investor(String name, String surname, String email, String phoneNo) {
		super(name, surname, email, phoneNo);
		this.committedBalance = 0.0;
	}
	
	public Double getCommittedBalance() {
		return this.committedBalance;
	}
	
	public void setCommittedBalance(Double committedBalance) {
		this.committedBalance = committedBalance;
	}
	
	
	
	
	
	public Integer giveCapitalAuthorization(Integer amount, Broker broker, LocalDateTime endDate) {
		if (amount < this.getBalance())
			return 1;
		
		AuthCapital authCapital = new AuthCapital(this, broker, LocalDateTime.now(), endDate, amount);
		this.committedBalance += amount;
		authorizations.add(authCapital);
		
		return 0;
	}
	
	public Integer removeCapitalAuthorization(AuthCapital authCapital) {
		this.committedBalance -= authCapital.getAmount();
		authorizations.remove(authCapital);
		
		return 0;
	}
	
	public Integer giveStocksAuthorization(Integer amount, StockHolding stockHolding, Broker broker, LocalDateTime endDate) {
		//TODO ELEGXOS (if fail return 1), StockHolding
		AuthStocks authStocks = new AuthStocks(this, stockHolding, broker, LocalDateTime.now(), endDate, amount);
		//stockHolding.committedAmount += amount;
		authorizations.add(authStocks);
		
		return 0;
	}
	
	public Integer removeStockAuthorization(AuthStocks authStocks) {
		//TODO StockHolding
		StockHolding stockHolding = authStocks.getStockholding();
		//stockHolding.setCommittedAmount(stockHolding.getCommittedAmount -= authStocks.getAmount());
		authorizations.remove(authStocks);
		
		return 0;
	}
}
