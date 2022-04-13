package org.acme.resources;

public class WalletDTO {

    private Long userId;
    private Double balance;

    public Long getUserId() {
        return userId;
    }

    public void setUserid(Long userId) {
        this.userId = userId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
