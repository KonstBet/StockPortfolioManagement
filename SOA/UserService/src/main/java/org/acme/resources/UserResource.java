package org.acme.resources;

import org.acme.services.UserService;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Path("")
    @Counted(name = "UsersListRequestsCounter", description = "How many requests for users list has been performed.")
    @Timed(name = "UsersListTimer", description = "A measure of how long it takes to perform a UsersList request.", unit = MetricUnits.MILLISECONDS)
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
    @Counted(name = "UserGetRequestsCounter", description = "How many requests for user get has been performed.")
    @Timed(name = "UserGetTimer", description = "A measure of how long it takes to perform a UserGet request.", unit = MetricUnits.MILLISECONDS)
    public Response get(@PathParam("id") Long id) {
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

    @PUT
    @Path("/{id}")
    @Transactional
    @Counted(name = "UserUpdateRequestsCounter", description = "How many requests for user update has been performed.")
    @Timed(name = "UserUpdateTimer", description = "A measure of how long it takes to perform a UserUpdate request.", unit = MetricUnits.MILLISECONDS)
    public Response update(@PathParam("id") Long id, UserDTO userDTO) {
        try {
            Boolean flag = userService.update(id, userDTO);
            if (!flag)
                return Response.status(404).build();
            return Response.ok().build();
        }
        catch(Exception e) {
            return null;
        }
    }

    @POST
    @Path("")
    @Transactional
    @Counted(name = "UserCreateRequestsCounter", description = "How many requests for user create has been performed.")
    @Timed(name = "UserCreateTimer", description = "A measure of how long it takes to perform a UserCreate request.", unit = MetricUnits.MILLISECONDS)
    public Response create(UserDTO userDTO) {
        try {
            Boolean flag = userService.create(userDTO);
            if (!flag)
                return Response.status(400).build();
            return Response.ok().build();
        }
        catch(Exception e) {
            return null;
        }
    }
}
