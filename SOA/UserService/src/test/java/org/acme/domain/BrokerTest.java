package org.acme.domain;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class BrokerTest {

    @Test
    void ConstructorTest() {
        Broker broker = new Broker("Nikos","Papadopoulos",
                "nikosme@mailbox.gr","6987654321","b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b",10.0);

        Assertions.assertEquals(broker.getBrokerageFee(),10.0);
        broker.setBrokerageFee(500.0);

        Assertions.assertTrue(broker.getName().equals("Nikos")
                && broker.getSurname().equals("Papadopoulos")
                && broker.getEmail().equals("nikosme@mailbox.gr") && broker.getPhoneNo().equals("6987654321")
                && broker.getPassword().equals("b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b")
                && broker.getBrokerageFee() == 500.0);
    }
}