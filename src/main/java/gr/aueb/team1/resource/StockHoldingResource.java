package gr.aueb.team1.resource;

import gr.aueb.team1.dao.StockHoldingDAO;
import gr.aueb.team1.dao.impl.StockHoldingDAOImpl;
import gr.aueb.team1.domain.StockHolding;
import gr.aueb.team1.service.StockHoldingService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("stockholding/{userid}")
public class StockHoldingResource {

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<StockHoldingInfo> listStockHoldings(@PathParam("userid") Integer userid) {

        try {
            StockHoldingDAO shd = new StockHoldingDAOImpl();

            StockHoldingService shService = new StockHoldingService(shd);
            List<StockHolding> holdings = shService.showStockHoldings(userid);

            List<StockHoldingInfo> shList = StockHoldingInfo.wrap(holdings);

            return shList;
        }
        catch(NullPointerException e) {
            return null;
        }
    }

    @GET
    @Path("{stockholdingid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public StockHoldingInfo getStockHolding(
            @PathParam("userid") Integer userid,
            @PathParam("stockholdingid") Integer shid) {

        try {
            StockHoldingDAO shd = new StockHoldingDAOImpl();

            StockHoldingService shService = new StockHoldingService(shd);
            StockHolding holding = shService.getStockHolding(userid,shid);

            StockHoldingInfo sh = new StockHoldingInfo(holding);

            return sh;
        }
        catch(NullPointerException e) {
            return null;
        }
    }
}
