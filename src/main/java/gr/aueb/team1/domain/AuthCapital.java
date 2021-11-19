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
}
