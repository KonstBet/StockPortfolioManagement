package org.acme.microprofile;


import io.quarkus.runtime.StartupEvent;
import org.acme.domain.Investor;
import org.acme.resources.UserDTO;
import org.acme.services.UserService;
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
    UserService userService;

    @Transactional
    void onStart(@Observes StartupEvent ev) {
        UserDTO userDTO = new UserDTO(null, "Nikos","Papadopoulos","b68fe43f0d1a0d7aef123722670be50268e15365401c442f8806ef83b612976b",
                "nikosme@mailbox.gr","6987654321",null,"investor",null);

        userService.create(userDTO);
    }

    @Override
    public HealthCheckResponse call() {
        Integer id = 1;
        UserDTO userDTO = userService.get(id.longValue());

        if (userDTO != null) {
            return HealthCheckResponse.named("Fake user can be read! Database is up!").up()
                    .withData("With id:",userDTO.getId())
                    .withData("With email:",userDTO.getEmail())
                    .withData("With name:",userDTO.getName())
                    .withData("With surname:",userDTO.getSurname())
                    .build();
        }

        return HealthCheckResponse.down("Database is down! (Dont run tests..They refresh database!)");
    }
}