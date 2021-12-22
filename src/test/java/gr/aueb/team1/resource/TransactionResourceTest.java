package gr.aueb.team1.resource;

import gr.aueb.team1.dao.Initializer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionResourceTest extends JerseyTest {

    Initializer init;

    @Override
    protected Application configure() {
        return new ResourceConfig(TransactionResource.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        init = new Initializer();
        init.prepareData();
    }

    @Test
    public void listTransactionsTest() {
        Integer userid = init.investor.getId();
        List<TransactionInfo> tList = target("transaction/"+userid+"").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<TransactionInfo>>() {});

        assertEquals(1,tList.size());
    }


    @Test
    public void depositTest() {
        Integer userid = init.investor.getId();

        Form form = new Form();
        form.param("amount","100");

        Response res = target("transaction/"+userid+"/deposit").request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),201);


        List<TransactionInfo> tList = target("transaction/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<TransactionInfo>>() {});

        assertEquals(2,tList.size());
    }
    
    @Test
    public void depositNonExistentUserTest() {
        Integer userid = 5;

        Form form = new Form();
        form.param("amount","100");

        Response res = target("transaction/"+userid+"/deposit").request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),404);


        List<TransactionInfo> tList = target("transaction/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<TransactionInfo>>() {});

        assertNull(tList);
    }

    @Test
    public void withdrawTest() {
        Integer userid = init.investor.getId();

        Form form = new Form();
        form.param("amount","100");

        Response res = target("transaction/"+userid+"/withdraw").request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),201);

        List<TransactionInfo> tList = target("transaction/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<TransactionInfo>>() {});

        assertEquals(2,tList.size());
    }
    
    @Test
    public void withdrawNonExistentUserTest() {
        Integer userid = 5;

        Form form = new Form();
        form.param("amount","100");

        Response res = target("transaction/"+userid+"/withdraw").request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));

        assertEquals(res.getStatus(),404);

        List<TransactionInfo> tList = target("transaction/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<TransactionInfo>>() {});

        assertNull(tList);
    }
}