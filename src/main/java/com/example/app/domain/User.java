package com.example.app.domain;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;

import com.example.app.domain.Order.Action;

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
	private Set<Transaction> transactions = new HashSet<Transaction>();
	
	@OneToMany(mappedBy="user")
	private Map<Stock, StockHolding> stockHoldings = new HashMap<Stock, StockHolding>();
	
	@OneToMany(mappedBy="user")
	private Set<Order> orders= new HashSet<Order>();
	
	@Column(name="balance", precision = 10, scale = 4)
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

	public Map<Stock,StockHolding> getStockHoldings() {
		return this.stockHoldings;
	}

	public void setStockHoldings(Map<Stock,StockHolding> stockHoldings) {
		this.stockHoldings = stockHoldings;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public void addStockHolding(Stock stock, StockHolding sh) {
		this.stockHoldings.put(stock, sh);
	}
		

	// Withdrawal to External Source
	public Boolean withdraw(Double amount) {
		if (getBalance() < amount || amount == 0) {
			return false;
		}
		transactions.add(new Withdrawal(this, amount, LocalDateTime.now()));
		// Double needs round up
		setBalance( (double) Math.round((getBalance() - amount)*100)/100 );
		System.out.print(getBalance());
		return true;
	}
	
	// Deposit from External Source
	public Boolean deposit(Double amount) {
		if(amount == 0) {
			return false;
		}
		transactions.add(new Deposit(this, amount, LocalDateTime.now()));
		setBalance(getBalance() + amount);
		return true;
	}
	
	public Boolean buyStock(Stock stock, Integer amount) {
		
		// Maybe make fee a constant?!
		Double fee = 0.1; 
		Order order = new Order(this, stock, amount, fee, LocalDateTime.now(), Action.BUY);
		Double orderPrice = order.getOrderPrice();
		
		// Not enough Balance
		if (getBalance() < orderPrice) {
			System.err.println("Not enough Balance");
			return false;
		}

		// New Balance (not the brand)
		setBalance(getBalance() - orderPrice);
		
		// Save Order
		orders.add(order);
		
		// Add stock to stock holdings
		if (this.stockHoldings.containsKey(stock)) {
			amount += this.stockHoldings.get(stock).getAmount();
			this.stockHoldings.put(stock, new StockHolding(amount, stock, this));
			return true;
		}
			
		this.stockHoldings.put(stock, new StockHolding(amount, stock, this));
		
		return true;
	}
	
	public Boolean sellStock(Stock stock, Integer amount) {
		// Maybe make fee a constant?!
		Double fee = 0.1;
		Order order = new Order(this, stock, amount, fee, LocalDateTime.now(), Action.SELL);
		Double orderPrice = order.getOrderPrice();

		// Check if the user has the stock holding
		if (!stockHoldings.containsKey(stock)) {
			return false;
		}
		
		StockHolding sh = stockHoldings.get(stock);
		
		// Check if the user has the amount to sell
		if (sh.getAmount() < amount) {
			return false;
		}
		sh.setAmount(sh.getAmount() - amount);
		
		// Check if the amount became 0
		if (sh.getAmount() == 0) {
			stockHoldings.remove(stock);
			orders.add(order);
			setBalance(getBalance() + orderPrice);
		}
		else {
			stockHoldings.put(stock, sh);
			orders.add(order);
			setBalance(getBalance() + orderPrice);
		}
		
		return true;
	}

	
//	public Boolean limitOrder(Double limit, Stock stock, Integer amount, Action action) {
//		Double fee = 0.1;
//		if(action.equals(Action.BUY)) {
//			AutomatedOrder ao = new AutomatedOrder(this, stock, amount, fee, LocalDateTime.now(), action, limit);
//			if (getBalance() < ao.getOrderPrice()) {
//				return false;
//			}
//			orders.add(ao);
//		} else {
//			if (!stockHoldings.containsKey(stock)) {
//				return false;
//			}
//			StockHolding sh = stockHoldings.get(stock);
//			// Check if the user has the amount to sell
//			if (sh.getAmount() < amount) {
//				return false;
//			}
//			AutomatedOrder ao = new AutomatedOrder(this, stock, amount, fee, LocalDateTime.now(), action, limit);
//			orders.add(ao);
//		}
//		return true;
//	}

}
