package org.acme.resources;

import org.acme.domain.StockHolding;
import org.acme.services.StockHoldingService;
import org.acme.services.StockService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/stockholdings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StockHoldingResource {

    @Inject
    StockHoldingService stockHoldingService;

    @GET
    @Path("")
    public Response list(@QueryParam("user_id") Long userId){

        try{

            List<StockHoldingDTO> stockHoldingDTOList = stockHoldingService.getUserStockHoldings(userId);

            if (stockHoldingDTOList == null) return Response.status(404).build();

            return Response.ok(stockHoldingDTOList).build();

        }catch(Exception e){return Response.status(500).build();}
    }

    @GET
    @Path("/{stockholding_id}")
    public Response getStock(@PathParam("stockholding_id") Long stockHoldingId){
        try {
            StockHoldingDTO stockHoldingDTO = stockHoldingService.getStockHoldingById(stockHoldingId);

            if (stockHoldingDTO == null) return Response.status(404).build();

            return Response.ok(stockHoldingDTO).build();

        } catch(Exception e) {return null;}
    }

    @PUT
    @Transactional
    @Path("/{stockholding_id}/status")
    public Response changeStockHoldingStatus(@PathParam("stockholding_id") Long stockHoldingId, StockHoldingDTO stockHoldingDTO){
        try{

           if(!stockHoldingService.updateStockHoldingStatus(stockHoldingId, stockHoldingDTO.getLocked())){
               return Response.status(400).build();
           }

           return Response.ok().build();



        }catch(Exception e){return Response.status(500).build();}
    }

}
