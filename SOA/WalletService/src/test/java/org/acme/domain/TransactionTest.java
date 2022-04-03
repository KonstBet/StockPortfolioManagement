package org.acme.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    Integer userid = 5;
    Transaction t;
    public LocalDateTime date1;

    @BeforeEach
    void setUpTests() {
        date1 = LocalDateTime.now();

        t = new Withdrawal(userid,100.0, date1);
    }

    @Test
    void getId() {
        Assertions.assertEquals(t.getId(),null);
    }

    @Test
    void setId() {
        t.setId(7);
        Assertions.assertEquals(t.getId(),7);
    }

    @Test
    void getAmount() {
        Assertions.assertEquals(t.getAmount(),100.0);
    }

    @Test
    void setAmount() {
        t.setAmount(200.0);
        Assertions.assertEquals(t.getAmount(),200.0);
    }

    @Test
    void getDate() {
        Assertions.assertEquals(t.getDate(),date1);
    }

    @Test
    void setDate() {
        LocalDateTime date2 = LocalDateTime.now();
        t.setDate(date2);
        Assertions.assertEquals(t.getDate(),date2);
    }

    @Test
    void getUser() {
        Assertions.assertEquals(t.getUserid(),5);
    }

    @Test
    void setUser() {
        t.setUserid(9);
        Assertions.assertEquals(t.getUserid(),9);
    }

    @Test
    void testToString() {
        Transaction t = new Transaction(5, 12.00, date1);
        String s = t.toString();
        assertEquals("ID: " + t.getId() +
                "\nAmount: " + t.getAmount() +  "€"
                + "\nDate: " + t.getDate().toString()
                + "\nUserid: " + t.getUserid().toString(),s);
    }
}