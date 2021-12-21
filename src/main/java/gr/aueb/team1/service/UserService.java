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
	
	public User findUserById(Integer id) {
		User u = null;
		u = ud.findById(id) ; 
				
		return u;
	}
	
	public String portfolioReport(Integer id) {
		User u = findUserById(id);
		
		return u.portfolioReport();
	}
}