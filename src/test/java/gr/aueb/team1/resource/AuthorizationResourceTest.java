package gr.aueb.team1.resource;

import gr.aueb.team1.dao.Initializer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import java.util.List;

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
    public void list4TransactionsTest() {
        Integer userid = init.investor.getId();
        List<AuthorizationInfo> tList = target("authorization/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<AuthorizationInfo>>() {});

        assertEquals(4,tList.size());
    }

    @Test
    public void list2TransactionsTest() {
        Integer userid = init.broker.getId();
        List<AuthorizationInfo> tList = target("authorization/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<AuthorizationInfo>>() {});

        assertEquals(2,tList.size());
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


}