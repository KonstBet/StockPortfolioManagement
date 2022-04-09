package org.acme.domain;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class InvestorTest {

    @Test
    void ConstructorTest() {
        Investor investor = new Investor("Nikos","Papadopoulos","nikosme@mailbox.gr","6987654321","b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");

        Assertions.assertEquals(investor.getCommittedBalance(),0.0);
        investor.setCommittedBalance(500.0);

        Assertions.assertTrue(investor.getName().equals("Nikos") && investor.getSurname().equals("Papadopoulos")
                && investor.getEmail().equals("nikosme@mailbox.gr") && investor.getPhoneNo().equals("6987654321")
                && investor.getPassword().equals("b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b")
                && investor.getCommittedBalance() == 500.0);
    }

}