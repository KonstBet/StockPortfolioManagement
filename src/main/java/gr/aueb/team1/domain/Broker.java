package gr.aueb.team1.domain;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;

import gr.aueb.team1.domain.Order.Action;
import gr.aueb.team1.domain.Order.Status;

@Entity
@DiscriminatorValue("B")
public class Broker extends User {
	
	@Column(name="brokerageFee", length=20, nullable=false)
	private Double brokerageFee;
	
	@OneToMany(mappedBy="broker")
	private Set<Authorization> authorizations = new HashSet<Authorization>();
	
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

	public Set<Authorization> getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(Set<Authorization> authorizations) {
		this.authorizations = authorizations;
	}

	public void addAuthorization(Authorization auth) {
		this.authorizations.add(auth);
	}
	
	public void removeAuthorization(Authorization auth) {
		this.authorizations.remove(auth);
	}
	
	
	
	public Boolean buyStocksForInvestor(AuthCapital authCapital, Stock stock, Integer amount) {
		if (authorizations.contains(authCapital)) {
			// Make a new ORDER and StockHolding by authCapital.getInvestor
			Double fee = 0.1;
			Order ord = new Order(authCapital.getInvestor(), stock, amount, fee, LocalDateTime.now(), Action.BUY, Status.COMPLETED);
			Double orderPrice = ord.getOrderPrice();
			
			if (orderPrice > authCapital.getAmount()) {
				return false;
			}
			authCapital.getInvestor().addOrder(ord);
			
			// Add stock to stock holdings
			if (authCapital.getInvestor().getStockHoldings().containsKey(stock)) 
			{
				Integer newAmount = authCapital.getInvestor().getStockHoldings().get(stock).getAmount() + amount;
				authCapital.getInvestor().addStockHolding(stock, new StockHolding(newAmount, stock, authCapital.getInvestor()));
			} 
			else 
			{
				authCapital.getInvestor().addStockHolding(stock, new StockHolding(amount, stock, authCapital.getInvestor()));
			}
			

			// Make a new AuthStocks by authCapital.getInvestor
			AuthStocks auths = new AuthStocks(authCapital.getInvestor(), authCapital.getInvestor().getStockHoldings().get(stock), authCapital.getBroker(), 
					LocalDateTime.now(), authCapital.getEnddate(), amount);
			
			authorizations.add(auths);
			authCapital.getInvestor().giveAuthorization(amount, authCapital.getInvestor().getStockHoldings().get(stock),
					authCapital.getBroker(), authCapital.getEnddate());
			

			// Delete authCapital
			Double newMoney = authCapital.getAmount() - orderPrice;
			authCapital.getInvestor().removeAuthorization(authCapital);
			authCapital.getInvestor().giveAuthorization(newMoney, authCapital.getBroker(), authCapital.getEnddate());
			return true;
		}
		return false;
	}
	
	public Boolean sellStocksForInvestor(AuthStocks authStocks, Integer amount) {
		//TODO IMPLEMENTATION
		if (authorizations.contains(authStocks)) {
			//Make a new ORDER by authstocks.getInvestor
			Double fee=0.1;
			Order ord=new Order(authStocks.getInvestor(), authStocks.getStockholding().getStock(), amount, fee, LocalDateTime.now(), Action.SELL, Status.COMPLETED);
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
