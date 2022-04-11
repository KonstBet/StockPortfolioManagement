package org.acme.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="orders")
public class Order
{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="stock_amount", nullable=false)
    private Integer stockAmount;

    @Column(name="fee", precision = 10, scale = 4)
    private Double fee;

    @Column(name="date")
    private LocalDateTime date;

    @Column(name="order_price", precision = 10, scale = 4)
    private Double orderPrice;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private OrderType type;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private OrderStatus status = OrderStatus.PENDING;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="stock_id")
    private Stock stock;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public Order() {}

    public Order(Integer stockAmount, Double fee, LocalDateTime date, Double orderPrice, OrderType type, Stock stock, Long userId) {
        this.stockAmount = stockAmount;
        this.fee = fee;
        this.date = date;
        this.orderPrice = orderPrice;
        this.type = type;
        this.stock = stock;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(Integer stockAmount) {
        this.stockAmount = stockAmount;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String toString() {
        return "ID: " + getId() +
                "\nType:" + getType() +
                "\nUser: " + getUserId() +
                "\nStock: " + getStock().getCompanyName() +
                "\nAmount: " + getStockAmount() +
                "\nOrder Total: " + getOrderPrice() +
                "\nDate: " + getDate() +
                "\nBroker Fee: " + getFee() +
                "\nStatus: " + getStatus();
    }
}