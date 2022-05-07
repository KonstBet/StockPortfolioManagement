package org.acme.microprofile;


import io.quarkus.runtime.StartupEvent;
import org.acme.domain.Wallet;
import org.acme.resources.TransactionDTO;
import org.acme.services.TransactionService;
import org.acme.services.WalletService;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Readiness
@ApplicationScoped
public class ReadyCheckProbe implements HealthCheck {

    @Inject
    TransactionService transactionService;

    @Inject
    WalletService walletService;

    private Integer i = -99;

    @Transactional
    void onStart(@Observes StartupEvent ev) {
        TransactionDTO transactionDTO = new TransactionDTO(i.longValue(), 9999.0,"deposit", LocalDateTime.now());

        transactionService.create(transactionDTO);
    }
    @Override
    public HealthCheckResponse call() {
        List<TransactionDTO> transactionDTOList = transactionService.list(i.longValue());

        Wallet wallet = walletService.get(i.longValue());

        if (transactionDTOList.size() == 1 && wallet != null) {
            return HealthCheckResponse.named("Fake user can be read! Database is up!").up()
                    .withData("With id:",wallet.getUserId().toString())
                    .withData("With balance:",wallet.getBalance().toString())
                    .withData("One deposit with id:",transactionDTOList.get(0).getId())
                    .build();
        }

        return HealthCheckResponse.down("Database is down!");
    }
}