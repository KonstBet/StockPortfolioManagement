package gr.aueb.team1.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.dao.impl.UserDAOImpl;
import gr.aueb.team1.domain.User;
import gr.aueb.team1.service.UserService;

@Path("user")
public class UserResource {

	@GET
	@Path("{email:^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,7}$}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserInfo(@PathParam("email") String email) {
		
		UserDAO ud = new UserDAOImpl();
		UserService us = new UserService(ud);
		
		User u = us.findUserByEmail(email);

		return u;
	}
}

