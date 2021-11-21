package gr.aueb.team1.domain;
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


	public Boolean giveAuthorization(Double amount, Broker broker, LocalDateTime endDate) {
		Iterator<Authorization> iterator = authorizations.iterator();
		Authorization ac;
		while (iterator.hasNext()) {
				ac = iterator.next();
				if (ac.existsAuthorizationToEqual(this,broker)) {

					return ac.giveToExistedAuthorization(amount);
				}
		}

		AuthCapital authCapital = new AuthCapital(this, broker, LocalDateTime.now(), endDate, amount);
		return authCapital.giveNewAuthorization(this,amount,broker);
	}

	public Boolean giveAuthorization(Integer amount, StockHolding stockHolding, Broker broker, LocalDateTime endDate) {
		Iterator<Authorization> iterator = authorizations.iterator();
		Authorization as;
		while (iterator.hasNext()) {
				as = iterator.next();
				if (as.existsAuthorizationToEqual(this,broker,stockHolding)) {

					return as.giveToExistedAuthorization(amount);
				}
		}

		AuthStocks authStocks = new AuthStocks(this, stockHolding, broker, LocalDateTime.now(), endDate, amount);
		return authStocks.giveNewAuthorization(this,amount,broker,stockHolding);
	}

	public Boolean removeAuthorization(Authorization auth) {
		if (!authorizations.contains(auth)) {
			return false;
		}

		auth.removeAuth();
		authorizations.remove(auth);
		auth.getBroker().removeAuthorization(auth);
		return true;
	}
}
