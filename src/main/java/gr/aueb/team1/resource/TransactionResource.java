package gr.aueb.team1.resource;

import gr.aueb.team1.dao.TransactionDAO;
import gr.aueb.team1.dao.impl.TransactionDAOImpl;
import gr.aueb.team1.domain.Transaction;
import gr.aueb.team1.service.TransactionService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("user/{userid}/transaction")
public class TransactionResource {

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TransactionInfo> listTransactions(@PathParam("userid") Integer userid) {
        TransactionDAO td = new TransactionDAOImpl();

        TransactionService tService = new TransactionService(td);
        List<Transaction> transactions = tService.showTransactions(userid);

        List<TransactionInfo> tList = TransactionInfo.wrap(transactions);

        return tList;
    }

    @GET
    @Path("{transactionid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TransactionInfo getTransaction(
            @PathParam("userid") Integer userid,
            @PathParam("transactionid") Integer tid) {

        TransactionDAO td = new TransactionDAOImpl();

        TransactionService tService = new TransactionService(td);
        Transaction transaction = tService.getTransaction(userid,tid);

        TransactionInfo t = new TransactionInfo(transaction);

        return t;
    }

    @POST
    @Path("deposit")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes("application/x-www-form-urlencoded")
    public TransactionInfo doDeposit(
            @PathParam("userid") Integer userid,
            @FormParam("amount") Double amount) {

        TransactionDAO td = new TransactionDAOImpl();

        TransactionService tService = new TransactionService(td);
        Transaction t = tService.doDeposit(userid, amount);

        TransactionInfo ti = new TransactionInfo(t);

        return ti;
    }

    @POST
    @Path("withdraw")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes("application/x-www-form-urlencoded")
    public TransactionInfo doWithdraw(
            @PathParam("userid") Integer userid,
            @FormParam("amount") Double amount) {

        TransactionDAO td = new TransactionDAOImpl();

        TransactionService tService = new TransactionService(td);
        Transaction t = tService.doWithdraw(userid, amount);

        TransactionInfo ti = new TransactionInfo(t);

        return ti;
    }
}
