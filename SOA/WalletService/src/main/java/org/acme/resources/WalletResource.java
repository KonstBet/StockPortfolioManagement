package org.acme.resources;

import org.acme.domain.Transaction;
import org.acme.domain.Wallet;
import org.acme.services.WalletService;

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
    public WalletDTO get(WalletDTO walletDTO) {
        try {
            Wallet wallet = walletService.get(walletDTO.getUserid());

            walletDTO.setUserid(null);
            walletDTO.setBalance(wallet.getBalance());
            return walletDTO;
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
            return walletService.update(walletDTO);
        }
        catch(Exception e) {
            return null;
        }
    }

}
