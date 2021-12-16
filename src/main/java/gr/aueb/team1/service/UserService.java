package gr.aueb.team1.service;

import java.util.List;
import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.domain.User;

public class UserService {

	private UserDAO ud;

	public UserService(UserDAO ud) {
		this.ud = ud;
	}


	public List<User> showUserInfo() {

		List<User> results = null;
		results = ud.findAll(); ; 
				
		return results;
	}
}