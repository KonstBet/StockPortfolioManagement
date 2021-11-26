package gr.aueb.team1.dao;

import java.util.List;
import gr.aueb.team1.domain.Order;
import gr.aueb.team1.domain.User;

public interface OrderDAO extends DAO<Order> {
	public List<Order> findByUser(User user);
}
