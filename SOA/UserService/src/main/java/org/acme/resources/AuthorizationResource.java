package org.acme.resources;

import org.acme.services.AuthorizationService;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/authorizations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorizationResource {

    @Inject
    AuthorizationService authorizationService;

    @GET
    @Path("")
    @Counted(name = "AuthorizationsListRequestsCounter", description = "How many requests for authorizations list has been performed.")
    @Timed(name = "AuthorizationsListTimer", description = "A measure of how long it takes to perform an AuthorizationsList request.", unit = MetricUnits.MILLISECONDS)
    public Response list(@QueryParam("user_id") Long userId, @QueryParam("type") String type) {
        try {
            List<AuthorizationDTO> authorizationDTOList;
            if(type.equals("broker")){
                authorizationDTOList = authorizationService.listBrokerAuthorizations(userId);
            }else{
                authorizationDTOList = authorizationService.listInvestorAuthorizations(userId);
            }

            //if (authorizationDTOList == null) return Response.status(404).build();

            return Response.ok(authorizationDTOList).build();
        }
        catch(Exception e) {
            return null;
        }
    }

    @GET
    @Path("/{id}")
    @Counted(name = "AuthorizationsGetRequestsCounter", description = "How many requests for authorizations get has been performed.")
    @Timed(name = "AuthorizationsGetTimer", description = "A measure of how long it takes to perform an AuthorizationsGet request.", unit = MetricUnits.MILLISECONDS)
    public Response findById(@PathParam("id") Long id) {
        try {
            AuthorizationDTO authorizationDTO = authorizationService.findById(id);
            if (authorizationDTO == null) return Response.status(404).build();

            return Response.ok(authorizationDTO).build();
        }
        catch(Exception e) {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("/link/verify")
    @Counted(name = "AuthorizationsVerifyRequestsCounter", description = "How many requests for authorizations verifyPairing has been performed.")
    @Timed(name = "AuthorizationsVerifyTimer", description = "A measure of how long it takes to perform an AuthorizationsVerifyParing request.", unit = MetricUnits.MILLISECONDS)
    public Response verifyPairing(  @QueryParam("investor_id") Long investorId, @QueryParam("broker_id") Long brokerId){

        try{
            Boolean isAuthorized = authorizationService.isAuthorized(investorId, brokerId);

            if(!isAuthorized) return Response.status(403).build();

            return Response.ok().build();
        }catch(Exception e){
            return Response.status(403).build();
        }
    }


    @POST
    @Path("")
    @Transactional
    @Counted(name = "AuthorizationsCreateRequestsCounter", description = "How many requests for authorizations create has been performed.")
    @Timed(name = "AuthorizationsCreateTimer", description = "A measure of how long it takes to perform an AuthorizationsCreate request.", unit = MetricUnits.MILLISECONDS)
    public Response create(AuthorizationDTO authorizationDTO) {
        try {

            if (!authorizationService.createAuthorization(authorizationDTO))
                return Response.status(400).build();

            return Response.ok().build();
        }
        catch(Exception e) {
            return Response.status(400).build();
        }
    }
}
