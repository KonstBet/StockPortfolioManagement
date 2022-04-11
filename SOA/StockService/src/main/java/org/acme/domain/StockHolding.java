package org.acme.domain;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="stock_holdings")
public class StockHolding {


    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="amount", length=100, nullable=false)
    private Integer amount;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="stock_id")
    private Stock stock;

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked = false;

    public StockHolding() {}

    public StockHolding(Integer amount, Stock stock, Long userId) {
        this.amount = amount;
        this.stock = stock;
        this.userId = userId;
    }

    public Long getId() {
        return this.id;
    }

    public Integer getAmount() {
        return this.amount;
    }


    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean locked) {
        isLocked = locked;
    }

    public Long getUserId() {
        return this.userId;
    }


    public Stock getStock() {
        return this.stock;
    }
}