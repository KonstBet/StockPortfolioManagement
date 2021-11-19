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
	
	
	public Set<Authorization> getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(Set<Authorization> authorizations) {
		this.authorizations = authorizations;
	}

	
	public Boolean giveCapitalAuthorization(Double amount, Broker broker, LocalDateTime endDate) {
		if (amount > this.getBalance()) {
			return false;
		}
			
		AuthCapital authCapital = new AuthCapital(this, broker, LocalDateTime.now(), endDate, amount);
		setCommittedBalance(getCommittedBalance() + amount);
		setBalance(getBalance() - amount);
		authorizations.add(authCapital);
		broker.addAuthorization(authCapital);
		return true;
	}
	
	public Boolean removeCapitalAuthorization(AuthCapital authCapital) {
		if (!authorizations.contains(authCapital)) {
			return false;
		}
		setCommittedBalance(getCommittedBalance() - authCapital.getAmount());
		setBalance(getBalance() + authCapital.getAmount());
		authorizations.remove(authCapital);
		authCapital.getBroker().removeAuthorization(authCapital);
		return true;
	}
	
	public Boolean giveStockAuthorization(Integer amount, StockHolding stockHolding, Broker broker, LocalDateTime endDate) {
		if (amount > (stockHolding.getAmount() - stockHolding.getCommittedAmount())) { // amount > notcommitedAmount
			return false;
		}
		AuthStocks authStocks = new AuthStocks(this, stockHolding, broker, LocalDateTime.now(), endDate, amount);
		stockHolding.setCommittedAmount(stockHolding.getCommittedAmount()+amount);
		authorizations.add(authStocks);
		
		return true;
	}
	
	public Boolean removeStockAuthorization(AuthStocks authStocks) {
		if (!authorizations.contains(authStocks)) {
			return false;
		}
		StockHolding stockHolding = authStocks.getStockholding();
		stockHolding.setCommittedAmount(stockHolding.getCommittedAmount() - authStocks.getAmount());
		authorizations.remove(authStocks);
		authStocks.getBroker().removeAuthorization(authStocks);
		return true;
	}
}
