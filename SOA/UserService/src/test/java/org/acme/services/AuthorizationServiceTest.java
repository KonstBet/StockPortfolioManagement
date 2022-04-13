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

        authorizationDTOList = authorizationService.listInvestorAuthorizations(initializer.getInvestor().getId());

        Assertions.assertEquals(authorizationDTOList.size(),1);

        authorizationDTOList = authorizationService.listBrokerAuthorizations(initializer.getBroker().getId());

        Assertions.assertEquals(authorizationDTOList.size(),1);
    }

    @Test
    void get() {
        AuthorizationDTO authorizationDTO = authorizationService.findById(initializer.getAuthorization().getId());
        Assertions.assertNotNull(authorizationDTO);
        Assertions.assertEquals(authorizationDTO.getBrokerId(),initializer.getBroker().getId());
        Assertions.assertEquals(authorizationDTO.getInvestorId(),initializer.getInvestor().getId());
    }

    @Test
    void getBadID() {
        AuthorizationDTO authorizationDTO = authorizationService.findById(99999L);
        Assertions.assertNull(authorizationDTO);
    }

    @Test
    @Transactional
    void create() {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(
                null, LocalDateTime.now(),
                LocalDateTime.now().plusDays(30),
                initializer.getInvestor().getId()
        ,initializer.getBroker().getId(), true);

        Boolean flag = authorizationService.createAuthorization(authorizationDTO);

        Assertions.assertTrue(flag);

        List<AuthorizationDTO> authorizationDTOList;

        authorizationDTOList = authorizationService.listInvestorAuthorizations(initializer.getInvestor().getId());

        Assertions.assertEquals(authorizationDTOList.size(),2);
    }
}