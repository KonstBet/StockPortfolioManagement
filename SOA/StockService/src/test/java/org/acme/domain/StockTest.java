package org.acme.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockTest {

    Stock stock;

    @BeforeEach
    void setUpTests() {
        stock = new Stock("JPMorgan Chase & Co", 131.67, 131.05, 133.9, 131.49, 10000.0);

    }

    @Test
    void getStockName() {
        Assertions.assertEquals(stock.getCompanyName(), "JPMorgan Chase & Co");
    }
}