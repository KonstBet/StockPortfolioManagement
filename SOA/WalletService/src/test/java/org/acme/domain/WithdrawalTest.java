package org.acme.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WithdrawalTest {

    @Test
    void Constructor() {
        LocalDateTime date = LocalDateTime.now();
        Withdrawal withdrawal = new Withdrawal(5,200.0, date);

        Assertions.assertEquals(withdrawal.getUserid(),5);
        Assertions.assertEquals(withdrawal.getAmount(),200.0);
        Assertions.assertEquals(withdrawal.getDate(),date);
    }
}