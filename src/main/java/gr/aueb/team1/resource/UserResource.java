package gr.aueb.team1.resource;

import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import gr.aueb.team1.constants.CONSTANTS;
import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.dao.impl.UserDAOImpl;
import gr.aueb.team1.domain.Address;
import gr.aueb.team1.domain.Broker;
import gr.aueb.team1.domain.Investor;
import gr.aueb.team1.domain.User;
import gr.aueb.team1.service.UserService;

@Path("user")
public class UserResource {
		
	@Context
	UriInfo uriInfo;
	
	@GET
	@Path("investors")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<UserInfo> listInvestors() {

		try {
			UserService us = new UserService(new UserDAOImpl());

			List<Investor> investors = us.showInvestors();

			List<UserInfo> list = UserInfo.wrapI(investors);

			return list;
		}
		catch(NullPointerException e) {
			return null;
		}
	}

	@GET
	@Path("brokers")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<UserInfo> listBrokers() {

		try {
			UserService us = new UserService(new UserDAOImpl());

			List<Broker> brokers = us.showBrokers();

			List<UserInfo> list = UserInfo.wrapB(brokers);

			return list;
		}
		catch(NullPointerException e) {
			return null;
		}
	}
	
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
	
//	@GET
//	@Path("portfolio")
//	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//	public String portfolioReport() {
//		UserService us = new UserService(new UserDAOImpl());
//		return us.portfolioReport(ui.getId());
//	}
	
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(UserInfo ui) {

		try {
			UserService us = new UserService(new UserDAOImpl());
			
			User u = UserInfo.getUser(ui);
			
			if(!validateUserInfo(ui)) {
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
	
	@PUT
	@Path("update/{userid:[0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(UserInfo ui) {
		
		try {
			
			return Response.ok().build();
		} catch(NullPointerException e) {
			e.printStackTrace();
            return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	private Boolean validateUserInfo(UserInfo ui) {
		Matcher m1 = CONSTANTS.emailPattern.matcher(ui.getEmail());
		Matcher m2 = CONSTANTS.phonePattern.matcher(ui.getPhoneNo());
		Matcher m3 = CONSTANTS.namePattern.matcher(ui.getName());
		Matcher m4 = CONSTANTS.namePattern.matcher(ui.getSurname());
		Boolean m5 = validateAddress(ui);
		
		return m1.matches() && m2.matches() && m3.matches() && m4.matches() && m5;
	}
	
	private Boolean validateAddress(UserInfo ui) {
		Address a = ui.getAddress();
		Matcher m1 = CONSTANTS.zipCodePattern.matcher(a.getZipCode());
		Matcher m2 = CONSTANTS.addNumPattern.matcher(a.getNumber());
		Matcher m3 = CONSTANTS.streetPattern.matcher(a.getStreet());
		
		return m1.matches() && m2.matches() && m3.matches();
	}
}

