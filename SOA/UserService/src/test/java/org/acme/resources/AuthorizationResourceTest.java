package org.acme.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.repositories.Initializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class AuthorizationResourceTest {

    @Inject
    Initializer initializer;

    @BeforeEach
    void initialize() {
        initializer.EraseData();
        initializer.Initialize();
    }

    @Test
    void list() {

        Integer userid = initializer.getInvestor().getId();

        List list = given()
                .contentType(ContentType.JSON)
                .body(userid)
                .when()
                .get("/authorizations")
                .then()
                .statusCode(200)
                .extract().
                as(List.class);

        Assertions.assertEquals(list.size(),2);
    }

    @Test
    void get() {

        AuthorizationDTO authorizationDTO =
                given()
                        .pathParam("id",initializer.getAuthCapital().getId())
                        .when()
                        .get("/authorizations/{id}")
                        .then()
                        .statusCode(200)
                        .extract().as(AuthorizationDTO.class);

        Assertions.assertEquals(authorizationDTO.getType(),"AuthCapital");
        Assertions.assertEquals(authorizationDTO.getAmount(),initializer.getAuthCapital().getAmount());
        Assertions.assertEquals(authorizationDTO.getInvestorid(),initializer.getInvestor().getId());
    }

    @Test
    void create() {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(LocalDateTime.now(),LocalDateTime.now().plusDays(30),initializer.getInvestor().getId()
                ,initializer.getBroker().getId(),"AuthStock",200.0,2);

        given()
                .contentType(ContentType.JSON)
                .body(authorizationDTO)
                .when()
                .post("/authorizations")
                .then()
                .statusCode(200);

        Integer userid = initializer.getInvestor().getId();

        List list = given()
                .contentType(ContentType.JSON)
                .body(userid)
                .when()
                .get("/authorizations")
                .then()
                .statusCode(200)
                .extract().
                as(List.class);

        Assertions.assertEquals(list.size(),3);
    }
}