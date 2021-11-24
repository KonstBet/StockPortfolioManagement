package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.domain.Broker;
import gr.aueb.team1.domain.Investor;
import gr.aueb.team1.domain.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDAOImplTest {
	private UserDAOImpl ud;
	private User user;
	private User user1;

    @BeforeEach
    void Initialize() {
        Initializer init = new Initializer();
        init.prepareData();
    	ud = new UserDAOImpl();
    	user = new Investor("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100");
    	user1 = new Broker("Mitsos", "Charalampidis", "mcharal@gmail.com", "697891030100", 1.0);
    }

    @Test
    void findAllTest() {
    	ud.save(user);
    	ud.save(user1);
    	List<User> res = ud.findAll();
    	assertEquals(2, res.size());
    }
    
    @Test
    void saveTest() {
    	User u = ud.save(user);
    	System.out.println(u.toString());
    	u.setName("Sakis");
    	u = ud.save(user);
    	System.out.println(u.toString());
    	assertNotNull(u);
    }
    
    @Test
    void deleteTest() {
    	ud.save(user);
    	ud.delete(user);
    	List<User> res = ud.findAll();
    	assertEquals(0, res.size());
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