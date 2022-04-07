package org.acme.domain;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="Transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Transaction {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
    @Column(name ="amount", precision = 10, scale = 4)
	private Double amount;
	
	@Column(name = "date", nullable = false)
	private LocalDateTime date;


	@ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name="walletid")
	@JsonbTransient
	private Wallet wallet;

//	@Column(name = "userid", nullable = false)
//	private Integer userid;

	public Transaction() {}
	public Transaction(Wallet wallet, Double amount, LocalDateTime date) {
		//super();
		this.wallet = wallet;
		this.amount = amount;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Wallet getWallet() {
		return wallet;
	}
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	//	public Integer getUserid() {
//		return userid;
//	}
//
//	public void setUserid(Integer userid) {
//		this.userid = userid;
//	}

	
	public String toString() {
		return "ID: " + getId() +
				"\nAmount: " + getAmount() +  "€"
				+ "\nDate: " + getDate().toString()
				+ "\nUserid: " + getWallet().toString();
	}

//	public void remove() {
//		getUser().getTransactions().remove(this);
//		setUser(null);
//	}
}
