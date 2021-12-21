package gr.aueb.team1.resource;

import gr.aueb.team1.dao.TransactionDAO;
import gr.aueb.team1.dao.impl.TransactionDAOImpl;
import gr.aueb.team1.domain.Transaction;
import gr.aueb.team1.service.TransactionService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("transaction/{userid}")
public class TransactionResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TransactionInfo> listTransactions(@PathParam("userid") Integer userid) {

        try {
            TransactionDAO td = new TransactionDAOImpl();

            TransactionService tService = new TransactionService(td);
            List<Transaction>transactions = tService.showDeposits(userid);
            List<TransactionInfo> DepositsList = TransactionInfo.wrap(transactions,"deposit");

            transactions = tService.showWithdraws(userid);
            List<TransactionInfo> WithdrawsList = TransactionInfo.wrap(transactions,"withdraw");

            DepositsList.addAll(WithdrawsList);

            return DepositsList;
        }
        catch(NullPointerException e) {
            return null;
        }
    }


    @POST
    @Path("deposit")
    @Consumes("application/x-www-form-urlencoded")
    public Response doDeposit(
            @PathParam("userid") Integer userid,
            @FormParam("amount") Double amount) {
        try {

            TransactionDAO td = new TransactionDAOImpl();

            TransactionService tService = new TransactionService(td);
            tService.doDeposit(userid, amount);

            UriBuilder ub = uriInfo.getBaseUriBuilder().path("transaction/"+userid);
            URI transactionsUri = ub.build();

            return Response.created(transactionsUri).build();
        }
            catch(NullPointerException e) {
            return null;
        }
    }

    @POST
    @Path("withdraw")
    @Consumes("application/x-www-form-urlencoded")
    public Response doWithdraw(
            @PathParam("userid") Integer userid,
            @FormParam("amount") Double amount) {

        try {
            TransactionDAO td = new TransactionDAOImpl();

            TransactionService tService = new TransactionService(td);
            Transaction t = tService.doWithdraw(userid, amount);

            UriBuilder ub = uriInfo.getBaseUriBuilder().path("transaction/"+userid);
            URI transactionsUri = ub.path(Integer.toString(t.getId())).build();

            return Response.created(transactionsUri).build();
        }
            catch(NullPointerException e) {
            return null;
        }
    }
}
