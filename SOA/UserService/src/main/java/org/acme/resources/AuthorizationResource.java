package org.acme.resources;

import org.acme.services.AuthorizationService;

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
    public Response list(Integer userid) {
        try {
            List<AuthorizationDTO> authorizationDTOList = authorizationService.list(userid);

            if (authorizationDTOList == null || authorizationDTOList.size() == 0)
                return Response.status(404).build();
            return Response.ok(authorizationDTOList).build();
        }
        catch(Exception e) {
            return null;
        }
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Integer id) {
        try {
            AuthorizationDTO authorizationDTO = authorizationService.get(id);
            if (authorizationDTO == null)
                return Response.status(404).build();
            return Response.ok(authorizationDTO).build();
        }
        catch(Exception e) {
            return null;
        }
    }

    @POST
    @Path("")
    @Transactional
    public Response create(AuthorizationDTO authorizationDTO) {
        try {
            if (!authorizationService.create(authorizationDTO))
                return Response.status(400).build();
            return Response.ok().build();
        }
        catch(Exception e) {
            return null;
        }
    }
}
