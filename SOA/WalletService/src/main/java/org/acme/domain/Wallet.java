package org.acme.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="UserWallet")
public class Wallet {

    @Id
    @Column(name = "userid", nullable = false)
    private Integer userid;

    @Column(name ="balance", precision = 10, scale = 4)
    private Double balance;

    @OneToMany(mappedBy="wallet", cascade = CascadeType.MERGE)
    private Set<Transaction> transactions = new HashSet<Transaction>();

    public Wallet() {
    }
    public Wallet(Integer userid, Double balance) {
        this.userid = userid;
        this.balance = balance;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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
