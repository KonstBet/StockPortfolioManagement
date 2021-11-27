package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.dao.TransactionDAO;
import gr.aueb.team1.domain.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.time.LocalDateTime;
import java.util.List;

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
    void findAllTest1() {
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

        transactionDAO.delete(transaction);
        List<Transaction> transactions = transactionDAO.findAll();
        Assertions.assertEquals(0,transactions.size());
    }
    
    @Test
    void findByUser() {
    	List<Transaction> s = transactionDAO.findByUser(init.investor);
    	assertEquals(init.investor.getTransactions().size(),s.size());
    }
}