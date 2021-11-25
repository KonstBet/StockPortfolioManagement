package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.dao.TransactionDAO;
import gr.aueb.team1.domain.Broker;
import gr.aueb.team1.domain.Investor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDAOImplTest {

    TransactionDAO transactionDAO;
    Initializer init;

    @BeforeEach
    void Initialize() {
        transactionDAO = new TransactionDAOImpl();
        init = new Initializer();
        init.prepareData();
    }

    @Test
    void findAll() {

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