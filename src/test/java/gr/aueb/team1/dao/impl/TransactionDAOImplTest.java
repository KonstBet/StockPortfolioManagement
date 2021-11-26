package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.dao.StockHoldingDAO;
import gr.aueb.team1.dao.TransactionDAO;
import gr.aueb.team1.domain.Broker;
import gr.aueb.team1.domain.Investor;
import gr.aueb.team1.domain.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDAOImplTest {

    private TransactionDAO transactionDAO;
    Initializer init;

    @BeforeEach
    void Initialize() {
        init = new Initializer();
        init.prepareData();

        transactionDAO = new TransactionDAOImpl();
    }

    @Test
    void findAll() {
        List<Transaction> transactions = transactionDAO.findAll();
        Assertions.assertEquals(1,transactions.size());
    }

    @Test
    void findById() {
        Transaction transaction = transactionDAO.findById(init.investor.getTransactions().iterator().next().getId());
        Assertions.assertNotNull(transaction);
    }

    @Test
    void save() {
        Transaction transaction = transactionDAO.findById(init.investor.getTransactions().iterator().next().getId());
        LocalDateTime date = LocalDateTime.now();
        transaction.setDate(date);

        transactionDAO.save(transaction);
        transaction = transactionDAO.findById(init.investor.getTransactions().iterator().next().getId());

        Assertions.assertTrue(transaction.getDate().isEqual(date));
    }

    @Test
    void delete() {
        Transaction transaction = transactionDAO.findById(init.investor.getTransactions().iterator().next().getId());
        transaction.remove();

        transactionDAO.delete(transaction);
        List<Transaction> transactions = transactionDAO.findAll();
        Assertions.assertEquals(0,transactions.size());
    }
}