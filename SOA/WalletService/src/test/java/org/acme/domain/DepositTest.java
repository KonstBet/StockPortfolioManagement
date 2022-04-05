package org.acme.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DepositTest {

    @Test
    void Constructor() {
        LocalDateTime date = LocalDateTime.now();
        Wallet wallet = new Wallet(5,1000.0);

        Deposit deposit = new Deposit(wallet,200.0, date);

        Assertions.assertEquals(deposit.getWallet().getUserid(),5);
        Assertions.assertEquals(deposit.getAmount(),200.0);
        Assertions.assertEquals(deposit.getDate(),date);
    }
}