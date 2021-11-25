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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDAOImplTest {

    private TransactionDAO transactionDAO;

    @BeforeEach
    void Initialize() {
        Initializer init = new Initializer();
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
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }
}