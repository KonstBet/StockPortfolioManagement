package gr.aueb.team1.service;

import gr.aueb.team1.dao.TransactionDAO;
import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.dao.impl.UserDAOImpl;
import gr.aueb.team1.domain.Transaction;
import gr.aueb.team1.domain.User;
import java.util.List;

public class TransactionService {
    private TransactionDAO td;

    public TransactionService(TransactionDAO td) {
        this.td = td;
    }

    public List<Transaction> showDeposits(Integer userid) {

        User u = getUser(userid);

        List<Transaction> results = null;
        results = td.findAllDepositsByUser(u);

        return results;
    }

    public List<Transaction> showWithdraws(Integer userid) {

        User u = getUser(userid);

        List<Transaction> results = null;
        results = td.findAllWithdrawsByUser(u);

        return results;
    }

    public Transaction getTransaction(Integer userid,Integer tid) {

        User u = getUser(userid);

        Transaction result = null;
        result = td.findById(tid);

        if (result.getUser().equals(u))
            return result;

        return null;
    }

    public Transaction doDeposit(Integer userid, Double amount) {

        User u = getUser(userid);

        Transaction t = u.deposit(amount);

        return td.save(t);
    }

    public Transaction doWithdraw(Integer userid, Double amount) {

        User u = getUser(userid);

        Transaction t = u.withdraw(amount);

        return td.save(t);
    }

    private User getUser(Integer userid) {
        UserDAO ud = new UserDAOImpl();
        User u = ud.findById(userid);
        return u;
    }


}
