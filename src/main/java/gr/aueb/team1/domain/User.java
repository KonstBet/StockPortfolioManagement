package gr.aueb.team1.domain;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;

import gr.aueb.team1.domain.Order.Action;
import gr.aueb.team1.domain.Order.Status;

@Entity
@Table(name="Users")
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
	
	@OneToMany(mappedBy="user",cascade = CascadeType.PERSIST)
	private Set<Transaction> transactions = new HashSet<Transaction>();
	
	@OneToMany(mappedBy="user",cascade = CascadeType.PERSIST)
	private Map<Stock, StockHolding> stockHoldings = new HashMap<Stock, StockHolding>();
	
	@OneToMany(mappedBy="user",cascade = CascadeType.PERSIST)
	private Set<Order> orders= new HashSet<Order>();
	
	@Column(name="balance", precision = 10, scale = 4)
	private Double balance;
		
//	List<Transaction> Transactions=new ArrayList<Transaction>();
	
	public User() {
		this.name = null;
		this.surname = null;
		this.email = null;
		this.phoneNo = null;
		this.balance = 0.0;
	}
	
	public User(String name, String surname, String email, String phoneNo) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phoneNo = phoneNo;
		this.balance = 0.0;
	}
	
	public Integer getId() {
		return this.id;
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
	
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	public String toString() {
		return "ID: " + getId() +
				"\nName: " + getName() + 
				"\nSurname: " + getSurname() + 
				"\nEmail: " + getEmail() + 
				"\nPhone: " + getPhoneNo();
	}

	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public void addStockHolding(Stock stock, StockHolding sh) {
		this.stockHoldings.put(stock, sh);
	}
	
	public void remStockHolding(Stock stock) {
		this.stockHoldings.remove(stock);
	}


	// Withdrawal to External Source
	public Boolean withdraw(Double amount) {
		if (getBalance() < amount || amount == 0) {
			return false;
		}
		transactions.add(new Withdrawal(this, amount, LocalDateTime.now()));
		// Double needs round up
		setBalance( (double) Math.round((getBalance() - amount)*100)/100 );
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
		Double fee= 0.1;
		Order order = new Order(this, stock, amount, fee, LocalDateTime.now(), Action.BUY, Status.PENDING);

		if (!order.applyOrder()) {
			return false;
		}
		this.orders.add(order);

		return true;
	}
	

	public Boolean sellStock(Stock stock, Integer amount) {
		Double fee = 0.1;
		Order order = new Order(this, stock, amount, fee, LocalDateTime.now(),Action.SELL, Status.PENDING);
		
		if (!order.applyOrder()) {
			return false;
		}
		this.orders.add(order);
		
		return true;
	}
	
	public Boolean limitOrder(Double limit, Stock stock, Integer amount, Action action) {
		Double fee = 0.1;
		if(action.equals(Action.BUY)) {
			AutomatedOrder ao = new AutomatedOrder(this, stock, amount, fee, LocalDateTime.now(), action, limit);
			if (getBalance() < ao.getOrderPrice()) {
				return false;
			}
			orders.add(ao);
		} else {
			if (!stockHoldings.containsKey(stock)) {
				return false;
			}
			StockHolding sh = stockHoldings.get(stock);
			// Check if the user has the amount to sell
			if (sh.getAmount() < amount) {
				return false;
			}
			AutomatedOrder ao = new AutomatedOrder(this, stock, amount, fee, LocalDateTime.now(), action, limit);
			orders.add(ao);
		}
		return true;
	}
	
	public String portfolioReport() {
		String bal = getBalance().toString();
		String res = "";
		res = "Balance: " + bal + "\n";
		for (Stock name: this.getStockHoldings().keySet()) {
		    String st = name.getCompanyName().toString();	
		    String amt = this.getStockHoldings().get(name).getAmount().toString();
		    String price = name.getOpen().toString();
		    res += st + " " + amt + " " + price +"\n";
		}
		return res;
		
	}
	
	public String orderReport() {
		//Money transactions
		String res="Money Transaction:\n";
		Iterator<Transaction> trans = transactions.iterator();
		while (trans.hasNext()) {
			Transaction tr=trans.next();
			if (LocalDateTime.now().getMonthValue() == tr.getDate().getMonthValue() || (tr.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()-1 && LocalDateTime.now().getDayOfMonth() <= tr.getDate().getDayOfMonth())) {
				String temp= tr.getClass() + " " + tr.getAmount() + " " + tr.getDate() + "\n";
				res += temp;
			}
		}
		
		//Orders
		res += "Stock Transactions\n";
		Iterator<Order> ords = orders.iterator();
		while (ords.hasNext()) {
			Order or = ords.next();
			if (LocalDateTime.now().getMonthValue() == or.getDate().getMonthValue() || (or.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()-1 && LocalDateTime.now().getDayOfMonth() <= or.getDate().getDayOfMonth())) {
				String temp = or.getStock().getCompanyName() + " " + or.getAmount() + " " + or.getOrderPrice() + "\n";
				res += temp;
			}	
		}
		return res;
	}
	
	

}
