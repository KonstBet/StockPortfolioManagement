package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.domain.Broker;
import gr.aueb.team1.domain.Investor;
import gr.aueb.team1.domain.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDAOImplTest {
	private UserDAO ud;
	private User user;
	private User user1;

    @BeforeEach
    void Initialize() {
        Initializer init = new Initializer();
        init.prepareData();
    	ud = new UserDAOImpl();
    	user = new Investor("Sakis", "Kanavopoulos", "skan@gmail.com", "697891030100");
    	user1 = new Broker("Aleksandros","Lamprineros", "alamp@gmail.com", "697891030100", 1.0);
    }

    @Test
    void findAllTest() {
    	ud.save(user);
    	ud.save(user1);
    	List<User> res = ud.findAll();
    	assertEquals(5, res.size());
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
    	assertEquals(3, res.size());
    }
    
    @Test
    void findByEmailTest() {
    	User u = ud.findByEmail("mitcharal@gmail.com");
    	assertEquals(u.getName(), "Mitsos");
    }
    

    
}