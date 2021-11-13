package com.example.app.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name="Authorizations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "AuthType", discriminatorType = DiscriminatorType.STRING)
public class Authorization {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "startdate")
	private LocalDateTime startdate;
	
	@Column(name = "enddate")
	private LocalDateTime enddate;
	
	@Column(name = "amount")
	private Integer amount;
	
	@ManyToOne
	@JoinColumn(name="Investorid", nullable = false)
	private Investor investor;
	
	@ManyToOne
	@JoinColumn(name="Brokerid", nullable = false)
	private Broker broker;
	
	public Authorization(LocalDateTime startdate, LocalDateTime enddate, Integer amount) {
		super();
		this.startdate = startdate;
		this.enddate = enddate;
		this.amount = amount;
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
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
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
}
