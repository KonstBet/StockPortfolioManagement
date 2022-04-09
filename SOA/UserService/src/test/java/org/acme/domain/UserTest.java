package org.acme.domain;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserTest {

    @Test
    void Constructor() {
        User usr = new User("Nikos","Papadopoulos","nikosme@mailbox.gr","6987654321","b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");

        Assertions.assertTrue(usr.getName().equals("Nikos") && usr.getSurname().equals("Papadopoulos")
                && usr.getEmail().equals("nikosme@mailbox.gr") && usr.getPhoneNo().equals("6987654321")
                && usr.getPassword().equals("b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b"));
    }

    @Test
    void GettersSetters() {
        User usr = new User();
        usr.setId(100);
        usr.setName("Nikos");
        usr.setSurname("Papadopoulos");
        usr.setEmail("nikosme@mailbox.gr");
        usr.setPhoneNo("6987654321");
        usr.setPassword("b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b");
        usr.setAddress(new Address("Andreou", "1", "11111"));

        Assertions.assertTrue(usr.getName().equals("Nikos") && usr.getSurname().equals("Papadopoulos")
                && usr.getEmail().equals("nikosme@mailbox.gr") && usr.getPhoneNo().equals("6987654321")
                && usr.getPassword().equals("b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b")
                && usr.getAddress().getStreet().equals("Andreou") && usr.getId() == 100);
    }

}