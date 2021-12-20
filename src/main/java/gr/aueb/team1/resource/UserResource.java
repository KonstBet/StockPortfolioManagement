package gr.aueb.team1.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.dao.impl.UserDAOImpl;
import gr.aueb.team1.domain.User;
import gr.aueb.team1.service.UserService;

@Path("user")
public class UserResource {

	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes("application/x-www-form-urlencoded")
	public UserInfo getUserInfo(@FormParam("email") String email, @FormParam("password") String password) {

		try {
			UserDAO ud = new UserDAOImpl();
			UserService us = new UserService(ud);

			User u = us.findUserByEmail(email);
			if (!password.equals(u.getPassword()))
				return null;

			UserInfo ui = new UserInfo(u);

			return ui;
		}
		catch(NullPointerException e) {
			return null;
		}
	}
}

