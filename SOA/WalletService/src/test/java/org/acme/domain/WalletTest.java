package org.acme.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    Integer userid = 5;
    Wallet wallet;

    @BeforeEach
    void setUpTests() {
        wallet = new Wallet(userid,500.0);
    }

    @Test
    void getUserid() {
        Assertions.assertEquals(wallet.getUserid(),5);
    }

    @Test
    void setUserid() {
        wallet.setUserid(7);
        Assertions.assertEquals(wallet.getUserid(),7);
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