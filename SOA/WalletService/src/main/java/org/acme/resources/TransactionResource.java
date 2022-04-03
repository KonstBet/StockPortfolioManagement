package org.acme.resources;
import org.acme.domain.Transaction;
import org.acme.repositories.TransactionRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/transaction")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {

    @Inject
    TransactionRepository transactionRepository;

    @GET
    public List<Transaction> list(@FormParam("userid") Integer userid) {
        return transactionRepository.findAllByUserID(userid);
    }
}
