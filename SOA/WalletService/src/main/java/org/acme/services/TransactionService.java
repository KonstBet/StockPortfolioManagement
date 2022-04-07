package org.acme.services;

import org.acme.domain.Transaction;
import org.acme.domain.Wallet;
import org.acme.repositories.TransactionRepository;
import org.acme.resources.TransactionDTO;
import org.acme.resources.WalletDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TransactionService {

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    WalletService walletService;

    public List<TransactionDTO> list(Integer userid){
        List<TransactionDTO> listTransactionDTO;
        List<Transaction> listTransactions;

        //DEPOSITS
        listTransactions = transactionRepository.findAllDepositsByUserID(userid);
        listTransactionDTO = listTransactiontoTransactionDTO(listTransactions,"deposit");

        //WITHDRAWS
        listTransactions = transactionRepository.findAllWithdrawsByUserID(userid);
        listTransactionDTO.addAll(listTransactiontoTransactionDTO(listTransactions,"withdraw"));

        return listTransactionDTO;
    }

    public TransactionDTO get(Integer id) {
        Transaction t = transactionRepository.findDepositByID(id);
        if (t == null) {
            t = transactionRepository.findWithdrawByID(id);
            if (t == null) //BAD ID
                return null;
            else //WITHDRAW
                return new TransactionDTO(id,t.getWallet().getUserid(),t.getAmount(),"withdraw",t.getDate());
        }
        else //DEPOSIT
            return new TransactionDTO(id,t.getWallet().getUserid(),t.getAmount(),"deposit",t.getDate());
    }

    public Integer create(TransactionDTO tDTO) {

        Wallet wallet= walletService.get(tDTO.getUserid());

        Transaction t;
        if (wallet == null) {
            if (tDTO.getType().equals("withdraw"))
                return -1;

            t = tDTO.TransactionDTOtoTransaction();
        }
        else {
            if ((wallet = typeActivity(wallet,tDTO)) == null)
                return -1;
            t = tDTO.TransactionDTOtoTransaction(wallet,tDTO.getType());
        }

        if (transactionRepository.saveTransaction(t))
            return t.getId();
        else
            return -1;
    }

    public List<TransactionDTO> listTransactiontoTransactionDTO(List<Transaction> listofTransactions, String type) {
        List<TransactionDTO> listOfTransactionDTO = new ArrayList<TransactionDTO>();
        for (Transaction t : listofTransactions) {
            TransactionDTO tDTO = new TransactionDTO(t.getId(),t.getWallet().getUserid(), t.getAmount(),type,t.getDate());
            listOfTransactionDTO.add(tDTO);
        }
        return listOfTransactionDTO;
    }

    private Wallet typeActivity(Wallet wallet, TransactionDTO transactionDTO) { //OPERATION ON TRANSACTION
        if (transactionDTO.getType().equals("withdraw") && transactionDTO.getAmount() > wallet.getBalance())
            return null;

        if (transactionDTO.getType().equals("withdraw"))
            wallet.setBalance(wallet.getBalance()-transactionDTO.getAmount());
        else wallet.setBalance(wallet.getBalance()+transactionDTO.getAmount());

        return wallet;
    }
}
