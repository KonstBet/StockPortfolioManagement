package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.domain.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDAOImplTest {
	private UserDAO userDAO;
	Initializer init;

	@BeforeEach
	void Initialize() {
		init = new Initializer();
		init.prepareData();

		userDAO = new UserDAOImpl();
	}

	@Test
	void findAll() {
		List<User> users = userDAO.findAll();
		Assertions.assertEquals(3,users.size());
	}

	@Test
	void findById() {
		User user = userDAO.findById(init.investor.getId());
		Assertions.assertNotNull(user);
	}

	@Test
	void save() {
		User user = userDAO.findById(init.investor.getId());
		user.setPhoneNo("6901234567");

		userDAO.save(user);
		user = userDAO.findById(init.investor.getId());

		Assertions.assertTrue(user.getPhoneNo().equals("6901234567"));
	}

	@Test
	void delete() {
		User user = userDAO.findById(init.investor.getId());

		userDAO.delete(user);
		List<User> users = userDAO.findAll();
		Assertions.assertEquals(2,users.size());
	}
    
    @Test
    void findByEmailTest() {
    	User u = userDAO.findByEmail("mitcharal@gmail.com");
    	assertEquals(u.getName(), "Mitsos");
    }
    

    
}