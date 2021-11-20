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
	
	@Column
	private LocalDateTime date;
	
	@Column(name="orderprice", precision = 10, scale = 4)
	private Double orderPrice;
	
    @Enumerated(EnumType.STRING)
    @Column(name="action")
	private Action action;
    
    @Enumerated(EnumType.STRING)
    @Column(name="status")
	private Status status;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="stockid")
    protected Stock stock;
    
    @ManyToOne(fetch=FetchType.LAZY)
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
	
	public void setId(Integer id) {
		this.id = id;
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
		user.setBalance(user.getBalance() - getOrderPrice());
		if (user.getStockHoldings().containsKey(this.stock)) {
			this.amount += user.getStockHoldings().get(this.stock).getAmount();
			user.addStockHolding(this.stock, new StockHolding(this.amount, this.stock, user));
		} else {
			user.addStockHolding(this.stock, new StockHolding(this.amount, this.stock, user));
		}
		this.status = Status.COMPLETED;
	}
	
	protected void sell(StockHolding sh) {
		user.setBalance(user.getBalance() + getOrderPrice());
		sh.setAmount(sh.getAmount() - amount);
		
		if (sh.getAmount() == 0) {
			user.remStockHolding(stock);
		}
		else {
			user.addStockHolding(this.stock, sh);
		}
		this.status = Status.COMPLETED;
	}
	
	public boolean applyBrokerBuy(AuthCapital auth) {
		if (this.status == Status.COMPLETED) {
			return false;
		}
		
		if (auth.getAmount() < this.getOrderPrice()) {
			return false;
		}
		
		auth.getInvestor().removeAuthorization(auth);
		auth.setAmount(auth.getAmount()-this.getOrderPrice());
		auth.getInvestor().giveAuthorization(auth.getAmount(), auth.getBroker(), LocalDateTime.now());
		if (auth.getInvestor().getStockHoldings().containsKey(this.stock)) {
			this.amount += user.getStockHoldings().get(this.stock).getAmount();
			user.addStockHolding(this.stock, new StockHolding(this.amount, this.stock, user));
		} else {
			user.addStockHolding(this.stock, new StockHolding(this.amount, this.stock, user));
		}
		this.status = Status.COMPLETED;
		return true;

	}
	
	
}
