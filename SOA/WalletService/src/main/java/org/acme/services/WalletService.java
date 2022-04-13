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

    public Wallet get(Long id) {
        Wallet wallet = walletRepository.findByID(id);
        if (wallet == null) return null;

        return wallet;
    }

    public Boolean update(WalletDTO walletDTO) {
        Wallet wallet = walletRepository.findByID(walletDTO.getUserId());
        if (wallet == null)
            return false;

        wallet.setBalance(walletDTO.getBalance());
        walletRepository.saveWallet(wallet);
        return true;
    }
}
