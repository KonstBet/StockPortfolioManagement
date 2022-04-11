package org.acme.resources;

import org.acme.services.AuthorizationService;
import org.acme.services.WalletService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/authorizations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorizationResource {

    @Inject
    AuthorizationService authorizationService;

    @Inject
    @RestClient
    WalletService walletService;

    @GET
    @Path("")
    public Response list(@QueryParam("userid") Integer userid) {
        try {
            List<AuthorizationDTO> authorizationDTOList = authorizationService.list(userid);

            if (authorizationDTOList == null || authorizationDTOList.size() == 0)
                return Response.status(404).build();
            return Response.ok(authorizationDTOList).build();
        }
        catch(Exception e) {
            return null;
        }
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Integer id) {
        try {
            AuthorizationDTO authorizationDTO = authorizationService.get(id);
            if (authorizationDTO == null)
                return Response.status(404).build();
            return Response.ok(authorizationDTO).build();
        }
        catch(Exception e) {
            return Response.status(404).build();
        }
    }

    @POST
    @Path("")
    @Transactional
    public Response create(AuthorizationDTO authorizationDTO) {
        try {
            if (!authorizationService.create(authorizationDTO))
                return Response.status(400).build();

            System.out.println("AAAAAA");
            //READ WALLET BALANCE
            WalletDTO walletDTO = new WalletDTO();
            walletDTO.setUserid(authorizationDTO.getInvestorid());System.out.println("AAA");

            Integer userid = authorizationDTO.getInvestorid();

            Response response = walletService.get(userid);
            System.out.println("BBBBB");System.out.println(response);
            //CHANGE WALLET BALANCE
            walletDTO = (WalletDTO) response.getEntity();System.out.println("FFF");System.out.println(walletDTO.getBalance());
            walletDTO.setBalance(walletDTO.getBalance() - authorizationDTO.getAmount());System.out.println(walletDTO.getBalance());
            response = walletService.update(walletDTO);

            System.out.println("CCC");
            if (response.getStatus() == 200)
                return Response.ok().build();
            else return Response.status(400).build();
        }
        catch(Exception e) {
            System.out.println(e);
            return Response.status(400).build();
        }
    }
}
