package com.example.app.domain;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="StockHolding")
public class StockHolding {
	
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="amount", length=100, nullable=false)
	private Integer amount;
	
	@Column(name="committedAmount", length=100, nullable=false)
	private Integer committedAmount;
	
	@ManyToOne
	@JoinColumn(name="UserId", nullable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="StockId", nullable=false)
	private Stock stock;
	
	@OneToMany(mappedBy="user")
	private Set<AuthStocks> authStock=new HashSet<AuthStocks>();

	
	public StockHolding() {
		
	}
	
	public StockHolding(Integer amount, Stock stock, User user) {
		this.amount = amount;
		this.stock = stock;
		this.user = user;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getCommittedAmount() {
		return this.committedAmount;
	}

	public void setCommittedAmount(Integer committedAmount) {
		this.committedAmount = committedAmount;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Stock getStock() {
		return this.stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Set<AuthStocks> getAuthStock() {
		return this.authStock;
	}

	public void setAuthStock(Set<AuthStocks> authStock) {
		this.authStock = authStock;
	}
}
