package gr.aueb.team1.resource;

import java.net.URI;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import gr.aueb.team1.dao.OrderDAO;
import gr.aueb.team1.dao.impl.OrderDAOImpl;
import gr.aueb.team1.dao.impl.StockDAOImpl;
import gr.aueb.team1.domain.Order;
import gr.aueb.team1.service.OrderService;
import gr.aueb.team1.service.StockService;


@Path("order/{userid}")
public class OrderResource {
	
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<OrderInfo> listOrders(@PathParam("userid") Integer userid) {
	
	    try {
	        OrderDAO od = new OrderDAOImpl();
	        OrderService os = new OrderService(od);
	        
	        List<Order> orders = os.getOrders(userid);
	        List<OrderInfo> orderInfoList = OrderInfo.wrap(orders);
	
	        return orderInfoList;
	    }
	    catch(NullPointerException e) {
	        return null;
	    }
	}
	
	@POST
    @Path("buy/{stockid}")
    @Consumes("application/x-www-form-urlencoded")
    public Response buyStock(
            @PathParam("userid") Integer userid,
            @PathParam("stockid") Integer stockid,
    		@FormParam("amount") Integer amount) {
        
		try {
            OrderDAO od = new OrderDAOImpl();
            StockService ss = new StockService(new StockDAOImpl());
            
            OrderService os = new OrderService(od);
            os.buyStock(userid, ss.getStock(stockid), amount);

            UriBuilder ub = uriInfo.getBaseUriBuilder().path("order/"+ userid);
            URI orderUri = ub.build();

            return Response.created(orderUri).build();
        } catch(NullPointerException e) {
            return null;
        }
    }
	
	@POST
    @Path("sell/{stockid}")
    @Consumes("application/x-www-form-urlencoded")
    public Response sellStock(
            @PathParam("userid") Integer userid,
            @PathParam("stockid") Integer stockid,
    		@FormParam("amount") Integer amount) {
        
		try {
            OrderDAO od = new OrderDAOImpl();
            StockService ss = new StockService(new StockDAOImpl());
            
            OrderService os = new OrderService(od);
            os.sellStock(userid, ss.getStock(stockid), amount);

            UriBuilder ub = uriInfo.getBaseUriBuilder().path("order/"+ userid);
            URI orderUri = ub.build();

            return Response.created(orderUri).build();
        }
            catch(NullPointerException e) {
            return null;
        }
    }
	
	@POST
    @Path("limit/{stockid}")
    @Consumes("application/x-www-form-urlencoded")
    public Response sellStock(
            @PathParam("userid") Integer userid,
            @PathParam("stockid") Integer stockid,
    		@FormParam("amount") Integer amount,
    		@FormParam("limit") Double limit,
    		@FormParam("action") Order.Action action) {
        
		try {
            OrderDAO od = new OrderDAOImpl();
            StockService ss = new StockService(new StockDAOImpl());
            
            OrderService os = new OrderService(od);
            os.limitOrder(userid, limit, ss.getStock(stockid), amount, action);

            UriBuilder ub = uriInfo.getBaseUriBuilder().path("order/"+ userid);
            URI orderUri = ub.build();

            return Response.created(orderUri).build();
        }
            catch(NullPointerException e) {
            return null;
        }
    }
	
	@POST
    @Path("buyforinv/{stockid}")
    @Consumes("application/x-www-form-urlencoded")
    public Response buyForInvestor(
            @PathParam("userid") Integer userid,
            @PathParam("stockid") Integer stockid,
            @FormParam("authid") Integer authid,
    		@FormParam("amount") Integer amount) {
        
		try {
            OrderDAO od = new OrderDAOImpl();
            StockService ss = new StockService(new StockDAOImpl());
            
            OrderService os = new OrderService(od);
            os.buyForInvestor(userid, authid, ss.getStock(stockid), amount);

            UriBuilder ub = uriInfo.getBaseUriBuilder().path("order/"+ userid);
            URI orderUri = ub.build();

            return Response.created(orderUri).build();
        }
            catch(NullPointerException e) {
            return null;
        }
    }
	
	@POST
    @Path("sellforinv/")
    @Consumes("application/x-www-form-urlencoded")
    public Response sellForInvestor(
            @PathParam("userid") Integer userid,
            @FormParam("authid") Integer authid,
    		@FormParam("amount") Integer amount) {
        
		try {
            OrderDAO od = new OrderDAOImpl();
            
            OrderService os = new OrderService(od);
            os.sellForInvestor(userid, authid, amount);

            UriBuilder ub = uriInfo.getBaseUriBuilder().path("order/"+ userid);
            URI orderUri = ub.build();

            return Response.created(orderUri).build();
        }
            catch(NullPointerException e) {
            return null;
        }
    }

}
