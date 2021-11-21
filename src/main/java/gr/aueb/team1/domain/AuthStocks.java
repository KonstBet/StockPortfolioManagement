package gr.aueb.team1.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@DiscriminatorValue("AuthStocks")
public class AuthStocks extends Authorization {

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="StockHoldingId", nullable = false)
	private StockHolding stockholding;

	public AuthStocks(Investor investor, StockHolding stockholding, Broker broker,
					  LocalDateTime startdate, LocalDateTime enddate, Integer amount) {
		
		super(investor, broker, startdate, enddate);
		this.amount = amount;
		this.stockholding = stockholding;
	}

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


	public boolean giveNewAuthorization(Investor investor, Integer amount, Broker broker, StockHolding stockHolding) {
		if (amount > stockHolding.getAmount()) { // amount > notcommitedAmount
			return false;
		}

		stockHolding.setCommittedAmount(stockHolding.getCommittedAmount()+amount);
		stockHolding.setAmount(stockHolding.getAmount()-amount);
		investor.getAuthorizations().add(this);
		broker.addAuthorization(this);
		return true;
	}

	public boolean giveToExistedAuthorization(AuthStocks ac) {
		if (ac.getAmount() > this.getStockholding().getAmount()) {
			return false;
		}

		this.getStockholding().setCommittedAmount(getStockholding().getCommittedAmount() + ac.getAmount());
		this.getStockholding().setAmount(this.getStockholding().getAmount()-amount);
		this.setAmount(this.getAmount() + +ac.getAmount());
		return true;
	}

	public boolean existsAuthorizationToEqual(AuthStocks ac) {
		if (ac.getInvestor() == this.getInvestor() & ac.getBroker() == this.getBroker() & ac.stockholding == this.stockholding)
			return true;
		return false;
	}

	public boolean removeAuth() {
		getStockholding().setCommittedAmount(getStockholding().getCommittedAmount() - getAmount());
		return true;
	}
}
