package org.acme.services;


import org.acme.resources.WalletDTO;
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
    @GET
    @Path("/{id}")
    Response getUserWallet(@PathParam("id") Long id);

    @Timeout(1000)
    @PUT
    @Path("")
    Response update(WalletDTO walletDTO);
}
