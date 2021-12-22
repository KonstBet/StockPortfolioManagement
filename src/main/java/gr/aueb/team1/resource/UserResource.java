package gr.aueb.team1.resource;

import java.net.URI;
import java.util.regex.Matcher;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import gr.aueb.team1.constants.CONSTANTS;
import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.dao.impl.UserDAOImpl;
import gr.aueb.team1.domain.Address;
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

		try {
			UserService us = new UserService(new UserDAOImpl());
			
			User u = UserInfo.getUser(ui);
			
			if(!validateUserInfo()) {
				System.err.println("Invalid Info");
				return null;
			}
			
			us.createUser(u);

			UriBuilder ub = uriInfo.getBaseUriBuilder().path("user");
			URI userUri = ub.build();

			return Response.created(userUri).build();
			
		} catch(NullPointerException e) {
			e.printStackTrace();
            return null;
		}
		
	}
	
	private Boolean validateUserInfo() {
		Matcher m1 = CONSTANTS.emailPattern.matcher(ui.getEmail());
		Matcher m2 = CONSTANTS.phonePattern.matcher(ui.getPhoneNo());
		Matcher m3 = CONSTANTS.namePattern.matcher(ui.getName());
		Matcher m4 = CONSTANTS.namePattern.matcher(ui.getSurname());
		Boolean m5 = validateAddress();
		
		return m1.matches() && m2.matches() && m3.matches() && m4.matches() && m5;
	}
	
	private Boolean validateAddress() {
		Address a = ui.getAddress();
		//TODO checks
		return true;
	}
}

