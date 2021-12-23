package gr.aueb.team1.resource;

import gr.aueb.team1.dao.Initializer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

import java.time.LocalDateTime;
import java.util.List;

import static gr.aueb.team1.constants.CONSTANTS.dateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;

public class AuthorizationResourceTest extends JerseyTest {

    Initializer init;

    @Override
    protected Application configure() {
        return new ResourceConfig(AuthorizationResource.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        init = new Initializer();
        init.prepareData();
    }

    @Test
    public void list4AuthsTest() {
        Integer userid = init.investor.getId();
        List<AuthorizationInfo> tList = target("authorization/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<AuthorizationInfo>>() {});

        assertEquals(4,tList.size());
    }

    @Test
    public void list2AuthsTest() {
        Integer userid = init.broker.getId();
        List<AuthorizationInfo> tList = target("authorization/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<AuthorizationInfo>>() {});

        assertEquals(2,tList.size());
    }

    @Test
    public void brokenListAuthsTest() {

        List<AuthorizationInfo> tList = target("authorization/"+"1000").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<AuthorizationInfo>>() {});

        assertNull(tList);
    }

    @Test
    public void getAuthorizationTest() {
        Integer userid = init.investor.getId();
        Integer shid = init.investor.getStockHoldings().get(init.PeiraiosStock).getAuthStock().iterator().next().getId();

        AuthorizationInfo ai = target("authorization/"+userid+"/"+shid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<AuthorizationInfo>() {});

        assertEquals(10,ai.getAmount());
        assertEquals("PIRAEUS",ai.getStockName());
    }

    @Test
    public void brokenGetAuthorizationTest() {

        AuthorizationInfo ai = target("authorization/"+"1000"+"/"+"1000").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<AuthorizationInfo>() {});

        assertNull(ai);
    }

    @Test
    public void giveCapitalAuthorizationTest() {
        Integer userid = init.investor.getId();
        Integer brokerid = init.broker.getId();

        Form form = new Form();
        form.param("amount","100");
        form.param("brokerid", brokerid.toString());
        form.param("enddate", LocalDateTime.of(1,1,1,1,1).format(dateTimeFormatter));

        Response res = target("authorization/"+userid+"/givecapitalauthorization").request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),201);


        AuthorizationInfo auth = target(res.getLocation().getPath()).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<AuthorizationInfo>() {});

        assertEquals(init.broker.getId(),auth.getBrokerid());
        assertNotNull(auth);
    }

    @Test
    public void brokenGiveCapitalAuthorizationTest() {

        Form form = new Form();
        form.param("amount","100");
        form.param("brokerid", "1000");
        form.param("enddate", LocalDateTime.of(1,1,1,1,1).format(dateTimeFormatter));

        Response res = target("authorization/"+"1000"+"/givecapitalauthorization").request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),204);
    }

    @Test
    public void giveStockAuthorizationTest() {
        Integer userid = init.investor.getId();
        Integer brokerid = init.broker.getId();

        Form form = new Form();
        form.param("amount","10");
        form.param("stockholdingid",init.investor.getStockHoldings().get(init.AlphaStock).getId().toString());
        form.param("brokerid", brokerid.toString());
        form.param("enddate", LocalDateTime.of(1,1,1,1,1).format(dateTimeFormatter));

        Response res = target("authorization/"+userid+"/givestockauthorization").request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),201);


        AuthorizationInfo auth = target(res.getLocation().getPath()).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<AuthorizationInfo>() {});

        assertEquals("ALPHA",auth.getStockName());
        assertEquals(10.0,auth.getAmount());
        assertEquals(init.broker.getId(),auth.getBrokerid());
        assertNotNull(auth);
    }

    @Test
    public void brokenGiveStockAuthorizationTest() {

        Form form = new Form();
        form.param("amount","100");
        form.param("stockholdingid","1000");
        form.param("brokerid", "1000");
        form.param("enddate", LocalDateTime.of(1,1,1,1,1).format(dateTimeFormatter));

        Response res = target("authorization/"+"1000"+"/givestockauthorization").request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),204);
    }
    
    @Test
    public void brokenGetAuthorizationTest1() {

        AuthorizationInfo ai = target("authorization/10000/10000").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<AuthorizationInfo>() {});

        assertNull(ai);
    }
}