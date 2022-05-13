package org.acme.services;


import org.acme.resources.WalletDTO;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/balance")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "wallet-api")
public interface WalletService {

    @Timeout(1000)
    @Retry(maxRetries = 2)
    @Fallback(fallbackMethod = "fallbackgetUserWallet")
    @GET
    @Path("/{id}")
    Response getUserWallet(@PathParam("id") Long id);

    @Timeout(1000)
    @Retry(maxRetries = 2)
    @Fallback(fallbackMethod = "fallbackupdate")
    @PUT
    @Path("")
    Response update(WalletDTO walletDTO);

    private Response fallbackgetUserWallet(@PathParam("id") Long id) {
        System.out.println("getUserWallet went to fallback -- returned default noContent response");
        return Response.noContent().build();
    }

    private Response fallbackupdate(WalletDTO walletDTO) {
        System.out.println("updateWallet went to fallback -- returned default notModified response");
        return Response.notModified().build();
    }
}
