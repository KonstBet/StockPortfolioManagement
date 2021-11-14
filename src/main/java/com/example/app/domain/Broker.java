package com.example.app.domain;
import java.util.*;
import javax.persistence.*;

@Entity
@DiscriminatorValue("B")
public class Broker extends User {
	
	@Column(name="brokerageFee", length=20, nullable=false)
	private Double brokerageFee;
	
	@OneToMany(mappedBy="broker")
	private Set<Authorization> authorizations=new HashSet<Authorization>();
	
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
	
}
