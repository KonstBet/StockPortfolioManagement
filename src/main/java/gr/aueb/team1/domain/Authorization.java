package gr.aueb.team1.domain;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name="Authorizations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "AuthType", discriminatorType = DiscriminatorType.STRING)
public class Authorization {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "startdate", nullable = false)
	private LocalDateTime startdate;
	
	@Column(name = "enddate", nullable = false)
	private LocalDateTime enddate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Investorid", nullable = false)
	private Investor investor;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Brokerid", nullable = false)
	private Broker broker;
	
	public Authorization(Investor investor, Broker broker,LocalDateTime startdate, LocalDateTime enddate) {
		super();
		this.investor = investor;
		this.broker = broker;
		this.startdate = startdate;
		this.enddate = enddate;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDateTime getStartdate() {
		return startdate;
	}
	public void setStartdate(LocalDateTime startdate) {
		this.startdate = startdate;
	}
	public LocalDateTime getEnddate() {
		return enddate;
	}
	public void setEnddate(LocalDateTime enddate) {
		this.enddate = enddate;
	}
	public Investor getInvestor() {
		return investor;
	}
	public void setInvestor(Investor investor) {
		this.investor = investor;
	}
	public Broker getBroker() {
		return broker;
	}
	public void setBroker(Broker broker) {
		this.broker = broker;
	}

	public boolean giveNewAuthorization(Investor investor, Double amount, Broker broker ) {return false;}
	public boolean giveNewAuthorization(Investor investor, Integer amount, Broker broker, StockHolding stockHolding) {return false;}
	public boolean giveToExistedAuthorization(Double amount) {return false;}//AuthCapital
	public boolean giveToExistedAuthorization(Integer amount) {return false;}//AuthStocks
	public boolean isBetween(Investor investor, Broker broker) {return false;}//AuthCapital
	public boolean isBetween(Investor investor, Broker broker, StockHolding stockHolding) {return false;}//AuthStocks
	public boolean removeAuth() {return false;}
}
