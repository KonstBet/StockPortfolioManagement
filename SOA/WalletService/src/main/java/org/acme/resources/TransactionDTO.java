package org.acme.resources;

import org.acme.domain.Deposit;
import org.acme.domain.Transaction;
import org.acme.domain.Wallet;
import org.acme.domain.Withdrawal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDTO {
    private Integer id;
    private Double amount;
    private String type;
    private Integer userid;
    private LocalDateTime date;

    public TransactionDTO() {}

    public TransactionDTO(Integer userid, Double amount, String type, LocalDateTime date) {
        this.userid = userid;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public TransactionDTO(Integer id, Integer userid, Double amount, String type, LocalDateTime date) {
        this.id = id;
        this.userid = userid;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Transaction TransactionDTOtoTransaction() {//new Wallet
            Wallet wallet = new Wallet(userid,amount);
            Deposit deposit = new Deposit(wallet,amount,LocalDateTime.now());
            wallet.getTransactions().add(deposit);
            deposit.setWallet(wallet);
            return deposit;
    }
    public Transaction TransactionDTOtoTransaction(Wallet w, String type) {//existing Wallet

            Transaction transaction = new Transaction(w,amount,LocalDateTime.now());
            if (type.equals("deposit"))
                transaction = new Deposit(w,amount,LocalDateTime.now());
            else
                transaction = new Withdrawal(w,amount,LocalDateTime.now());

            w.getTransactions().add(transaction);
            transaction.setWallet(w);
            return transaction;
    }
}
