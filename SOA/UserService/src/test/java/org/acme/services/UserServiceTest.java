package org.acme.services;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.repositories.Initializer;
import org.acme.resources.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wildfly.common.Assert;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserServiceTest {

    @Inject
    Initializer initializer;

    @Inject
    UserService userService;

    @BeforeEach
    void initialize() {
        initializer.EraseData();
        initializer.Initialize();
    }

    @Test
    void list() {
        List<UserDTO> userDTOList = userService.list(null);

        Assertions.assertEquals(userDTOList.size(),2);

        userDTOList = userService.list("investor");

        Assertions.assertEquals(userDTOList.size(),1);

        userDTOList = userService.list("broker");

        Assertions.assertEquals(userDTOList.size(),1);
    }

    @Test
    void get() {
        UserDTO userDTO = userService.get(initializer.getInvestor().getId());

        Assertions.assertNotNull(userDTO);
        Assertions.assertEquals(userDTO.getEmail(),"nikosme@mailbox.gr");
    }

    @Test
    @Transactional
    void update() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("papaki@mail.com");

        //1
        Boolean flag = userService.update(initializer.getInvestor().getId(),userDTO);

        UserDTO usrDTO = userService.get(initializer.getInvestor().getId());

        Assertions.assertTrue(flag);
        Assertions.assertEquals(usrDTO.getEmail(),"papaki@mail.com");

        //2
        flag = userService.update(initializer.getBroker().getId(),userDTO);

        usrDTO = userService.get(initializer.getBroker().getId());

        Assertions.assertTrue(flag);
        Assertions.assertEquals(usrDTO.getEmail(),"papaki@mail.com");

        //3
        flag = userService.update(999999,userDTO);

        Assertions.assertFalse(flag);
    }

    @Test
    @Transactional
    void create() {
        UserDTO userDTO = new UserDTO(null,"giannhs","papad","987654321","gp@email.com","987654321",null,"broker",10.0);

        Boolean flag = userService.create(userDTO);

        Assertions.assertTrue(flag);

        List<UserDTO> userDTOList = userService.list("broker");

        Assertions.assertEquals(userDTOList.size(),2);
    }
}