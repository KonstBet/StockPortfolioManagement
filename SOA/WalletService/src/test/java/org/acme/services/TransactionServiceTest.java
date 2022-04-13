package org.acme.services;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.repositories.Initializer;
import org.acme.resources.TransactionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TransactionServiceTest {

    @Inject
    Initializer initializer;

    @Inject
    TransactionService transactionService;

    @BeforeEach
    void initialize() {
        initializer.EraseData();
        initializer.Initialize();
    }

    @Test
    void list() {
        List<TransactionDTO> transactionDTOList = transactionService.list(1L);

        Assertions.assertEquals(transactionDTOList.size(),2);
    }

    @Test
    void getDeposit() {
        TransactionDTO transactionDTO = transactionService.get(initializer.getDeposit().getId());

        Assertions.assertNotNull(transactionDTO);
    }

    @Test
    void getWithdraw() {
        TransactionDTO transactionDTO = transactionService.get(initializer.getWithdraw().getId());

        Assertions.assertNotNull(transactionDTO);
    }

    @Test
    void getBadID() {
        TransactionDTO transactionDTO = transactionService.get(99999L);

        Assertions.assertNull(transactionDTO);
    }

    @Test
    @Transactional
    void create() {
        TransactionDTO transactionDTO = new TransactionDTO(1L, 100.0,"deposit", LocalDateTime.now());

        transactionService.create(transactionDTO);

        List<TransactionDTO> transactionDTOList = transactionService.list(1L);

        Assertions.assertEquals(transactionDTOList.size(),3);
    }

    @Test
    @Transactional
    void createNULLDeposit() {
        TransactionDTO transactionDTO = new TransactionDTO(9999L, 100.0,"deposit", LocalDateTime.now());

        transactionService.create(transactionDTO);

        List<TransactionDTO> transactionDTOList = transactionService.list(9999L);

        Assertions.assertEquals(transactionDTOList.size(),1);
    }

    @Test
    @Transactional
    void createNULLWithdraw() {
        TransactionDTO transactionDTO = new TransactionDTO(9999L, 10.0,"withdraw", LocalDateTime.now());

        transactionService.create(transactionDTO);

        List<TransactionDTO> transactionDTOList = transactionService.list(9999L);

        Assertions.assertEquals(transactionDTOList.size(),0);
    }
}