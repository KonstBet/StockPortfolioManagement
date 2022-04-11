package org.acme.services;


import org.acme.resources.WalletDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/balance")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "wallet-api")
public interface WalletService {

    @GET
    @Path("/{id}")
    Response get(@PathParam("id") Integer id);

    @PUT
    @Path("")
    Response update(WalletDTO walletDTO);
}
