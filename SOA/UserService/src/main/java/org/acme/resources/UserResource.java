package org.acme.resources;

import org.acme.services.UserService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Path("")
    public Response list(@QueryParam("type") String type) {
        try {
            List<UserDTO> userDTOList = userService.list(type);

            if (userDTOList == null || userDTOList.size() == 0)
                return Response.status(404).build();
            return Response.ok(userDTOList).build();
        }
        catch(Exception e) {
            return null;
        }
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Integer id) {
        try {
            UserDTO userDTO = userService.get(id);
            if (userDTO == null)
                return Response.status(404).build();
            return Response.ok(userDTO).build();
        }
        catch(Exception e) {
            return null;
        }
    }


}
