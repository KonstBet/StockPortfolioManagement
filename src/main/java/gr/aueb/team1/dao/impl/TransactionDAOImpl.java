package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.TransactionDAO;
import gr.aueb.team1.domain.Transaction;
import gr.aueb.team1.domain.User;
import gr.aueb.team1.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    private EntityManager em;

    public TransactionDAOImpl() {
        em = JPAUtil.getCurrentEntityManager();
    }

    public List<Transaction> findAll() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createQuery("select a from Transaction a");
        List<Transaction> result = q.getResultList();

        tx.commit();

        return result;
    }
    public Transaction findById(Integer id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Transaction t = em.find(Transaction.class,id);

        tx.commit();
        return t;
    }

    public Transaction save(Transaction t) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Transaction savedTransaction = t;
        if (t.getId() != null) {
            savedTransaction = em.merge(t);
        } else {
            em.persist(t);
        }

        tx.commit();

        return savedTransaction;
    }

    public Transaction delete(Transaction t) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        int id = t.getId();
        Query q = em.createQuery("delete from User u where u.id = :id");
        q.setParameter("id", id);
        q.executeUpdate();

        tx.commit();
        return t;
    }
}
