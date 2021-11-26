package gr.aueb.team1.dao;

import gr.aueb.team1.domain.User;

public interface UserDAO extends DAO<User> {
	public User findByEmail(String mail);
}
