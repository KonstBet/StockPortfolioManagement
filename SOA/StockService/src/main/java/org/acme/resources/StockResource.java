package org.acme.resources;

import org.acme.domain.Stock;
import org.acme.services.StockService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/stocks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StockResource {

    @Inject
    StockService stockService;

    @GET
    @Path("")
    public Response list(){

        try{

            List<StockDTO> stockDTOList = stockService.getAllStocks();

            if (stockDTOList == null) return Response.status(404).build();

            return Response.ok(stockDTOList).build();

        }catch(Exception e){return Response.status(500).build();}
    }

    @POST
    @Transactional
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
    public Response getStock(@PathParam("stock_id") Long stockId){
        try {
            StockDTO stockDTO = stockService.getStockById(stockId);

            if (stockDTO == null) return Response.status(404).build();

            return Response.ok(stockDTO).build();

        } catch(Exception e) {return Response.status(500).build();}
    }


}
