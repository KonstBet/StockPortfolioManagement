package gr.aueb.team1.dao.impl;

import gr.aueb.team1.dao.Initializer;
import gr.aueb.team1.persistence.JPAUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @BeforeEach
    void Initialize() {
        Initializer init = new Initializer();
        init.prepareData();
    }

    @Test
    void findAll() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}