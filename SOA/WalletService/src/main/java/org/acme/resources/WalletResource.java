package org.acme.resources;

import org.acme.domain.Transaction;
import org.acme.domain.Wallet;
import org.acme.services.WalletService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/balance")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WalletResource {

    @Inject
    WalletService walletService;

    @GET
    @Path("")
    public Response get(WalletDTO walletDTO) {
        try {
            Wallet wallet = walletService.get(walletDTO.getUserid());

            if (wallet == null)
                return Response.status(404).build();

            walletDTO.setUserid(null);
            walletDTO.setBalance(wallet.getBalance());
            return Response.ok(walletDTO).build();
        }
        catch(Exception e) {
            return null;
        }
    }

    @PUT
    @Path("")
    @Transactional
    public Response update(WalletDTO walletDTO) {
        try {
            if (!walletService.update(walletDTO))
                return Response.status(400).build();
            return Response.ok().build();
        }
        catch(Exception e) {
            return null;
        }
    }

}
