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

public class StockHoldingResourceTest extends JerseyTest {

    Initializer init;

    @Override
    protected Application configure() {
        return new ResourceConfig(StockHoldingResource.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        init = new Initializer();
        init.prepareData();
    }

    @Test
    public void listStockHoldingsTest() {
        List<StockHoldingInfo> shList = target("user/"+init.investor.getId()+"/holding").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<StockHoldingInfo>>() {});

        assertEquals(2,shList.size());
    }

}