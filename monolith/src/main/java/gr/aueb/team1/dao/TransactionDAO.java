package gr.aueb.team1.dao;

import java.util.List;
import gr.aueb.team1.domain.Transaction;
import gr.aueb.team1.domain.User;

public interface TransactionDAO extends DAO<Transaction> {
	public List<Transaction> findByUser(User user);
	public List<Transaction> findAllDepositsByUser(User user);
	public List<Transaction> findAllWithdrawsByUser(User user);
}
