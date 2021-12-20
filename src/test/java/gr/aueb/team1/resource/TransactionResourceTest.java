package gr.aueb.team1.resource;

import gr.aueb.team1.dao.Initializer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
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
        List<TransactionInfo> tList = target("transaction/"+userid+"").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<TransactionInfo>>() {});

        assertEquals(1,tList.size());
    }

    @Test
    public void getTransactionTest() {
        Integer userid = init.investor.getId();
        Integer tid = init.investor.getTransactions().iterator().next().getId();

        TransactionInfo t = target("transaction/"+userid+"/"+tid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<TransactionInfo>() {});

        assertEquals(t.getId(),tid);
        assertEquals(t.getAmount(),10000.0);
    }

    @Test
    public void doDepositTest() {
        Integer userid = init.investor.getId();

        Form form = new Form();
        form.param("amount","100");

        TransactionInfo ti = target("transaction/"+userid+"/deposit").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED),new GenericType<TransactionInfo>() {});

        assertNotNull(ti.getId());
        assertEquals(ti.getAmount(),100.0);

        List<TransactionInfo> tList = target("transaction/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<TransactionInfo>>() {});

        assertEquals(2,tList.size());
    }

    @Test
    public void doWithdrawTest() {
        Integer userid = init.investor.getId();

        Form form = new Form();
        form.param("amount","1000");

        TransactionInfo ti = target("transaction/"+userid+"/withdraw").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED),new GenericType<TransactionInfo>() {});

        assertNotNull(ti.getId());
        assertEquals(ti.getAmount(),1000.0);

        List<TransactionInfo> tList = target("transaction/"+userid+"").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<TransactionInfo>>() {});

        assertEquals(2,tList.size());
    }
}