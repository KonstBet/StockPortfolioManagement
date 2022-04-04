package org.acme.services;

import org.acme.domain.Transaction;
import org.acme.domain.Wallet;
import org.acme.repositories.WalletRepository;
import org.acme.resources.TransactionDTO;
import org.acme.resources.WalletDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.net.URI;

@ApplicationScoped
public class WalletService {

    @Inject
    WalletRepository walletRepository;

    public Wallet get(Integer id) {
        Wallet wallet = walletRepository.findByID(id);
        if (wallet == null) return null;

        return wallet;
    }

    public Response update(WalletDTO walletDTO) {
        Wallet wallet = walletRepository.findByID(walletDTO.getUserid());
        if (wallet == null)
            return Response.status(400).build();

        wallet.setBalance(walletDTO.getBalance());
        if (walletRepository.saveWallet(wallet))
            return Response.ok().build();
        else
            return Response.status(400).build();
    }
}
