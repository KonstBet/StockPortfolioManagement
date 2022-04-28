package org.acme.resources;

import org.acme.domain.Order;
import org.acme.domain.OrderType;
import org.acme.services.OrderService;
import org.acme.services.StockService;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
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
@Retry(maxRetries = 3)
@Timeout(2000)
public class OrderResource {

    @Inject
    OrderService orderService;

    @GET
    @Path("")
    @Counted(name = "OrdersListRequestsCounter", description = "How many requests for orders list has been performed.")
    @Timed(name = "OrdersListTimer", description = "A measure of how long it takes to perform a OrdersList request.", unit = MetricUnits.MILLISECONDS)
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
    @Counted(name = "OrdersGetRequestsCounter", description = "How many requests for order get has been performed.")
    @Timed(name = "OrdersGetTimer", description = "A measure of how long it takes to perform a OrdersGet request.", unit = MetricUnits.MILLISECONDS)
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
    @Counted(name = "OrdersExecuteRequestsCounter", description = "How many requests for order execute/create has been performed.")
    @Timed(name = "OrdersExecuteTimer", description = "A measure of how long it takes to perform a OrdersExecute request.", unit = MetricUnits.MILLISECONDS)
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
