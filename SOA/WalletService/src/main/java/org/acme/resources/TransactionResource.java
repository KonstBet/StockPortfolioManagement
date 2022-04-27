package org.acme.resources;
import io.quarkus.hibernate.orm.runtime.graal.service.jacc.Delete_StandardJaccServiceImpl;
import org.acme.domain.Transaction;
import org.acme.repositories.TransactionRepository;
import org.acme.services.TransactionService;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

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
@Retry(maxRetries = 3)
@Timeout(2000)
public class TransactionResource {

    @Inject
    TransactionService transactionService;

    @GET
    @Counted(name = "transactionListRequestsCounter", description = "How many requests for transaction list has been performed.")
    @Timed(name = "transactionListTimer", description = "A measure of how long it takes to perform a transactionList request.", unit = MetricUnits.MILLISECONDS)
    public Response list(@QueryParam("user_id") Long userId) {
        try {
            List<TransactionDTO> transactionDTOList = transactionService.list(userId);

            if (transactionDTOList == null || transactionDTOList.size() == 0)
                return Response.status(404).build();
            return Response.ok(transactionDTOList).build();
        }
        catch(Exception e) {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("/{id}")
    @Counted(name = "transactionGetRequestsCounter", description = "How many requests for transaction get has been performed.")
    @Timed(name = "transactionGetTimer", description = "A measure of how long it takes to perform a transactionGet request.", unit = MetricUnits.MILLISECONDS)
    public Response get(@PathParam("id") Long id) {
        try {
            TransactionDTO transactionDTO = transactionService.get(id);
            if (transactionDTO == null)
                return Response.status(404).build();
            return Response.ok(transactionDTO).build();
        }
        catch(Exception e) {
            return Response.status(404).build();
        }
    }

    @POST
    @Transactional
    @Counted(name = "transactionCreateRequestsCounter", description = "How many requests for transaction creation has been performed.")
    @Timed(name = "transactionCreateTimer", description = "A measure of how long it takes to perform a transactionCreate request.", unit = MetricUnits.MILLISECONDS)
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
