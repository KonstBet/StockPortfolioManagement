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

    @Column(name = "investor_id", nullable = false)
    private Long investorId;

    @Column(name = "broker_id")
    private Long brokerId;

    public Order() {}

    public Order(Integer stockAmount, Double fee, LocalDateTime date, Double orderPrice,
                 OrderType type, Stock stock, Long investorId, Long brokerId) {
        this.stockAmount = stockAmount;
        this.fee = fee;
        this.date = date;
        this.orderPrice = orderPrice;
        this.type = type;
        this.stock = stock;
        this.investorId = investorId;
        this.brokerId = brokerId;
    }

    public Long getId() {
        return id;
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

    public Long getInvestorId() {
        return investorId;
    }

    public void setInvestorId(Long investorId) {
        this.investorId = investorId;
    }

    public Long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }
}