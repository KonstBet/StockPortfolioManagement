package org.acme.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WithdrawalTest {

    @Test
    void Constructor() {
        LocalDateTime date = LocalDateTime.now();
        Wallet wallet = new Wallet(5L,1000.0);

        Withdrawal withdrawal = new Withdrawal(wallet, 200.0, date);

        Assertions.assertEquals(withdrawal.getWallet().getUserId(),5);
        Assertions.assertEquals(withdrawal.getAmount(),200.0);
        Assertions.assertEquals(withdrawal.getDate(),date);
    }
}