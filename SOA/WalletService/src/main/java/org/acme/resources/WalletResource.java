package org.acme.resources;

import org.acme.domain.Transaction;
import org.acme.domain.Wallet;
import org.acme.services.WalletService;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/balance")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Retry(maxRetries = 3)
@Timeout(2000)
public class WalletResource {

    @Inject
    WalletService walletService;

    @GET
    @Path("/{id}")
    @Counted(name = "BalanceGetRequestsCounter", description = "How many requests for balance get has been performed.")
    @Timed(name = "BalanceGetTimer", description = "A measure of how long it takes to perform a balanceGet request.", unit = MetricUnits.MILLISECONDS)
    public Response get(@PathParam("id") Long id) {
        try {
            Wallet wallet = walletService.get(id);

            if (wallet == null) {
                WalletDTO walletDTO = new WalletDTO();
                walletDTO.setBalance(0.0);
                return Response.ok(walletDTO).build();
            }

            WalletDTO walletDTO = new WalletDTO();
            walletDTO.setBalance(wallet.getBalance());
            walletDTO.setUserId(id);
            return Response.ok(walletDTO).build();
        }
        catch(Exception e) {
            return Response.status(404).build();
        }
    }

    @PUT
    @Path("")
    @Transactional
    @Counted(name = "BalanceUpdateRequestsCounter", description = "How many requests for balance update has been performed.")
    @Timed(name = "BalanceUpdateTimer", description = "A measure of how long it takes to perform a balanceUpdate request.", unit = MetricUnits.MILLISECONDS)
    public Response update(WalletDTO walletDTO) {
        try {

            if (!walletService.update(walletDTO))
                return Response.status(400).build();
            return Response.ok().build();
        }
        catch(Exception e) {
            return Response.status(400).build();
        }
    }

}
