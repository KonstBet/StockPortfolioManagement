package org.acme.repositories;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.domain.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@QuarkusTest
class StockRepositoryTest {

    @Inject
    Initializer initializer;

    @Inject
    StockRepository stockRepository;

    @BeforeEach
    void initialize(){
        initializer.eraseData();
        initializer.seedData();
    }

    @Test
    void listStocks(){
        List<Stock> stocks = stockRepository.listStocks();
        Assertions.assertEquals(stocks.size(), 2);
    }

    @Test
    void findStockByName(){
        Stock stock = stockRepository.findByCompanyName(initializer.getStocks().get(0).getCompanyName());
        Assertions.assertEquals(stock.getCompanyName(), initializer.getStocks().get(0).getCompanyName());
    }


}
