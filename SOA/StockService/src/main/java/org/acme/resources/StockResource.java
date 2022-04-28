package org.acme.resources;

import org.acme.domain.Stock;
import org.acme.services.StockService;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/stocks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Retry(maxRetries = 3)
@Timeout(2000)
public class StockResource {

    @Inject
    StockService stockService;

    @GET
    @Path("")
    @Counted(name = "StocksListRequestsCounter", description = "How many requests for stocks list has been performed.")
    @Timed(name = "StocksListTimer", description = "A measure of how long it takes to perform a StocksList request.", unit = MetricUnits.MILLISECONDS)
    public Response list(){

        try{

            List<StockDTO> stockDTOList = stockService.getAllStocks();

            if (stockDTOList == null) return Response.status(404).build();

            return Response.ok(stockDTOList).build();

        }catch(Exception e){return Response.status(500).build();}
    }

    @POST
    @Transactional
    @Counted(name = "StocksCreateRequestsCounter", description = "How many requests for stock create has been performed.")
    @Timed(name = "StocksCreateTimer", description = "A measure of how long it takes to perform a StocksCreate request.", unit = MetricUnits.MILLISECONDS)
    public Response createStock(StockDTO stockDTO){

        try{

            if(stockService.createStock(stockDTO)){
                return Response.ok().build();
            }
            return Response.status(400).build();

        }catch(Exception e){return Response.status(500).build();}

    }

    @GET
    @Path("/{stock_id}")
    @Counted(name = "StocksGetRequestsCounter", description = "How many requests for stock get has been performed.")
    @Timed(name = "StocksGetTimer", description = "A measure of how long it takes to perform a StocksGet request.", unit = MetricUnits.MILLISECONDS)
    public Response getStock(@PathParam("stock_id") Long stockId){
        try {
            StockDTO stockDTO = stockService.getStockById(stockId);

            if (stockDTO == null) return Response.status(404).build();

            return Response.ok(stockDTO).build();

        } catch(Exception e) {return Response.status(500).build();}
    }


}
