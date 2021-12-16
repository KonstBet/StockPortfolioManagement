package gr.aueb.team1.service;

import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.domain.User;

public class UserService {

	private UserDAO ud;

	public UserService(UserDAO ud) {
		this.ud = ud;
	}


	public User findUserByEmail(String email) {

		User u = null;
		u = ud.findByEmail(email) ; 
				
		return u;
	}
}