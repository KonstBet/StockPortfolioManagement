package org.acme.microprofile;


import io.quarkus.runtime.StartupEvent;
import org.acme.resources.StockDTO;
import org.acme.services.StockService;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Readiness
@ApplicationScoped
public class ReadyCheckProbe implements HealthCheck {

    @Inject
    StockService stockService;

    @Transactional
    void onStart(@Observes StartupEvent ev) {
        StockDTO stockDTO = new StockDTO(null,"Fake Company IO", 131.67, 131.05, 133.9, 131.49, 10000.0);

        stockService.createStock(stockDTO);
    }

    @Override
    public HealthCheckResponse call() {
        Integer id = 1;
        StockDTO stockDTO = stockService.getStockById(id.longValue());

        if (stockDTO != null) {
            return HealthCheckResponse.named("Fake stock can be read! Database is up!").up()
                    .withData("With id:",stockDTO.getId())
                    .withData("With company name:",stockDTO.getCompanyName())
                    .withData("With vol:",stockDTO.getVol().toString())
                    .build();
        }

        return HealthCheckResponse.down("Database is down! (Dont run tests..They refresh database!)");
    }
}