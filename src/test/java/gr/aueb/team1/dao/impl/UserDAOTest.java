package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.domain.Investor;
import gr.aueb.team1.domain.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDAOTest {

    @BeforeEach
    void Initialize() {
        Initializer init = new Initializer();
        init.prepareData();
    }

    @Test
    void saveTest() {
    	UserDAO ud = new UserDAO();
    	User user = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
    	User u = ud.save(user);
    	System.out.println(u.toString());
    	u.setName("Sakis");
    	u = ud.save(user);
    	System.out.println(u.toString());
    	assertNotNull(u);
    }

//    @Test
//    void save() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
}