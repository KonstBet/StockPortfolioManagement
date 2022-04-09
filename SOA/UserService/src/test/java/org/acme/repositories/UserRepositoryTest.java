package org.acme.repositories;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.domain.Broker;
import org.acme.domain.Investor;
import org.acme.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserRepositoryTest {

    @Inject
    Initializer initializer;

    @Inject
    UserRepository userRepository;

    @BeforeEach
    void initialize() {
        initializer.EraseData();
        initializer.Initialize();
    }

    @Test
    void findInvestorByID() {
        Investor investor = userRepository.findInvestorByID(initializer.getInvestor().getId());

        Assertions.assertEquals(investor.getId(),initializer.getInvestor().getId());
        Assertions.assertEquals(investor.getAuthorizations().size(),initializer.getInvestor().getAuthorizations().size());
        Assertions.assertEquals(investor.getEmail(),initializer.getInvestor().getEmail());
    }

    @Test
    void findBrokerByID() {
        Broker broker = userRepository.findBrokerByID(initializer.getBroker().getId());

        Assertions.assertEquals(broker.getId(),initializer.getBroker().getId());
        Assertions.assertEquals(broker.getAuthorizations().size(),initializer.getBroker().getAuthorizations().size());
        Assertions.assertEquals(broker.getEmail(),initializer.getBroker().getEmail());
        Assertions.assertEquals(broker.getBrokerageFee(),initializer.getBroker().getBrokerageFee());
    }

    @Test
    @Transactional
    void saveUser() {
        Investor investor = new Investor("AA","BB","mail@mail.com","123456789","password");
        userRepository.saveUser(investor);

        User user = userRepository.findInvestorByID(investor.getId());

        Assertions.assertEquals(investor.getId(),user.getId());
        Assertions.assertEquals(investor.getPassword(),user.getPassword());
        Assertions.assertEquals(investor.getEmail(),user.getEmail());
    }
}