package gr.aueb.team1.resource;

import gr.aueb.team1.dao.StockHoldingDAO;
import gr.aueb.team1.dao.impl.StockHoldingDAOImpl;
import gr.aueb.team1.domain.Stock;
import gr.aueb.team1.domain.StockHolding;
import gr.aueb.team1.service.StockHoldingService;
import gr.aueb.team1.service.StockService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("user/{userid}/holding")
public class StockHoldingResource {

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<StockHoldingInfo> listStockHoldings(@PathParam("userid") Integer userid) {
        StockHoldingDAO shd = new StockHoldingDAOImpl();

        StockHoldingService shService = new StockHoldingService(shd);
        List<StockHolding> holdings = shService.showStocks(userid);

        List<StockHoldingInfo> shList = StockHoldingInfo.wrap(holdings);

        return shList;
    }
}
