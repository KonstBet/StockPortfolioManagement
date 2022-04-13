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
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        try {
            Wallet wallet = walletService.get(id);

            if (wallet == null) {
                WalletDTO walletDTO = new WalletDTO();
                walletDTO.setBalance(0.0);
                return Response.ok(walletDTO).build();
            }

            WalletDTO walletDTO = new WalletDTO();
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
