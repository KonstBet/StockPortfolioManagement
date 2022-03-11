package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.AuthorizationDAO;
import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.domain.Authorization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;


class AuthorizationDAOImplTest {

    private AuthorizationDAO authorizationDAO;
    Initializer init;

    @BeforeEach
    void Initialize() {
        init = new Initializer();
        init.prepareData();

        authorizationDAO = new AuthorizationDAOImpl();
    }

    @Test
    void findAll() {
        List<Authorization> authorizations = authorizationDAO.findAll();
        Assertions.assertEquals(4,authorizations.size());
    }

    @Test
    void findById() {
        Authorization authorization = authorizationDAO.findById(init.investor.getAuthorizations().iterator().next().getId());
        Assertions.assertNotNull(authorization);
    }

    @Test
    void save() {
        Authorization authorization = authorizationDAO.findById(init.investor.getAuthorizations().iterator().next().getId());
        LocalDateTime date = LocalDateTime.now();
        authorization.setStartdate(LocalDateTime.now());

        authorizationDAO.save(authorization);
        authorization = authorizationDAO.findById(init.investor.getAuthorizations().iterator().next().getId());

        Assertions.assertTrue(authorization.getStartdate().isEqual(date));
    }

    @Test
    void delete() {
        Authorization authorization = authorizationDAO.findById(init.investor.getAuthorizations().iterator().next().getId());

        authorizationDAO.delete(authorization);
        List<Authorization> authorizations = authorizationDAO.findAll();
        Assertions.assertEquals(3,authorizations.size());
    }

    @Test
    void findAllByBrokerID() {
        List<Authorization> authorizations = authorizationDAO.findAllByBrokerID(init.broker.getId());
        Assertions.assertEquals(2,authorizations.size());
    }

    @Test
    void findAllByInvestorID() {
        List<Authorization> authorizations = authorizationDAO.findAllByInvestorID(init.investor.getId());
        Assertions.assertEquals(4,authorizations.size());
    }
}