package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.dao.StockHoldingDAO;
import gr.aueb.team1.domain.StockHolding;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockHoldingDAOImplTest {

    private StockHoldingDAO stockHoldingDAO;
    Initializer init;

    @BeforeEach
    void Initialize() {
        init = new Initializer();
        init.prepareData();

        stockHoldingDAO = new StockHoldingDAOImpl();
    }

    @Test
    void findAll() {
        List<StockHolding> stockHoldings = stockHoldingDAO.findAll();
        Assertions.assertEquals(2,stockHoldings.size());
    }

    @Test
    void findById() {
        StockHolding stockHolding = stockHoldingDAO.findById(init.investor.getStockHoldings().get(init.PeiraiosStock).getId());
        Assertions.assertNotNull(stockHolding);
    }

    @Test
    void save() {
        StockHolding stockHolding = stockHoldingDAO.findById(init.investor.getStockHoldings().get(init.PeiraiosStock).getId());
        stockHolding.setAmount(952);

        stockHoldingDAO.save(stockHolding);
        stockHolding = stockHoldingDAO.findById(init.investor.getStockHoldings().get(init.PeiraiosStock).getId());

        Assertions.assertEquals(952,stockHolding.getAmount());
    }

    @Test
    void delete() {
        StockHolding stockHolding = stockHoldingDAO.findById(init.investor.getStockHoldings().get(init.PeiraiosStock).getId());

        stockHoldingDAO.delete(stockHolding);
        List<StockHolding> stockHoldings = stockHoldingDAO.findAll();
        Assertions.assertEquals(1,stockHoldings.size());
    }
}