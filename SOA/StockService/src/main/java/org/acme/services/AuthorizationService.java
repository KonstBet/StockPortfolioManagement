package org.acme.services;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
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
    @Retry(maxRetries = 4)
    @Fallback(fallbackMethod = "fallbackverifyLink")
    @GET
    @Path("/link/verify")
    Response verifyLink(@QueryParam("investor_id") Long investorId, @QueryParam("broker_id") Long brokerId);

    static Response fallbackverifyLink(@QueryParam("investor_id") Long investorId, @QueryParam("broker_id") Long brokerId) {
        System.out.println("verifyLink went to fallback -- returned default noContent response");
        return Response.noContent().build();
    }
}
