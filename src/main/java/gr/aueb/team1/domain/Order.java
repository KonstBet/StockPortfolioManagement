package gr.aueb.team1.domain;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name="orders")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name="type",
		discriminatorType=DiscriminatorType.STRING
)
public class Order {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="amount", nullable=false)
	private Integer amount;
		
	@Column(name="fee", precision = 10, scale = 4)
	private Double fee;
	
	@Column(name="date")
	private LocalDateTime date;
	
	@Column(name="orderprice", precision = 10, scale = 4)
	private Double orderPrice;
	
    @Enumerated(EnumType.STRING)
    @Column(name="action")
	private Action action;
    
    @Enumerated(EnumType.STRING)
    @Column(name="status")
	private Status status;
    
    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name="stockid")
    protected Stock stock;
    
    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name="userid")
    protected User user;
    
	enum Action {
		BUY,
		SELL
	}
		
    enum Status {
		PENDING,
		COMPLETED
	}
	
	public Order() {
		
	}
	
	public Order(User user, Stock stock, Integer amount, Double fee, LocalDateTime date, Action action, Status status) {
		this.stock=stock;
		this.user= user;
		this.amount = amount;
		this.fee = fee;
		this.date = date;
		this.action = action;
		this.status = status;
		if (action.equals(Action.BUY)) {
			this.orderPrice = this.calculatePriceB();
		} else {
			this.orderPrice = this.calculatePriceS();
		}
	}

	public Integer getId() {
		return id;
	}
	
	
	public Integer getAmount() {
		return this.amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
		
	public Double getFee() {
		return fee;
	}
	
	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	public LocalDateTime getDate() {
		return this.date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Action getAction() {
		return this.action;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Double getOrderPrice() {
		return this.orderPrice;
	}
	
	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	private Double calculatePriceB() {
		return stock.getOpen()*getAmount() + Math.max(6, getAmount()*stock.getOpen()*fee);
	}
	
	private Double calculatePriceS() {
		return stock.getOpen()*getAmount() - Math.max(6, getAmount()*stock.getOpen()*fee);
	}
	
	public String toString() {
		return "ID: " + getId() +
				"\nAmount: " + getAmount() + 
				"\nFee: " + getFee() + "%" +
				"\nDate: " + getDate().toString() + 
				"\nAction: " + getAction() + 
				"\nOrder Price: " + getOrderPrice() + "€";
	}
	
	public Boolean applyOrder() {
		
		if (this.status==Status.COMPLETED) {
			return false;
		}
				
		// Check if balance is enough
		if (this.action==Action.BUY) {
			
			if (user.getBalance() < getOrderPrice()) {
				//System.err.println("Not enough Balance");
				return false;
			}
			buy();
			
		} else {		
			// Check stock holdings for stock
			if (!user.getStockHoldings().containsKey(this.stock)) {
				return false;
			}
			
			// Retrieve stock holding
			StockHolding sh = user.getStockHoldings().get(stock);
			
			if (sh.getAmount() < this.amount) {
				return false;
			}
			sell(sh);
		}
		return true;
	}

	protected void buy() {
		user.setBalance(user.getBalance() - this.getOrderPrice());
		if (user.getStockHoldings().containsKey(this.stock)) {
			this.amount += user.getStockHoldings().get(this.stock).getAmount();
			Integer temp = user.getStockHoldings().get(this.stock).getCommittedAmount();
			user.addStockHolding(this.stock, new StockHolding(this.amount, this.stock, user, temp));
		} else {
			user.addStockHolding(this.stock, new StockHolding(this.amount, this.stock, user, 0));
		}
		
		stock.getOrders().add(this);
		this.status = Status.COMPLETED;
	}
	
	protected void sell(StockHolding sh) {
		user.setBalance(user.getBalance() + getOrderPrice());
		sh.setAmount(sh.getAmount() - amount);
		
		if (sh.getAmount() == 0 && sh.getCommittedAmount() == 0) {
			user.remStockHolding(stock);
		}
		else {
			user.addStockHolding(this.stock, sh);
		}
		
		stock.getOrders().add(this);
		this.status = Status.COMPLETED;
	}
	
	public boolean applyBrokerOrder(AuthCapital auth) {
		if (this.status == Status.COMPLETED) {
			return false;
		}
		
		double bfee = auth.getBroker().getBrokerageFee()*this.getOrderPrice()/100;
		setOrderPrice(bfee + this.getOrderPrice());
		
		if (auth.getAmount() < this.getOrderPrice()) {
			return false;
		}
		

		auth.getInvestor().giveAuthorization(-this.getOrderPrice(), auth.getBroker(), auth.getEnddate());
		auth.getInvestor().setBalance(auth.getInvestor().getBalance()-this.getOrderPrice());
		
		if (auth.getInvestor().getStockHoldings().containsKey(this.stock)) {
			StockHolding stockHol = auth.getInvestor().getStockHoldings().get(stock);			
			stockHol.setAmount(stockHol.getAmount()+this.amount);
			user.addStockHolding(this.stock, stockHol);
		} else {
			user.addStockHolding(this.stock, new StockHolding(0, this.stock, user, this.amount));
		}
		auth.getInvestor().giveAuthorization(this.amount, auth.getInvestor().getStockHoldings().get(this.stock) , auth.getBroker(), auth.getEnddate());
		
		auth.getBroker().setBalance(auth.getBroker().getBalance() + bfee);
		
		stock.getOrders().add(this);
		this.status = Status.COMPLETED;
		return true;
	}
	
	public boolean applyBrokerOrder(AuthStock auth){
		
		if (!user.getStockHoldings().containsKey(this.stock)) {
			return false;
		}
		
		StockHolding sh = user.getStockHoldings().get(stock);
		
		if (sh.getCommittedAmount() < this.amount) {
			return false;
		}
		
		double bfee = auth.getBroker().getBrokerageFee()*this.getOrderPrice()/100;
		setOrderPrice(this.getOrderPrice() - bfee);
		
		
		auth.getInvestor().giveAuthorization(-this.getAmount(), sh, auth.getBroker(), auth.getEnddate());
		auth.getInvestor().giveAuthorization(this.getOrderPrice(), auth.getBroker(), auth.getEnddate());	
		sh.setAmount(sh.getAmount()-this.getAmount());


		if (sh.getAmount() == 0 && sh.getCommittedAmount() == 0) {
			System.out.println("done");
			auth.getInvestor().remStockHolding(stock);
		} 

		auth.getBroker().setBalance(auth.getBroker().getBalance() + bfee);
		
		stock.getOrders().add(this);
		this.status = Status.COMPLETED;
		return true;
		
	}
	

}
	
	
	
	

