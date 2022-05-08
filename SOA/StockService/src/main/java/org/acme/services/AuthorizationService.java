package org.acme.services;

import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/authorizations")
@RegisterRestClient(configKey = "authorization-api")
public interface AuthorizationService {

    @Timeout(1000)
    @GET
    @Path("/{id}")
    Response get(@PathParam("id") Long id);

    @Timeout(1000)
    @GET
    @Path("/link/verify")
    Response verifyLink(@QueryParam("investor_id") Long investorId, @QueryParam("broker_id") Long brokerId);
}
