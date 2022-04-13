package org.acme.repositories;

import org.acme.domain.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class Initializer {

    @Inject
    StockRepository stockRepository;

    @Inject
    StockHoldingRepository stockHoldingRepository;

    @Inject
    OrderRepository orderRepository;

    private List<Stock> stocks;
    public Long userId1 = 1L;
    public Long userId2 = 2L;


    public List<Stock> getStocks(){
        return stocks;
    }


    Initializer(){
        this.stocks = new ArrayList<Stock>();
    }

    @Transactional
    public void eraseData() {
        orderRepository.deleteAll();
        stockHoldingRepository.deleteAll();
        stockRepository.deleteAll();
    }

    @Transactional
    public void seedData() {

        Stock jpStock = new Stock("JPMorgan Chase & Co", 131.67, 131.05, 133.9, 131.49, 10000.0);
        Stock metaStock = new Stock("Meta", 222.38, 220.05, 225.13, 220.03, 30000.0);



        StockHolding stockHolding = new StockHolding(15, jpStock, userId1);
        StockHolding stockHolding2 = new StockHolding(20, metaStock, userId1);
        StockHolding stockHolding3 = new StockHolding(10, jpStock, userId2);


        Order order = new Order(15, 0.0, LocalDateTime.now(),
                15*jpStock.getHigh(), OrderType.PURCHASE, jpStock, userId1, null);

        Order order2 = new Order(20, 0.0, LocalDateTime.now(),
                20*metaStock.getHigh(), OrderType.PURCHASE, metaStock, userId1, null);

        Order order3 = new Order(10, 0.0, LocalDateTime.now(),
                10*jpStock.getHigh(), OrderType.PURCHASE, jpStock, userId2, null);

        // save my lists to get ids on test cases!
        stocks.add(jpStock);
        stocks.add(metaStock);

        // Save entries to DB
        stockRepository.saveStock(jpStock);
        stockRepository.saveStock(metaStock);
        stockHoldingRepository.saveStockHolding(stockHolding);
        stockHoldingRepository.saveStockHolding(stockHolding2);
        stockHoldingRepository.saveStockHolding(stockHolding3);
        orderRepository.saveOrder(order);
        orderRepository.saveOrder(order2);
        orderRepository.saveOrder(order3);


    }
}
