package org.acme.services;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.repositories.Initializer;
import org.acme.resources.AuthorizationDTO;
import org.acme.resources.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class AuthorizationServiceTest {

    @Inject
    Initializer initializer;

    @Inject
    AuthorizationService authorizationService;

    @BeforeEach
    void initialize() {
        initializer.EraseData();
        initializer.Initialize();
    }

    @Test
    void list() {
        List<AuthorizationDTO> authorizationDTOList;

        authorizationDTOList = authorizationService.list(initializer.getInvestor().getId());

        Assertions.assertEquals(authorizationDTOList.size(),2);

        authorizationDTOList = authorizationService.list(initializer.getBroker().getId());

        Assertions.assertEquals(authorizationDTOList.size(),2);
    }

    @Test
    void get() {
        AuthorizationDTO authorizationDTO = authorizationService.get(initializer.getAuthCapital().getId());

        Assertions.assertNotNull(authorizationDTO);
        Assertions.assertEquals(authorizationDTO.getType(),"AuthCapital");
        Assertions.assertEquals(authorizationDTO.getAmount(),initializer.getAuthCapital().getAmount());
    }

    @Test
    @Transactional
    void create() {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(null, LocalDateTime.now(),LocalDateTime.now().plusDays(30),initializer.getInvestor().getId()
        ,initializer.getBroker().getId(),"AuthStock",200.0,2);

        Boolean flag = authorizationService.create(authorizationDTO);

        Assertions.assertTrue(flag);


        List<AuthorizationDTO> authorizationDTOList;

        authorizationDTOList = authorizationService.list(initializer.getInvestor().getId());

        Assertions.assertEquals(authorizationDTOList.size(),3);
    }
}