package org.acme.resources;

import org.acme.domain.Deposit;
import org.acme.domain.Transaction;
import org.acme.domain.Wallet;
import org.acme.domain.Withdrawal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDTO {
    private Long id;
    private Double amount;
    private String type;
    private Long userId;
    private LocalDateTime date;

    public TransactionDTO() {}

    public TransactionDTO(Long userId, Double amount, String type, LocalDateTime date) {
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public TransactionDTO(Long id, Long userId, Double amount, String type, LocalDateTime date) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Transaction TransactionDTOtoTransaction() {//new Wallet
            Wallet wallet = new Wallet(userId,amount);
            Deposit deposit = new Deposit(wallet,amount,LocalDateTime.now());
            wallet.getTransactions().add(deposit);
            deposit.setWallet(wallet);
            return deposit;
    }
    public Transaction TransactionDTOtoTransaction(Wallet w, String type) {//existing Wallet

            Transaction transaction;
            if (type.equals("deposit")) transaction = new Deposit(w,amount,LocalDateTime.now());
            else transaction = new Withdrawal(w,amount,LocalDateTime.now());

            w.getTransactions().add(transaction);
            transaction.setWallet(w);
            return transaction;
    }
}
