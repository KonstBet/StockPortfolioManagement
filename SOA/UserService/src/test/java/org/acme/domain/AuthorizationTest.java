package org.acme.domain;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class AuthorizationTest {

    public Investor investor;
    public Integer stockHoldingId;
    public Broker broker;
    public LocalDateTime date1;
    public LocalDateTime date2;
    public Authorization auth;

    @BeforeEach
    void setUpTests() {
        investor = new Investor();
        investor.setName("Giannhs");

        stockHoldingId = 10;

        broker = new Broker();
        broker.setName("Kwstas");

        date1 = LocalDateTime.now();
        date2 = LocalDateTime.now().plusMonths(2);

        auth = new Authorization(investor, broker, date1, date2, true);
    }


}