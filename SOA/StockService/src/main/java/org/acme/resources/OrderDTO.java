package org.acme.resources;

import org.acme.domain.Order;
import org.acme.domain.OrderStatus;
import org.acme.domain.OrderType;
import org.acme.domain.Stock;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;
    private Integer stockAmount;
    private Double fee;
    private LocalDateTime date;
    private Double orderPrice;
    private OrderType type;
    private OrderStatus status = OrderStatus.PENDING;
    private Long stockId;
    private Long userId;

    public OrderDTO(){}

    public OrderDTO(Order order){
        this(order.getId(), order.getStockAmount(), order.getFee(), order.getDate(),
                order.getOrderPrice(), order.getType(), order.getStatus(),
                order.getStock().getId(), order.getUserId());
    }

    public OrderDTO(Long id, Integer stockAmount, Double fee, LocalDateTime date,
                    Double orderPrice, OrderType type, OrderStatus status, Long stockId, Long userId) {
        this.id = id;
        this.stockAmount = stockAmount;
        this.fee = fee;
        this.date = date;
        this.orderPrice = orderPrice;
        this.type = type;
        this.status = status;
        this.stockId = stockId;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public Long getStockId() {
        return stockId;
    }

    public void setStock(Long stockId) {
        this.stockId = stockId;
    }

    public static List<OrderDTO> listToDTOList(List<Order> orders){
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order: orders){
            orderDTOS.add(new OrderDTO(order));
        }
        return orderDTOS;
    }
}
