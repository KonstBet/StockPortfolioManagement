package gr.aueb.team1.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@DiscriminatorValue("AuthCapital")
public class AuthCapital extends Authorization {

	@Column(name = "amount", nullable = false)
	private Double amount;

	public AuthCapital() {super();}
	public AuthCapital(Investor investor, Broker broker, LocalDateTime startdate, LocalDateTime enddate, Double amount) {
		super(investor, broker, startdate, enddate);
		this.amount = amount;
	}

	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public AuthCapital giveNewAuthorization(Investor investor, Double amount, Broker broker) {
		if (amount > investor.getBalance()) {
			return null;
		}

		investor.setCommittedBalance(investor.getCommittedBalance() + amount);
		investor.setBalance(investor.getBalance() - amount);
		investor.getAuthorizations().add(this);
		broker.addAuthorization(this);
		return this;
	}
	
	@Override
	public AuthCapital updateAuthorization(Double amount) {
		if (amount > this.getInvestor().getBalance()) {
			return null;
		}

		this.getInvestor().setCommittedBalance(this.getInvestor().getCommittedBalance() + amount);
		this.getInvestor().setBalance(this.getInvestor().getBalance() - amount);
		this.setAmount(this.getAmount() + amount);
		return this;
	}

	@Override
	public boolean isBetween(Investor investor, Broker broker) {
		if (investor == this.getInvestor() && broker == this.getBroker()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean removeAuth() {
		if(getInvestor().getCommittedBalance() == 0.0) {
			return false;
		}
		getInvestor().setCommittedBalance(getInvestor().getCommittedBalance() - getAmount());
		getInvestor().setBalance(getInvestor().getBalance() + getAmount());

		getInvestor().getAuthorizations().remove(this);
		getBroker().getAuthorizations().remove(this);
		setBroker(null);
		setInvestor(null);
		return true;
	}
}
