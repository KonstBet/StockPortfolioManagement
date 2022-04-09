package org.acme.repositories;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.domain.AuthCapital;
import org.acme.domain.AuthStock;
import org.acme.domain.Authorization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class AuthorizationRepositoryTest {

    @Inject
    Initializer initializer;

    @Inject
    AuthorizationRepository authorizationRepository;

    @BeforeEach
    void initialize() {
        initializer.EraseData();
        initializer.Initialize();
    }

    @Test
    void findAuthStockByID() {
        AuthStock authStock = authorizationRepository.findAuthStockByID(initializer.getAuthStock().getId());

        Assertions.assertEquals(authStock.getAmount(),initializer.getAuthStock().getAmount());
        Assertions.assertEquals(authStock.getInvestor().getId(),initializer.getInvestor().getId());
    }

    @Test
    void findAuthCapitalByID() {
        AuthCapital authCapital = authorizationRepository.findAuthCapitalByID(initializer.getAuthCapital().getId());

        Assertions.assertEquals(authCapital.getAmount(),initializer.getAuthCapital().getAmount());
        Assertions.assertEquals(authCapital.getInvestor().getId(),initializer.getInvestor().getId());
    }

    @Test
    void findAllAuthStocksByUserID() {
        List<Authorization> authorizationList =  authorizationRepository.findAllAuthStocksByUserID(initializer.getBroker().getId());

        Assertions.assertEquals(authorizationList.size(),1);
    }

    @Test
    void findAllAuthCapitalsByUserID() {
        List<Authorization> authorizationList =  authorizationRepository.findAllAuthCapitalsByUserID(initializer.getBroker().getId());

        Assertions.assertEquals(authorizationList.size(),1);
    }

    @Test
    @Transactional
    void saveAuthorization() {
        AuthCapital authCapital = new AuthCapital(null,null,initializer.getStartTime(),initializer.getEndTime(),9999.0);

        authorizationRepository.saveAuthorization(authCapital);

        AuthCapital authCapital2 = authorizationRepository.findAuthCapitalByID(authCapital.getId());

        Assertions.assertEquals(authCapital2.getStartdate(),authCapital.getStartdate());
        Assertions.assertEquals(authCapital2.getAmount(),9999.0);
    }
}