package org.acme.services;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.domain.Wallet;
import org.acme.repositories.Initializer;
import org.acme.resources.WalletDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class WalletServiceTest {

    @Inject
    Initializer initializer;

    @Inject
    WalletService walletService;

    @BeforeEach
    void initialize() {
        initializer.EraseData();
        initializer.Initialize();
    }

    @Test
    void get() {
        Wallet wallet = walletService.get(1);

        Assertions.assertNotNull(wallet);
    }

    @Test
    @Transactional
    void update() {
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setUserid(1);
        walletDTO.setBalance(100000.0);
        walletService.update(walletDTO);

        Wallet wallet = walletService.get(1);

        Assertions.assertEquals(wallet.getBalance(),100000.0);
    }
}