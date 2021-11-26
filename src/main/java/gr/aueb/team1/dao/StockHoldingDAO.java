package gr.aueb.team1.dao;

import java.util.List;
import gr.aueb.team1.domain.StockHolding;
import gr.aueb.team1.domain.User;

public interface StockHoldingDAO extends DAO<StockHolding> {
	public List<StockHolding> findByUser(User user);
}
