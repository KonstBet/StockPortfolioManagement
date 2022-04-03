package org.acme.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DepositTest {

    @Test
    void Constructor() {
        LocalDateTime date = LocalDateTime.now();
        Deposit deposit = new Deposit(5,200.0, date);

        Assertions.assertEquals(deposit.getUserid(),5);
        Assertions.assertEquals(deposit.getAmount(),200.0);
        Assertions.assertEquals(deposit.getDate(),date);
    }
}