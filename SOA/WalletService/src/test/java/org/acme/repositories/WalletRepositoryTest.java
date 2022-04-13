package org.acme.repositories;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.domain.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class WalletRepositoryTest {

    @Inject
    Initializer initializer;

    @Inject
    WalletRepository walletRepository;

    @BeforeEach
    void initialize() {
        initializer.EraseData();
        initializer.Initialize();
    }

    @Test
    void findByID() {
        Wallet wallet = walletRepository.findByID(initializer.getWallet().getUserId());

        Assertions.assertEquals(wallet.getBalance(),initializer.getWallet().getBalance());
        Assertions.assertEquals(wallet.getTransactions().size(),initializer.getWallet().getTransactions().size());
    }

    @Test
    @Transactional
    void saveWallet() {
        Wallet wallet = new Wallet(2L,200.0);

        walletRepository.saveWallet(wallet);

        Wallet w = walletRepository.findByID(2L);

        Assertions.assertEquals(w.getBalance(),200.0);
    }
}