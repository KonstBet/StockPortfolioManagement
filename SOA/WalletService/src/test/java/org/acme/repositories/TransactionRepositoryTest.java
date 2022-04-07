package org.acme.repositories;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.domain.Transaction;
import org.acme.domain.Withdrawal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.AssertTrue;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TransactionRepositoryTest {

    @Inject
    Initializer initializer;

    @Inject
    TransactionRepository transactionRepository;

    @BeforeEach
    void initialize() {
        initializer.EraseData();
        initializer.Initialize();
    }

    @Test
    void findDepositByID() {
        Transaction deposit = transactionRepository.findDepositByID(initializer.getDeposit().getId());

        Assertions.assertEquals(deposit.getId(),initializer.getDeposit().getId());
        Assertions.assertEquals(deposit.getAmount(),initializer.getDeposit().getAmount());
    }

    @Test
    void findWithdrawByID() {
        Transaction withdraw = transactionRepository.findWithdrawByID(initializer.getWithdraw().getId());

        Assertions.assertEquals(withdraw.getId(),initializer.getWithdraw().getId());
        Assertions.assertEquals(withdraw.getAmount(),initializer.getWithdraw().getAmount());
    }

    @Test
    void findAllDepositsByUserID() {
        List<Transaction> depositList = transactionRepository.findAllDepositsByUserID(1);

        Assertions.assertEquals(depositList.size(),1);
    }

    @Test
    void findAllWithdrawsByUserID() {
        List<Transaction> withdrawList = transactionRepository.findAllWithdrawsByUserID(1);
        Assertions.assertEquals(withdrawList.size(),1);
    }

    @Test
    @Transactional
    void saveTransaction() {
        Withdrawal withdraw = new Withdrawal(null,10.0, LocalDateTime.now());

        transactionRepository.saveTransaction(withdraw);

        Transaction w = transactionRepository.findWithdrawByID(withdraw.getId());

        Assertions.assertEquals(w.getId(),withdraw.getId());
        Assertions.assertEquals(w.getAmount(),withdraw.getAmount());
    }
}