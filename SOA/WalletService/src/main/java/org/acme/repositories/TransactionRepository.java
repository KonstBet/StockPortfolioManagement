package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.Transaction;
import org.acme.resources.TransactionDTO;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transaction> {

    public Transaction findDepositByID(Long id) {
        return find("id = ?1 and type = ?2", id,"deposit").firstResult();
    }

    public Transaction findWithdrawByID(Long id) {
        return find("id = ?1 and type = ?2", id,"withdraw").firstResult();
    }

    public List<Transaction> findAllDepositsByUserID(Long userId) {
        return list("wallet.userId = ?1 and type = ?2", userId,"deposit");
    }

    public List<Transaction> findAllWithdrawsByUserID(Long userId) {
        return list("wallet.userId = ?1 and type = ?2", userId,"withdraw");
    }

    public Boolean saveTransaction(Transaction t) {
        persist(t);
        return isPersistent(t);
    }
}
