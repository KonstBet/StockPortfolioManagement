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

public class StockResourceTest extends JerseyTest {

    Initializer init;

    @Override
    protected Application configure() {
        return new ResourceConfig(StockResource.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        init = new Initializer();
        init.prepareData();
    }

    @Test
    public void listStocksTest() {
        List<StockInfo> stockList = target("stock").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<StockInfo>>() {});

        assertEquals(3,stockList.size());
    }

    @Test
    public void fetchStockTest() {
        StockInfo stock = target("stock/"+init.PeiraiosStock.getId()).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<StockInfo>() {});

        assertEquals("PIRAEUS",stock.getCompanyName());
    }

    @Test
    public void BrokenfetchStockTest() {
        StockInfo stock = target("stock/"+"100000").request(MediaType.APPLICATION_JSON)
                .get(new GenericType<StockInfo>() {});

        assertNull(stock);
    }
    
}