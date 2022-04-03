package org.acme.services;

import org.acme.domain.Transaction;
import org.acme.repositories.TransactionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class TransactionService {

    @Inject
    TransactionRepository transactionRepository;

    public List<Transaction> list(){
        return transactionRepository.findAll().list();
    }

    public Transaction get(Integer id) {
        return transactionRepository.findByID(id);
    }

    public Response create(Transaction t) {
        if (transactionRepository.saveTransaction(t))
            return Response.created(URI.create("/persons/" + t.getId())).build();
        else
            return Response.status(400).build();
    }
}
