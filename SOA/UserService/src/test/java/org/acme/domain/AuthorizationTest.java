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
    public Integer stockHoldingid;
    public Broker broker;
    public LocalDateTime date1;
    public LocalDateTime date2;
    public Authorization auth;

    @BeforeEach
    void setUpTests() {
        investor = new Investor();
        investor.setName("Giannhs");

        stockHoldingid = 10;

        broker = new Broker();
        broker.setName("Kwstas");

        date1 = LocalDateTime.now();
        date2 = LocalDateTime.now().plusMonths(2);

        auth = new Authorization(investor, broker, date1, date2);
    }

    @Test
    void testGetters() {
        AuthCapital authcapital = new AuthCapital(investor, broker, date1, date2, 10.0);

        boolean flag = false;
        if (authcapital.getAmount() == 10 && authcapital.getStartdate().isEqual(date1)
                && authcapital.getEnddate().isEqual(date2) && authcapital.getId() == null
                && authcapital.getInvestor().getName().equals("Giannhs") && authcapital.getBroker().getName().equals("Kwstas"))
            flag = true;

        Assertions.assertTrue(flag);
    }

    @Test
    void testSetters() {
        AuthStock authstocks = new AuthStock(investor, stockHoldingid, broker, date1, date2, 10);
        authstocks.setAmount(20);
        authstocks.setId(7);
        authstocks.setInvestor(investor);
        authstocks.setBroker(broker);
        authstocks.setStockholdingid(stockHoldingid);

        LocalDateTime date3 = LocalDateTime.now();
        authstocks.setStartdate(date3);
        LocalDateTime date4 = LocalDateTime.now();
        authstocks.setEnddate(date4);

        boolean flag = false;
        if (authstocks.getAmount() == 20 && authstocks.getStartdate().isEqual(date3)
                && authstocks.getEnddate().isEqual(date4) && authstocks.getId() == 7 && authstocks.getStockholdingid() != null
                && authstocks.getInvestor().getName().equals("Giannhs") && authstocks.getBroker().getName().equals("Kwstas") )
            flag = true;

        Assertions.assertTrue(flag);

        //-------------------------------
        AuthCapital authCapital = new AuthCapital(investor, broker, date1, date2, 100.0);

        authCapital.setId(8);
        authCapital.setAmount(200.0);

        flag = false;
        if (authCapital.getAmount() == 200.0 && authCapital.getStartdate().isEqual(date1)
                && authCapital.getEnddate().isEqual(date2) && authCapital.getId() == 8
                && authstocks.getInvestor().getName().equals("Giannhs") && authCapital.getBroker().getName().equals("Kwstas") )
            flag = true;

        Assertions.assertTrue(flag);
    }

}