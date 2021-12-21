package gr.aueb.team1.dao;

import gr.aueb.team1.domain.User;
import gr.aueb.team1.domain.Investor;
import gr.aueb.team1.domain.Broker;

import java.util.List;

public interface UserDAO extends DAO<User> {
	public User findByEmail(String mail);
	public Broker findBrokerById(Integer id);
	public Investor findInvestorById(Integer id);
	public List<User> findAllBrokers();
	public List<User> findAllInvestors();
}
