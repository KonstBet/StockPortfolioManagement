package org.acme.resources;

import org.acme.domain.Order;
import org.acme.domain.OrderType;
import org.acme.services.OrderService;
import org.acme.services.StockService;
import org.mockito.internal.matchers.Or;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @GET
    @Path("")
    public Response getOrders(@QueryParam("user_id") Long userId, @QueryParam("type") OrderType type){

        try{

            List<OrderDTO> orderDTOS;

            if(type == OrderType.PURCHASE)  orderDTOS = orderService.getPurchaseOrders(userId);
            else if(type == OrderType.SALE) orderDTOS = orderService.getSaleOrders(userId);
            else orderDTOS = orderService.getOrders(userId);

            if(orderDTOS == null) return Response.status(404).build();

            return Response.ok(orderDTOS).build();
        }catch(Exception e){return Response.status(500).build();}

    }


    @GET
    @Path("/{order_id}")
    public Response getOrderById(@PathParam("order_id") Long orderId){

        try{

            OrderDTO orderDTO = orderService.getOrderById(orderId);
            if(orderDTO == null) return Response.status(404).build();

            return Response.ok(orderDTO).build();

        }catch(Exception e){return Response.status(500).build();}
    }

    @POST
    @Transactional
    @Path("")
    public Response executeOrder(OrderDTO orderDTO){

        try{
            if(orderService.createOrder(orderDTO)){
                return Response.ok().build();
            }
            // creation failed!
            return Response.status(400).build();
        }catch(Exception e){return Response.status(500).build();}
    }


}
