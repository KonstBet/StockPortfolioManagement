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
    public void listStockHoldings2Test() {
        Integer userid = init.investor.getId();
        List<StockHoldingInfo> shList = target("stockholding/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<StockHoldingInfo>>() {});

        assertEquals(2,shList.size());
    }

    @Test
    public void listStockHoldings0Test() {
        Integer userid = init.broker.getId();
        List<StockHoldingInfo> shList = target("stockholding/"+userid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<StockHoldingInfo>>() {});

        assertEquals(0,shList.size());
    }

    @Test
    public void BrokenlistStockHoldingsTest() {
        List<StockHoldingInfo> shList = target("stockholding/"+"1000").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<StockHoldingInfo>>() {});

        assertNull(shList);
    }

    @Test
    public void getStockHoldingPeiraiosTest() {
        Integer userid = init.investor.getId();
        Integer shid = init.investor.getStockHoldings().get(init.PeiraiosStock).getId();

        StockHoldingInfo sh = target("stockholding/"+userid+"/"+shid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<StockHoldingInfo>() {});

        assertEquals(sh.getId(),shid);
        assertEquals(sh.getCommittedAmount(),20);
    }

    @Test
    public void getStockHoldingAlphaTest() {
        Integer userid = init.investor.getId();
        Integer shid = init.investor.getStockHoldings().get(init.AlphaStock).getId();

        StockHoldingInfo sh = target("stockholding/"+userid+"/"+shid).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<StockHoldingInfo>() {});

        assertEquals(sh.getId(),shid);
        assertEquals(sh.getAmount(),20);
    }

//    @Test
//    public void getStockHoldingNotMineTest() {
//        Integer userid = init.investor.getId();
//        Integer shid = init.broker.getStockHoldings().get(init.AlphaStock).getId();
//
//        StockHoldingInfo sh = target("stockholding/"+userid+"/"+shid).request(MediaType.APPLICATION_JSON)
//                .get(new GenericType<StockHoldingInfo>() {});
//
//        assertEquals(sh.getId(),shid);
//        assertEquals(sh.getAmount(),20);
//    }

    @Test
    public void BrokengetStockHoldingTest() {

        StockHoldingInfo sh = target("stockholding/"+"1000"+"/"+"1000").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<StockHoldingInfo>() {});

        assertNull(sh);
    }
}