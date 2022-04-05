package org.acme.repositories;

import org.acme.domain.Deposit;
import org.acme.domain.Wallet;
import org.acme.domain.Withdrawal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@ApplicationScoped
public class Initializer {

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    WalletRepository walletRepository;

    private Wallet wallet;
    private Deposit deposit;
    private Withdrawal withdraw;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }

    public Withdrawal getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Withdrawal withdraw) {
        this.withdraw = withdraw;
    }

    @Transactional
    public void EraseData() {
        transactionRepository.deleteAll();
        transactionRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Transactional
    public void Initialize() {
        wallet = new Wallet(1,1000.0);
        deposit = new Deposit(wallet,1000.0, LocalDateTime.now());
        wallet.getTransactions().add(deposit);

        withdraw = new Withdrawal(wallet, 1000.0, LocalDateTime.now());
        wallet.getTransactions().add(withdraw);

        walletRepository.saveWallet(wallet);
        transactionRepository.saveTransaction(deposit);
        transactionRepository.saveTransaction(withdraw);
    }
}
