package com.example.app.domain;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;

import com.example.app.domain.Order.Action;

@Entity
@DiscriminatorValue("B")
public class Broker extends User {
	
	@Column(name="brokerageFee", length=20, nullable=false)
	private Double brokerageFee;
	
	@OneToMany(mappedBy="broker")
	private Set<Authorization> authorizations=new HashSet<Authorization>();
	
	public Broker() {
		super();
		this.brokerageFee=0.0;
	}
	
	public Broker(String name, String surname, String email, String phoneNo, Double brokerageFee) {
		super(name, surname, email, phoneNo);
		this.brokerageFee=0.0;
	}
	
	public Double getBrokerageFee() {
		return this.brokerageFee;
	}
	
	public void setBrokerageFee(Double brokerageFee) {
		this.brokerageFee = brokerageFee;
	}
	
	
	
	
	public Boolean buyStocksForInvestor(AuthCapital authCapital, Stock stock, Integer amount) {
		//TODO IMPLEMENTATION
		if (authorizations.contains(authCapital)) {
			//Make a new ORDER and StockHolding by authCapital.getInvestor
			Double fee=0.1;
			Order ord=new Order(authCapital.getInvestor(), stock, amount, fee, LocalDateTime.now(), Action.BUY);
			Double orderPrice=ord.getOrderPrice();
			if (orderPrice>authCapital.getAmount())
				return false;
			authCapital.getInvestor().addOrder(ord);
			
			// Add stock to stock holdings
			if (authCapital.getInvestor().getStockHoldings().containsKey(stock)) {
				Integer newAmount=authCapital.getInvestor().getStockHoldings().get(stock).getAmount()+amount;
				authCapital.getInvestor().addStockHolding(stock, new StockHolding(newAmount, stock, authCapital.getInvestor()));
			}
			
			authCapital.getInvestor().addStockHolding(stock, new StockHolding(amount, stock, authCapital.getInvestor()));
			//Make a new AuthStocks by authCapital.getInvestor
			AuthStocks auths=new AuthStocks(authCapital.getInvestor(), authCapital.getInvestor().getStockHoldings().get(stock), authCapital.getBroker(), 
					LocalDateTime.now(), authCapital.getEnddate(), amount);
			authorizations.add(auths);
			

			//Delete authCapital
			Double newMoney= authCapital.getAmount()-orderPrice;
			if (newMoney!=0) {
				authCapital.getInvestor().giveCapitalAuthorization(newMoney, authCapital.getBroker(), authCapital.getEnddate());
				authorizations.remove(authCapital);
				return true;
			}
			authorizations.remove(authCapital);
			return true;
		}
		return false;
	}
	public Boolean sellStocksForInvestor(AuthStocks authStocks, Integer amount) {
		//TODO IMPLEMENTATION
		if (authorizations.contains(authStocks)) {
			//Make a new ORDER by authstocks.getInvestor
			Double fee=0.1;
			Order ord=new Order(authStocks.getInvestor(), authStocks.getStockholding().getStock(), amount, fee, LocalDateTime.now(), Action.SELL);
			Double orderPrice=ord.getOrderPrice();
			if (!authStocks.getInvestor().getStockHoldings().containsKey(authStocks.getStockholding().getStock())) {
				return false;
			}
			
			//Make a new AuthCapital by authCapital.getInvestor
			//Delete authstocks
			return true;			
		}

			
		return false;
	}
}
