package gr.aueb.team1.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@DiscriminatorValue("AuthStocks")
public class AuthStock extends Authorization {

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
	@JoinColumn(name="StockHoldingId")
	private StockHolding stockholding;

	public AuthStock(Investor investor, StockHolding stockholding, Broker broker,
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
	public StockHolding getStockHolding() {
		return this.stockholding;
	}
	public void setStockholding(StockHolding stockholding) {
		this.stockholding = stockholding;
	}


	@Override
	public boolean giveNewAuthorization(Investor investor, Integer amount, Broker broker, StockHolding stockHolding) {
		if (amount > stockHolding.getAmount()) { // amount > notcommitedAmount
			return false;
		}

		stockHolding.setCommittedAmount(stockHolding.getCommittedAmount()+amount);
		stockHolding.setAmount(stockHolding.getAmount()-amount);
		investor.getAuthorizations().add(this);
		broker.getAuthorizations().add(this);
		return true;
	}

	@Override
	public boolean updateAuthorization(Integer amount) {
		if (amount > this.getStockHolding().getAmount()) {
			return false;
		}

		this.getStockHolding().setCommittedAmount(getStockHolding().getCommittedAmount() + amount);
		this.getStockHolding().setAmount(this.getStockHolding().getAmount()-amount);
		this.setAmount(this.getAmount() + amount);
		return true;
	}

	@Override
	public boolean isBetween(Investor investor, Broker broker, StockHolding stockHolding) {
		if (investor == this.getInvestor() && broker == this.getBroker() && stockHolding == this.stockholding)
			return true;
		return false;
	}

	@Override
	public boolean removeAuth() {
		if (getStockHolding().getCommittedAmount() == 0) {
			return false;
		}
		getStockHolding().setCommittedAmount(getStockHolding().getCommittedAmount() - getAmount());
		getStockHolding().setAmount(getStockHolding().getAmount() + getAmount());
		getInvestor().getAuthorizations().remove(this);
		getBroker().getAuthorizations().remove(this);
		return true;
	}
}
