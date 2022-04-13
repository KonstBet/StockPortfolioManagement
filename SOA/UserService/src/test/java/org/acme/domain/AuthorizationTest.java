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

    @Test
    void setUpTests() {
        investor = new Investor();
        investor.setName("Giannhs");

        stockHoldingId = 10;

        broker = new Broker();
        broker.setName("Kwstas");

        date1 = LocalDateTime.now();
        date2 = LocalDateTime.now().plusMonths(2);

        auth = new Authorization(investor, broker, date1, date2, true);

        Assertions.assertTrue(investor==auth.getInvestor() && broker==auth.getBroker());

        auth.setActive(false);
        auth.setBroker(null);
        auth.setInvestor(null);
        auth.setStartDate(date2);
        auth.setEndDate(date1);
        auth.setId(111111L);

        Assertions.assertTrue(auth.getActive() == false && auth.getBroker() == null
        && auth.getInvestor() == null && auth.getEndDate().equals(date1) && auth.getStartDate().equals(date2)
        && auth.getId().equals(111111L));
    }


}