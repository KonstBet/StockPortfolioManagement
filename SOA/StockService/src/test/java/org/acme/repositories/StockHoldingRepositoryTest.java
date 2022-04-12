package org.acme.repositories;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.domain.Stock;
import org.acme.domain.StockHolding;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@QuarkusTest
class StockHoldingRepositoryTest {

    @Inject
    Initializer initializer;

    @Inject
    StockHoldingRepository stockHoldingRepository;

    @Inject
    StockRepository stockRepository;

    @BeforeEach
    void initialize(){
        initializer.eraseData();
        initializer.seedData();
    }

    @Test
    void getUser1StockHoldings(){
        List<StockHolding> stockHoldings = stockHoldingRepository.findUserStockholdings(initializer.userId1);
        Assertions.assertEquals(stockHoldings.size(), 2);
    }

    @Test
    void getUser2StockHoldings(){
        List<StockHolding> stockHoldings = stockHoldingRepository.findUserStockholdings(initializer.userId2);
        Assertions.assertEquals(stockHoldings.size(), 1);


        Stock stock = stockRepository.findByPk(stockHoldings.get(0).getStock().getId());
        Assertions.assertEquals(stock.getCompanyName(), "JPMorgan Chase & Co");

        Assertions.assertEquals(stockHoldings.get(0).getUserId(), initializer.userId2);
    }

    @Test
    void getByStockIdAndUserId(){
        List<StockHolding> stockHoldings = stockHoldingRepository.findUserStockholdings(initializer.userId2);
        Stock stock = stockRepository.findByPk(stockHoldings.get(0).getStock().getId());

        StockHolding stockHolding = stockHoldingRepository.findByUserAndStockId(initializer.userId2, stock.getId());

        Assertions.assertEquals(stockHolding.getAmount(),10);
    }

    @Test
    void getNotExistingStockHoldingByStockIdAndUserId(){
        StockHolding stockHolding = stockHoldingRepository.findByUserAndStockId(initializer.userId2, Long.MAX_VALUE);

        Assertions.assertNull(stockHolding);
    }


}
