package org.acme.services;

import org.acme.domain.Transaction;
import org.acme.domain.Wallet;
import org.acme.repositories.TransactionRepository;
import org.acme.resources.TransactionDTO;
import org.acme.resources.WalletDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class TransactionService {

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    WalletService walletService;

    public List<Transaction> list(Integer userid){
        return transactionRepository.findAllByUserID(userid);
    }

    public Transaction get(Integer id) {
        return transactionRepository.findByID(id);
    }

    public Response create(TransactionDTO tDTO) {

        Wallet wallet= walletService.get(tDTO.getUserid());

        Transaction t;
        if (wallet == null) {
            if (tDTO.getType().equals("withdraw"))
                return Response.status(400).build();
            t = tDTO.TransactionDTOtoTransaction();
        }
        else {
            if ((wallet = typeActivity(wallet,tDTO)) == null)
                return Response.status(400).build();
            t = tDTO.TransactionDTOtoTransaction(wallet);
        }
        if (transactionRepository.saveTransaction(t))
            return Response.created(URI.create("/transaction/" + t.getId())).build();
        else
            return Response.status(400).build();
    }

    private Wallet typeActivity(Wallet wallet, TransactionDTO transactionDTO) {
        if (transactionDTO.getType().equals("withdraw") && transactionDTO.getAmount() > wallet.getBalance())
            return null;

        if (transactionDTO.getType().equals("withdraw"))
            wallet.setBalance(wallet.getBalance()-transactionDTO.getAmount());
        else wallet.setBalance(wallet.getBalance()+transactionDTO.getAmount());

        return wallet;
    }
}
