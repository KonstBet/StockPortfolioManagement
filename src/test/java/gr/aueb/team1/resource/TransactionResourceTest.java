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
        List<TransactionInfo> tList = target("user/"+userid+"/transaction").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<TransactionInfo>>() {});

        assertEquals(1,tList.size());
    }

    @Test
    public void getTransactionTest() {
        Integer userid = init.investor.getId();
        Integer tid = init.investor.getTransactions().iterator().next().getId();

        TransactionInfo t = target("user/"+userid+"/transaction/"+tid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<TransactionInfo>() {});

        assertEquals(t.getId(),tid);
        assertEquals(t.getAmount(),10000.0);
    }
}