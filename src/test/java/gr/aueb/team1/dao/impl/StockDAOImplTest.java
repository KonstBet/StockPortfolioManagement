package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.StockDAO;
import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.domain.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockDAOImplTest {

    private StockDAO stockDAO;
    Initializer init;

    @BeforeEach
    void Initialize() {
        init = new Initializer();
        init.prepareData();

        stockDAO = new StockDAOImpl();
    }

    @Test
    void findAll() {
        List<Stock> Stocks = stockDAO.findAll();
        Assertions.assertEquals(3,Stocks.size());
    }

    @Test
    void findById() {
        Stock Stock = stockDAO.findById(init.PeiraiosStock.getId());
        Assertions.assertNotNull(Stock);
    }

    @Test
    void save() {
        Stock Stock = stockDAO.findById(init.PeiraiosStock.getId());
        LocalDateTime date = LocalDateTime.now();
        Stock.setDate(LocalDateTime.now());

        stockDAO.save(Stock);
        Stock = stockDAO.findById(init.PeiraiosStock.getId());

        Assertions.assertTrue(Stock.getDate().isEqual(date));
    }

    @Test
    void delete() {
        Stock stock = stockDAO.findById(init.PeiraiosStock.getId());

        stockDAO.delete(stock);
        List<Stock> stocks = stockDAO.findAll();
        Assertions.assertEquals(2,stocks.size());
    }
}