package gr.aueb.team1.dao;

import gr.aueb.team1.domain.User;

import java.util.List;

public interface UserDAO extends DAO<User> {
	public User findByEmail(String mail);
	public List<User> findAllBrokers();
	public List<User> findAllInvestors();
}
