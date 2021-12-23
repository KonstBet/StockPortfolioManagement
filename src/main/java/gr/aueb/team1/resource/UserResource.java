package gr.aueb.team1.resource;

import java.net.URI;
import java.util.List;
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
import gr.aueb.team1.domain.Broker;
import gr.aueb.team1.domain.Investor;
import gr.aueb.team1.domain.User;
import gr.aueb.team1.service.UserService;

@Path("user")
public class UserResource {
		
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
			if (!password.equals(u.getPassword())) {
				System.err.println("Wrong credentials");
				return null;
			}

			UserInfo ui = new UserInfo(u);

			return ui;
		}
		catch(NullPointerException e) {
			System.err.println("Wrong credentials");
			return null;
		}
	}
	
	@GET
	@Path("investor/{userid}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public UserInfo getInvestor(@PathParam("userid") Integer id) {

		try {
			UserService us = new UserService(new UserDAOImpl());

			Investor i = us.findInvestorById(id);

			UserInfo ii = new UserInfo(i);

			return ii;
		}
		catch(NullPointerException e) {
			return null;
		}
	}
	
	@GET
	@Path("investors")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<UserInfo> listInvestors() {
		UserService us = new UserService(new UserDAOImpl());

		List<Investor> investors = us.showInvestors();

		List<UserInfo> list = UserInfo.wrapI(investors);

		return list;
	}

	@GET
	@Path("brokers")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<UserInfo> listBrokers() {
			UserService us = new UserService(new UserDAOImpl());

			List<Broker> brokers = us.showBrokers();

			List<UserInfo> list = UserInfo.wrapB(brokers);

			return list;
	}
	
	@GET
	@Path("broker/{userid}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public UserInfo getBroker(@PathParam("userid") Integer id) {

		try {
			UserService us = new UserService(new UserDAOImpl());

			Broker b = us.findBrokerById(id);

			UserInfo bi = new UserInfo(b);

			return bi;
		}
		catch(NullPointerException e) {
			return null;
		}
	}
			
	@GET
	@Path("portfolio/{userid}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String getPortfolioReport(@PathParam("userid") Integer id) {
		try {
			UserService us = new UserService(new UserDAOImpl());
			return us.portfolioReport(id);
		}
		catch(NullPointerException e) {
			return null;
		}
	}
	
	@GET
	@Path("orderreport/{userid}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String getOrderReport(@PathParam("userid") Integer id) {
		try {
			UserService us = new UserService(new UserDAOImpl());
			return us.orderReport(id);
		}
		catch(NullPointerException e) {
			return null;
		}
	}

	
	
	@POST
	@Path("createi")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createInvestor(UserInfo ui) {

		try {
			UserService us = new UserService(new UserDAOImpl());
			
			Investor u = UserInfo.unwrapI(ui);
			
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
	
	@POST
	@Path("createb")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createBroker(UserInfo ui) {

		try {
			UserService us = new UserService(new UserDAOImpl());
			
			Broker u = UserInfo.unwrapB(ui);
			
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
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(UserInfo ui) {

		try {
			UserService us = new UserService(new UserDAOImpl());
			User u = UserInfo.unwrapU(ui);
			
			if(!validateUserInfo(ui)) {
				System.err.println("Invalid Info");
				return null;
			}
			
			us.createUser(u);

			return Response.ok().build();
			
		} catch(NullPointerException e) {
			e.printStackTrace();
            return null;
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

