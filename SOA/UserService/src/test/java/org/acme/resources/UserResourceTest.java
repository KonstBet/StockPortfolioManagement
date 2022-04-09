package org.acme.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.repositories.Initializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserResourceTest {

    @Inject
    Initializer initializer;

    @BeforeEach
    void initialize() {
        initializer.EraseData();
        initializer.Initialize();
    }

    @Test
    void list() {

        List list = given()
                .when()
                .queryParam("type","investor")
                .get("/users")
                .then()
                .statusCode(200)
                .extract().
                as(List.class);

        Assertions.assertEquals(list.size(),1);
    }

    @Test
    void get() {

        UserDTO userDTO =
                given()
                        .pathParam("id",initializer.getInvestor().getId())
                        .when()
                        .get("/users/{id}")
                        .then()
                        .statusCode(200)
                        .extract().as(UserDTO.class);

        Assertions.assertEquals(userDTO.getEmail(),initializer.getInvestor().getEmail());
        Assertions.assertEquals(userDTO.getSurname(),initializer.getInvestor().getSurname());
        Assertions.assertEquals(userDTO.getType(),"investor");
        Assertions.assertEquals(userDTO.getEmail(),initializer.getInvestor().getEmail());
    }

    @Test
    void update() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("papaki@mail.com");

        given()
                .contentType(ContentType.JSON)
                .pathParam("id",initializer.getInvestor().getId())
                .body(userDTO)
                .when()
                .put("/users/{id}")
                .then()
                .statusCode(200);

        userDTO =
                given()
                        .pathParam("id",initializer.getInvestor().getId())
                        .when()
                        .get("/users/{id}")
                        .then()
                        .statusCode(200)
                        .extract().as(UserDTO.class);

        Assertions.assertEquals(userDTO.getEmail(),"papaki@mail.com");
    }
}