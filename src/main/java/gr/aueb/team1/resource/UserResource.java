package gr.aueb.team1.resource;

import java.net.URI;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.dao.impl.UserDAOImpl;
import gr.aueb.team1.domain.User;
import gr.aueb.team1.service.UserService;

@Path("user")
public class UserResource {
	
	private UserInfo ui;
	
	@Context
	UriInfo uriInfo;
	
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

			ui = new UserInfo(u);

			return ui;
		}
		catch(NullPointerException e) {
			return null;
		}
	}
	
	@GET
	@Path("portfolio")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String portfolioReport() {
		UserService us = new UserService(new UserDAOImpl());
		return us.portfolioReport(ui.getId());
	}
	
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(UserInfo ui) {

		UserService us = new UserService(new UserDAOImpl());
		
		User u = UserInfo.getUser(ui);
		// TODO: should validate user
		
		us.createUser(u);

		//TODO: uri
		//UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI userUri = null;//= ub.path(Integer.toString(book.getId())).build();


		return Response.created(userUri).build();
	}
}

