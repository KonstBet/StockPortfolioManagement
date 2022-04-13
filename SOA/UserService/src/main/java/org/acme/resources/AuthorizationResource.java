package org.acme.resources;

import org.acme.services.AuthorizationService;
import org.acme.services.WalletService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

@Path("/authorizations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorizationResource {

    @Inject
    AuthorizationService authorizationService;

    @GET
    @Path("")
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
