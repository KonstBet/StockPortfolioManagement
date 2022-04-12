package org.acme.resources;

public class WalletDTO {

    private Long userid;
    private Double balance;

    public WalletDTO() {}

    public Long getUserId() {
        return userid;
    }

    public void setUserId(Long userid) {
        this.userid = userid;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
