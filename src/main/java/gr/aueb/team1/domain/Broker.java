package gr.aueb.team1.domain;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;

import gr.aueb.team1.domain.Order.Action;
import gr.aueb.team1.domain.Order.Status;

@Entity
@DiscriminatorValue("B")
public class Broker extends User {
	
	@Column(name="brokerageFee", length=20, nullable=false, columnDefinition = "double default 0.0")
	private Double brokerageFee;
	
	@OneToMany(mappedBy="broker")
	private Set<Authorization> authorizations = new HashSet<Authorization>();
	
	public Broker() {
		super();
		this.brokerageFee = 0.0;
	}
	
	public Broker(String name, String surname, String email, String phoneNo, Double brokerageFee) {
		super(name, surname, email, phoneNo);
		this.brokerageFee = 1.0;
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
	
	
	
	public Boolean buyForInvestor(AuthCapital authCapital, Stock stock, Integer amount) {
		Double fee = 0.1;
		Order order = new Order(authCapital.getInvestor(), stock, amount, fee, LocalDateTime.now(), Action.BUY, Status.PENDING);
		
		if (!order.applyBrokerOrder(authCapital)) {
			return false;
		}
		
		authCapital.getInvestor().addOrder(order);
		return true;
	}
	
	public Boolean sellForInvestor(AuthStock authStocks, Integer amount) {
		Double fee = 0.1;
		Order order = new Order(authStocks.getInvestor(), authStocks.getStockHolding().getStock(), amount, fee, LocalDateTime.now(), Action.SELL, Status.PENDING);
		if (!order.applyBrokerOrder(authStocks)) {
			return false;
		}
		
		authStocks.getInvestor().addOrder(order);
		return true;
	}
}
