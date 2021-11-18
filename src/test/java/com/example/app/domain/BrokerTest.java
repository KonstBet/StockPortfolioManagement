package com.example.app.domain;

import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

public class BrokerTest {
    private Investor investor;
    private Broker broker;
    private Stock PeiraiosStock;
    private Stock AlphaStock;
    private Stock CosmoteStock;
    private Integer amount;
    private StockHolding sh;

    @BeforeEach
    public void setUpTests() {
        investor = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
        investor.setBalance(500.00);
        broker = new Broker("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100",0.0);

        PeiraiosStock = new Stock("P200", "PIRAIUS", LocalDateTime.now(), 10.00, 200.99, 1000.00, 10.00, 2460.00);
        AlphaStock = new Stock("P224", "ALPHA", LocalDateTime.now(), 100.00, 200.99, 1000.00, 10.00, 2460.00);
        CosmoteStock = new Stock("P104", "COSMOTE", LocalDateTime.now(), 29.00, 200.99, 1000.00, 10.00, 2460.00);
        amount = 10;
        sh = new StockHolding(amount, PeiraiosStock, investor);
        investor.addStockHolding(PeiraiosStock, sh);
    }
}
