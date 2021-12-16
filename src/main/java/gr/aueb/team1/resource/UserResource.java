package gr.aueb.team1.resource;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import gr.aueb.team1.dao.StockDAO;
import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.dao.impl.StockDAOImpl;
import gr.aueb.team1.dao.impl.UserDAOImpl;
import gr.aueb.team1.domain.User;
import gr.aueb.team1.service.UserService;

@Path("user")
public class UserResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> listUserInfo() {
		UserDAO ud = new UserDAOImpl();
		UserService stockService = new UserService(ud);
		
		//List<User> user = UserService.showStocks();
		//List<StockInfo> stockInfo = StockInfo.wrap(stocks);

		return null;
	
	}
}

