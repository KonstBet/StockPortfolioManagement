package org.acme.domain;

import javax.persistence.*;

@Entity
@Table(name="UserWallet")
public class Wallet {

    @Id
    @Column(name = "userid", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userid;

    @Column(name ="balance", precision = 10, scale = 4)
    private Double balance;

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
}
