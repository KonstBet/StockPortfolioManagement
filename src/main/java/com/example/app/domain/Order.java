package com.example.app.domain;
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
	
	public Order() {
		
	}
	
	public Order(User user,Stock stock, Integer amount, Double fee, LocalDateTime date, Action action) {
		this.stock=stock;
		this.user= user;
		this.amount = amount;
		this.fee = fee;
		this.date = date;
		this.action = action;
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
	
	public Action getAction() {
		return this.action;
	}
	
	public void setAction(Action action) {
		this.action = action;
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
	
}
