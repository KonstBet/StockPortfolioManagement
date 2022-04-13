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
    public Response list(@QueryParam("userid") Long userId) {
        try {
            List<TransactionDTO> transactionDTOList = transactionService.list(userId);

            if (transactionDTOList == null || transactionDTOList.size() == 0)
                return Response.status(404).build();
            return Response.ok(transactionDTOList).build();
        }
        catch(Exception e) {
            return null;
        }
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        try {
            TransactionDTO transactionDTO = transactionService.get(id);
            if (transactionDTO == null)
                return Response.status(404).build();
            return Response.ok(transactionDTO).build();
        }
        catch(Exception e) {
            return null;
        }
    }

    @POST
    @Transactional
    public Response create(TransactionDTO t) {
        try {
            Long id = transactionService.create(t);
            if (id < 0)
                return Response.status(400).build();
            return Response.created(URI.create("/transaction/" + id)).build();
        }
        catch(Exception e) {
            return Response.status(400).build();
        }
    }
}
