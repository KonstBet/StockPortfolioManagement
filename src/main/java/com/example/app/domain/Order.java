package com.example.app.domain;

import java.util.Date;
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
	
	@Column(name="stockprice", precision = 10, scale = 4)
	private Double stockPrice;
	
	@Column(name="fee", precision = 10, scale = 4)
	private Float fee;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(name="orderprice", precision = 10, scale = 4)
	private Double orderPrice;
	
    @Enumerated(EnumType.STRING)
    @Column(name="action")
	private Action action;
	
	public enum Action {
		BUY,
		SELL
	}
	
	public Order() {
		
	}
	
	public Order(Integer id, Integer amount, Double stockPrice, Float fee, Date date, Action action) {
		super();
		this.id = id;
		this.amount = amount;
		this.stockPrice = stockPrice;
		this.fee = fee;
		this.date = date;
		this.action = action;
		this.orderPrice = this.calculatePrice();
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
	
	public Double getStockPrice() {
		return this.stockPrice;
	}
	
	public void setStockPrice(Double stockPrice) {
		this.stockPrice = stockPrice;
	}
	
	public Float getFee() {
		return fee;
	}
	
	public void setFee(Float fee) {
		this.fee = fee;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public void setDate(Date date) {
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
	
	private Double calculatePrice() {
		return stockPrice * amount + fee;
	}
	
	public String toString() {
		return "ID: " + getId() +
				"\nAmount: " + getAmount() + 
				"\nStock Price: "+ getStockPrice() + "€" +
				"\nFee: " + getFee() + "%" +
				"\nDate: " + getDate().toString() + 
				"\nAction: " + getAction() + 
				"\nOrder Price: " + getOrderPrice();
	}
	
}
