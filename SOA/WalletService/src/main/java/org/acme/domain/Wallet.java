package org.acme.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="UserWallet")
public class Wallet {

    @Id
    @Column(name = "userid", nullable = false)
    private Long userId;

    @Column(name ="balance", precision = 10, scale = 4)
    private Double balance;

    @OneToMany(mappedBy="wallet", cascade = CascadeType.PERSIST)
    private Set<Transaction> transactions = new HashSet<Transaction>();

    public Wallet() {
    }
    public Wallet(Long userId, Double balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userid) {
        this.userId = userid;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void updateBalance(Double updateAmount) {
        setBalance(balance + updateAmount);
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
