package org.acme.resources;

import org.acme.domain.Transaction;
import org.acme.domain.Wallet;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Double amount;
    private String type;
    private Integer userid;

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

    public Transaction TransactionDTOtoTransaction() {
            Wallet wallet = new Wallet(userid,amount);
            Transaction transaction = new Transaction(wallet,amount,LocalDateTime.now());
            wallet.getTransactions().add(transaction);
            transaction.setWallet(wallet);
            return transaction;
    }
    public Transaction TransactionDTOtoTransaction(Wallet w) {
            Transaction transaction = new Transaction(w,amount,LocalDateTime.now());
            w.getTransactions().add(transaction);
            transaction.setWallet(w);
            return transaction;
    }
}
