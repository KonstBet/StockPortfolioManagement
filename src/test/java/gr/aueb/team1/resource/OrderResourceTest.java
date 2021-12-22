package gr.aueb.team1.resource;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import gr.aueb.team1.domain.Stock;

import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.domain.Order;

public class OrderResourceTest extends JerseyTest {
	
	Initializer init;

    @Override
    protected Application configure() {
        return new ResourceConfig(OrderResource.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        init = new Initializer();
        init.prepareData();
    }
    
    @Test
    public void listOrdersTest() {
        Integer userid = init.investor.getId();
        List<OrderInfo> oList = target("order/"+userid+"").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<OrderInfo>>() {});
        assertEquals(4,oList.size());
    }
    
    @Test
    public void listOrdersFailTest() {
        Integer userid = 20;
        List<OrderInfo> oList = target("order/"+userid+"").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<OrderInfo>>() {});
        assertNull(oList);
    }
    
    @Test
    public void buyStockTest() {
        Integer userid = init.investor.getId();
        Integer stockid = init.AlphaStock.getId();

        Form form = new Form();
        form.param("amount","1");
        
        Response res = target("order/"+userid+"/buy/"+stockid).request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),201);


        List<OrderInfo> oList = target("order/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<OrderInfo>>() {});

        assertEquals(5, oList.size());
    }
    
    @Test
    public void buyNonExistentStock() {
        Integer userid = init.investor.getId();
        Integer stockid = 1000;

        Form form = new Form();
        form.param("amount","1");
        
        Response res = target("order/"+userid+"/buy/"+stockid).request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(), 204);


        List<OrderInfo> oList = target("order/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<OrderInfo>>() {});

        assertEquals(4, oList.size());
    }
    
    @Test
    public void sellStockTest() {
        Integer userid = init.investor.getId();
        Integer stockid = init.AlphaStock.getId();

        Form form = new Form();
        form.param("amount","1");
        
        Response res = target("order/"+userid+"/sell/"+stockid).request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),201);


        List<OrderInfo> oList = target("order/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<OrderInfo>>() {});

        assertEquals(5, oList.size());
    }
    
    @Test
    public void sellNotOwnedStockTest() {
        Integer userid = init.investor.getId();
        Integer stockid = 12;

        Form form = new Form();
        form.param("amount","1");
        
        Response res = target("order/"+userid+"/sell/"+stockid).request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),204);


        List<OrderInfo> oList = target("order/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<OrderInfo>>() {});

        assertEquals(4, oList.size());
    }
    
    @Test
    public void limitStockTest() {
        Integer userid = init.investor.getId();
        Integer stockid = init.AlphaStock.getId();

        Form form = new Form();
        form.param("amount","2");
        form.param("limit","0.2");
        form.param("action", Order.Action.SELL.toString());
        
        
        Response res = target("order/"+userid+"/limit/"+stockid).request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),201);


        List<OrderInfo> oList = target("order/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<OrderInfo>>() {});
        
        assertEquals(5, oList.size());
    }
    
    @Test
    public void limitSellNotOwnedStockTest() {
        Integer userid = init.investor.getId();
        Integer stockid = 5;

        Form form = new Form();
        form.param("amount","2");
        form.param("limit","0.2");
        form.param("action", Order.Action.SELL.toString());
        
        
        Response res = target("order/"+userid+"/limit/"+stockid).request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(), 204);


        List<OrderInfo> oList = target("order/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<OrderInfo>>() {});
        
        assertEquals(4, oList.size());
    }
    
    @Test
    public void buyFroInvestorTest() {
    	Integer brokerid = init.broker2.getId();
    	Integer authid = init.ac.getId();
    	Integer investorid = init.ac.getInvestor().getId();
        Integer stockid = init.AlphaStock.getId();

        Form form = new Form();
        form.param("amount","1")
        .param("authid",authid.toString());
        
        Response res = target("order/"+brokerid+"/buyforinv/"+stockid).request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),201);


        List<OrderInfo> oList = target("order/"+investorid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<OrderInfo>>() {});

        assertEquals(5, oList.size());
    }
    
    @Test
    public void buyFroInvestorNonExistentStockTest() {
    	Integer brokerid = init.broker2.getId();
    	Integer authid = init.ac.getId();
    	Integer investorid = init.ac.getInvestor().getId();
        Integer stockid = 12345;

        Form form = new Form();
        form.param("amount","1")
        .param("authid",authid.toString());
        
        Response res = target("order/"+brokerid+"/buyforinv/"+stockid).request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),204);


        List<OrderInfo> oList = target("order/"+investorid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<OrderInfo>>() {});

        assertEquals(4, oList.size());
    }
    
    @Test
    public void sellForInvTest() {
    	Integer brokerid = init.broker.getId();
        Integer authid = init.as.getId();
        Integer investorid = init.as.getInvestor().getId();
        

        Form form = new Form();
        form.param("amount","1")
        .param("authid", authid.toString());
        
        Response res = target("order/"+brokerid+"/sellforinv/").request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),201);


        List<OrderInfo> oList = target("order/"+investorid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<OrderInfo>>() {});

        assertEquals(5, oList.size());
    }
    
    @Test
    public void sellForInvMoreThanOwnedTest() {
    	Integer brokerid = init.broker.getId();
        Integer authid = init.as.getId();
        Integer investorid = init.as.getInvestor().getId();
        

        Form form = new Form();
        form.param("amount","21")
        .param("authid", authid.toString());
        
        Response res = target("order/"+brokerid+"/sellforinv/").request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),204);


        List<OrderInfo> oList = target("order/"+investorid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<OrderInfo>>() {});

        assertEquals(4, oList.size());
    }



}
