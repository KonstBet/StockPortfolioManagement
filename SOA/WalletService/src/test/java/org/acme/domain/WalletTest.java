package org.acme.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    Long userId = 5L;
    Wallet wallet;

    @BeforeEach
    void setUpTests() {
        wallet = new Wallet(userId,500.0);
    }

    @Test
    void getUserId() {
        Assertions.assertEquals(wallet.getUserId(),5);
    }

    @Test
    void setUserId() {
        wallet.setUserId(7L);
        Assertions.assertEquals(wallet.getUserId(),7);
    }

    @Test
    void getBalance() {
        Assertions.assertEquals(wallet.getBalance(),500.0);
    }

    @Test
    void setBalance() {
        wallet.setBalance(400.0);
        Assertions.assertEquals(wallet.getBalance(),400.0);
    }

    @Test
    void updateBalance() {
        wallet.updateBalance(100.0);
        Assertions.assertEquals(wallet.getBalance(),600.0);
    }
}