package gr.aueb.team1.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@DiscriminatorValue("AuthCapital")
public class AuthCapital extends Authorization {

	@Column(name = "amount", nullable = false)
	private Double amount;

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

	public boolean giveNewAuthorization(Investor investor, Double amount, Broker broker) {
		if (amount > investor.getBalance()) {
			return false;
		}

		investor.setCommittedBalance(investor.getCommittedBalance() + amount);
		investor.setBalance(investor.getBalance() - amount);
		investor.getAuthorizations().add(this);
		broker.addAuthorization(this);
		return true;
	}

	public boolean giveToExistedAuthorization(AuthCapital ac) {
		if (ac.getAmount() > (this.getInvestor().getCommittedBalance())) {
			return false;
		}

		this.getInvestor().setCommittedBalance(this.getInvestor().getCommittedBalance() + ac.getAmount());
		this.setAmount(this.getAmount() + + ac.getAmount());
		return true;
	}

	public boolean existsAuthorizationToEqual(AuthCapital ac) {
		if (ac.getInvestor() == this.getInvestor() & ac.getBroker() == this.getBroker())
			return true;
		return false;
	}

	public boolean removeAuth() {
		getInvestor().setCommittedBalance(getInvestor().getCommittedBalance() - getAmount());
		getInvestor().setBalance(getInvestor().getBalance() + getAmount());
		return true;
	}
}
