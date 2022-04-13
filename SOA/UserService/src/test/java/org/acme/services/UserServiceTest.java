package org.acme.services;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.domain.Address;
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
    void listBadType() {
        List<UserDTO> userDTOList = userService.list("BAD TYPE");

        Assertions.assertNull(userDTOList);
    }

    @Test
    void get() {
        UserDTO userDTO = userService.get(initializer.getInvestor().getId());

        Assertions.assertNotNull(userDTO);
        Assertions.assertEquals(userDTO.getEmail(),"nikosme@mailbox.gr");

        userDTO = userService.get(initializer.getBroker().getId());

        Assertions.assertNotNull(userDTO);
        Assertions.assertEquals(userDTO.getEmail(),"makhsg@mailbox.gr");
    }

    @Test
    void getBadID() {
        UserDTO userDTO = userService.get(99999L);

        Assertions.assertNull(userDTO);
    }

    @Test
    @Transactional
    void update() {
        Address address = new Address("dromos","123","23145");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("papaki@mail.com");
        userDTO.setName("NAME");
        userDTO.setSurname("SURNAME");
        userDTO.setPassword("1223334556");
        userDTO.setPhoneNo("123456778");
        userDTO.setAddress(address);

        //1
        Boolean flag = userService.update(initializer.getInvestor().getId(),userDTO);

        UserDTO usrDTO = userService.get(initializer.getInvestor().getId());

        Assertions.assertTrue(flag);
        Assertions.assertEquals(usrDTO.getEmail(),"papaki@mail.com");
        Assertions.assertEquals(usrDTO.getAddress(),address);

        //2
        userDTO.setBrokerageFee(100.0);

        flag = userService.update(initializer.getBroker().getId(),userDTO);

        usrDTO = userService.get(initializer.getBroker().getId());

        Assertions.assertTrue(flag);
        Assertions.assertEquals(usrDTO.getEmail(),"papaki@mail.com");
        Assertions.assertEquals(usrDTO.getAddress(),address);

        //3
        flag = userService.update(999999L,userDTO);

        Assertions.assertFalse(flag);
    }

    @Test
    @Transactional
    void createBroker() {
        UserDTO userDTO = new UserDTO(null,"giannhs","papad",
                "987654321","gp@email.com","987654321",null,"broker",10.0);

        Boolean flag = userService.create(userDTO);

        Assertions.assertTrue(flag);

        List<UserDTO> userDTOList = userService.list("broker");

        Assertions.assertEquals(userDTOList.size(),2);
    }

    @Test
    @Transactional
    void createBadBroker() {
        UserDTO userDTO = new UserDTO(null,"giannhs","papad",
                "987654321","gp@email.com","987654321",null,"broker",null);

        Boolean flag = userService.create(userDTO);

        Assertions.assertFalse(flag);

        List<UserDTO> userDTOList = userService.list("broker");

        Assertions.assertEquals(userDTOList.size(),1);
    }

    @Test
    @Transactional
    void createInvestor() {
        UserDTO userDTO = new UserDTO(null,"giannhs","papad",
                "987654321","gp@email.com","987654321",null,"investor",10.0);

        Boolean flag = userService.create(userDTO);

        Assertions.assertTrue(flag);

        List<UserDTO> userDTOList = userService.list("investor");

        Assertions.assertEquals(userDTOList.size(),2);
    }

    @Test
    @Transactional
    void createBadData() {
        //1   //NO NAME
        UserDTO userDTO = new UserDTO(null,null,"papad",
                "987654321","gp@email.com","987654321",null,"broker",10.0);

        Boolean flag = userService.create(userDTO);

        Assertions.assertFalse(flag);

        List<UserDTO> userDTOList = userService.list("broker");

        Assertions.assertEquals(userDTOList.size(),1);

        //2   //BAD TYPE
       userDTO = new UserDTO(null,"giannhs","papad",
                "987654321","gp@email.com","987654321",null,"BAD TYPE",10.0);

        flag = userService.create(userDTO);

        Assertions.assertFalse(flag);
    }
}