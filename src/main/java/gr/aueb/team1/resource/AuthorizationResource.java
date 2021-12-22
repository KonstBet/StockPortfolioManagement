package gr.aueb.team1.resource;

import gr.aueb.team1.dao.AuthorizationDAO;
import gr.aueb.team1.dao.AuthorizationDAO;
import gr.aueb.team1.dao.impl.AuthorizationDAOImpl;
import gr.aueb.team1.domain.AuthCapital;
import gr.aueb.team1.domain.AuthStock;
import gr.aueb.team1.domain.Authorization;
import gr.aueb.team1.service.AuthorizationService;
import gr.aueb.team1.service.AuthorizationService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static gr.aueb.team1.constants.CONSTANTS.dateTimeFormatter;

@Path("authorization/{userid}")
public class AuthorizationResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AuthorizationInfo> listAuths(@PathParam("userid") Integer userid) {

        try {
            AuthorizationDAO ad = new AuthorizationDAOImpl();

            AuthorizationService authService = new AuthorizationService(ad);
            List<Authorization> Auths = authService.showAuthorizations(userid);

            List<AuthorizationInfo> AuthsList = AuthorizationInfo.wrap(Auths);

            return AuthsList;
        }
        catch(NullPointerException e) {
            return null;
        }
    }

    @GET
    @Path("{authorizationid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public AuthorizationInfo getAuthorization(
            @PathParam("userid") Integer userid,
            @PathParam("authorizationid") Integer authid) {

        try {
            AuthorizationDAO ad = new AuthorizationDAOImpl();

            AuthorizationService authService = new AuthorizationService(ad);

            AuthStock as = authService.getAuthStock(userid,authid);
            if (as != null) {
                AuthorizationInfo ai = new AuthorizationInfo(as);
                return ai;
            }

            AuthCapital ac = authService.getAuthCapital(userid,authid);
            if (ac != null) {
                AuthorizationInfo ai = new AuthorizationInfo(ac);
                return ai;
            }

            return null;
        }
        catch(NullPointerException e) {
            return null;
        }
    }


    @POST
    @Path("givecapitalauthorization")
    @Consumes("application/x-www-form-urlencoded")
    public Response giveCapitalAuthorization(
            @PathParam("userid") Integer userid,
            @FormParam("amount") Double amount,
            @FormParam("brokerid") Integer brokerid,
            @FormParam("enddate") String endDate)
    {

        try {

            AuthorizationDAO td = new AuthorizationDAOImpl();

            AuthorizationService authService = new AuthorizationService(td);
            Authorization auth =  authService.giveCapitalAuthorization(userid, amount, brokerid, LocalDateTime.parse(endDate,dateTimeFormatter));

            UriBuilder ub = uriInfo.getBaseUriBuilder().path("authorization/"+userid+"/"+auth.getId());
            URI AuthUri = ub.build();

            return Response.created(AuthUri).build();
        }
        catch(NullPointerException e) {
            return null;
        }
    }

    @POST
    @Path("givestockauthorization")
    @Consumes("application/x-www-form-urlencoded")
    public Response giveStockAuthorization(
            @PathParam("userid") Integer userid,
            @FormParam("amount") Integer amount,
            @FormParam("stockholdingid") Integer stockholdingid,
            @FormParam("brokerid") Integer brokerid,
            @FormParam("enddate") String endDate)
    {
        try {

            AuthorizationDAO td = new AuthorizationDAOImpl();

            AuthorizationService authService = new AuthorizationService(td);
            Authorization auth =  authService.giveStockAuthorization(userid, amount, stockholdingid, brokerid, LocalDateTime.parse(endDate,dateTimeFormatter));

            UriBuilder ub = uriInfo.getBaseUriBuilder().path("authorization/"+userid+"/"+auth.getId());
            URI AuthUri = ub.build();

            return Response.created(AuthUri).build();
        }
        catch(NullPointerException e) {
            return null;
        }
    }
}
