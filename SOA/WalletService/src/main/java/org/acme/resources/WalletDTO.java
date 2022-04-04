package org.acme.resources;

import org.acme.domain.Wallet;

import java.util.Optional;

public class WalletDTO {

    private Integer userid;
    private Double balance;

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

//    public Wallet WalletDTOtoWallet() {
//        return new Wallet(userid,balance);
//    }
}
