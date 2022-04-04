package org.acme.resources;
import io.quarkus.hibernate.orm.runtime.graal.service.jacc.Delete_StandardJaccServiceImpl;
import org.acme.domain.Transaction;
import org.acme.repositories.TransactionRepository;
import org.acme.services.TransactionService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/transaction")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {

    @Inject
    TransactionService transactionService;

    @GET
    public List<Transaction> list(WalletDTO walletDTO) {
        return transactionService.list(walletDTO.getUserid());
    }

    @GET
    @Path("/{id}")
    public Transaction get(@PathParam("id") Integer id) {
        return transactionService.get(id);
    }

    @POST
    @Transactional
    public Response create(TransactionDTO t) {
        return transactionService.create(t);
    }
}
