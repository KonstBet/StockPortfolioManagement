package org.acme.repositories;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.domain.Authorization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;

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
}