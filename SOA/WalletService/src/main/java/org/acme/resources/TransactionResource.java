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
    public List<TransactionDTO> list(WalletDTO walletDTO) {
        try {
            return transactionService.list(walletDTO.getUserid());
        }
        catch(Exception e) {
            return null;
        }
    }

    @GET
    @Path("/{id}")
    public TransactionDTO get(@PathParam("id") Integer id) {
        try {
            return transactionService.get(id);
        }
        catch(Exception e) {
            return null;
        }
    }

    @POST
    @Transactional
    public Response create(TransactionDTO t) {
        try {
            return transactionService.create(t);
        }
        catch(Exception e) {
            return null;
        }
    }
}
