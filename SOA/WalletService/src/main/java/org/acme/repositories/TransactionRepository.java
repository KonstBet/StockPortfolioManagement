package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.Transaction;
import org.acme.resources.TransactionDTO;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transaction> {

    public Transaction findDepositByID(Integer id) {
        return find("id = ?1 and type = ?2", id,"deposit").firstResult();
    }

    public Transaction findWithdrawByID(Integer id) {
        return find("id = ?1 and type = ?2", id,"withdraw").firstResult();
    }

    public List<Transaction> findAllDepositsByUserID(Integer userid) {
        return list("wallet.userid = ?1 and type = ?2", userid,"deposit");
    }

    public List<Transaction> findAllWithdrawsByUserID(Integer userid) {
        return list("wallet.userid = ?1 and type = ?2", userid,"withdraw");
    }

    public Boolean saveTransaction(Transaction t) {
        persist(t);
        return isPersistent(t);
    }
}
