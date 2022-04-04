package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.Transaction;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transaction> {

    public Transaction findByID(Integer id) {
        return find("id", id).firstResult();
    }

    public List<Transaction> findAllByUserID(Integer userid) {
        return list("wallet.userid", userid);
    }

    public Boolean saveTransaction(Transaction t) {
        persist(t);
        return isPersistent(t);
    }
}
