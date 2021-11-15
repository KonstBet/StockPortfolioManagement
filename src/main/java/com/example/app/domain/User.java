package com.example.app.domain;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="User")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name="type",
		discriminatorType=DiscriminatorType.STRING
)
public class User {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="name", length=30, nullable=false)
	private String name;
	
	@Column(name="surname", length=30, nullable=false)
	private String surname;
	
	@Column(name="email", length=200, nullable=false)
	private String email;
	
	@Column(name="phoneNo", length=20, nullable=false)
	private String phoneNo;
	
	@Embedded
	private Address address;
	
	@OneToMany(mappedBy="user")
	private Set<Transaction> transactions=new HashSet<Transaction>();
	
	@OneToMany(mappedBy="user")
	private Set<StockHolding> stockHoldings= new HashSet<StockHolding>();
	
	
	private Double balance;
	
//	List<Transaction> Transactions=new ArrayList<Transaction>();
	
	public User() {
		this.name = null;
		this.surname = null;
		this.email = null;
		this.phoneNo = null;
	}
	
	public User(String name, String surname, String email, String phoneNo) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phoneNo = phoneNo;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Set<StockHolding> getStockHoldings() {
		return stockHoldings;
	}

	public void setStockHoldings(Set<StockHolding> stockHoldings) {
		this.stockHoldings = stockHoldings;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	
	
	
	
	public Integer withdraw(Integer amount) {
		if (this.balance < amount)
			return 1;
		transactions.add(new Withdrawal(this, amount, LocalDateTime.now()));
		this.balance -= amount;
		return 0;
	}
	public Integer deposit(Integer amount) {
		transactions.add(new Deposit(this, amount, LocalDateTime.now()));
		this.balance += amount;
		return 0;
	}
}
