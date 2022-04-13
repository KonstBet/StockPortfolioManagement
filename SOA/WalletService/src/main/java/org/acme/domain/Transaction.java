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
	private Long id;
	
    @Column(name ="amount", precision = 10, scale = 4)
	private Double amount;
	
	@Column(name = "date", nullable = false)
	private LocalDateTime date;


	@ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
	@JoinColumn(name="walletid")
	@JsonbTransient
	private Wallet wallet;


	public Transaction() {}
	public Transaction(Wallet wallet, Double amount, LocalDateTime date) {
		super();
		this.wallet = wallet;
		this.amount = amount;
		this.date = date;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	//	public Integer getUserId() {
//		return userid;
//	}
//
//	public void setUserId(Integer userid) {
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
