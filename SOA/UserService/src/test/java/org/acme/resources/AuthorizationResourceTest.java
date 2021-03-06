package org.acme.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.acme.repositories.Initializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.List;

import static io.restassured.RestAssured.given;

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
    void listInvestors() {

        List list = given()
                .contentType(ContentType.JSON)
                .queryParam("user_id",initializer.getInvestor().getId())
                .queryParam("type", "investor")
                .when()
                .get("/authorizations")
                .then()
                .statusCode(200)
                .extract().
                as(List.class);

        Assertions.assertEquals(list.size(),1);
    }

    @Test
    void listBrokers() {

        List list = given()
                .contentType(ContentType.JSON)
                .queryParam("user_id",initializer.getBroker().getId())
                .queryParam("type", "broker")
                .when()
                .get("/authorizations")
                .then()
                .statusCode(200)
                .extract().
                as(List.class);

        Assertions.assertEquals(list.size(),1);
    }

    @Test
    void verifyValidUsersPairing(){

        Response response = given().contentType(ContentType.JSON)
                .queryParam("broker_id", initializer.getBroker().getId())
                .queryParam("investor_id", initializer.getInvestor().getId())
                .when().get("/authorizations/link/verify");

        Assertions.assertEquals(200, response.getStatusCode());

    }
    @Test
    void invalidUserPairing(){
        Response response = given().contentType(ContentType.JSON)
                .queryParam("broker_id", initializer.getInvestor().getId())
                .queryParam("investor_id", initializer.getInvestor().getId())
                .when().get("/authorizations/link/verify");

        Assertions.assertEquals(403, response.getStatusCode());
    }

    @Test
    void findById() {

        AuthorizationDTO authorizationDTO =
                given()
                        .pathParam("id",initializer.getAuthorization().getId())
                        .when()
                        .get("/authorizations/{id}")
                        .then()
                        .statusCode(200)
                        .extract().as(AuthorizationDTO.class);

        Assertions.assertTrue(authorizationDTO.getActive());
        Assertions.assertEquals(authorizationDTO.getBrokerId(),initializer.getBroker().getId());
        Assertions.assertEquals(authorizationDTO.getInvestorId(),initializer.getInvestor().getId());
    }

    @Test
    void findByIdBadID() {

        given()
                .pathParam("id",99999)
                .when()
                .get("/authorizations/{id}")
                .then()
                .statusCode(404);

    }

    @Test
    void create() {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(null,initializer.getStartTime(),initializer.getEndTime()
            ,initializer.getInvestor().getId(),initializer.getBroker().getId(),true);

        given()
                .contentType(ContentType.JSON)
                .body(authorizationDTO)
                .when()
                .post("/authorizations")
                .then()
                .statusCode(200);

        Long userid = initializer.getInvestor().getId();

        List list = given()
                .contentType(ContentType.JSON)
                .queryParam("user_id",initializer.getInvestor().getId())
                .queryParam("type", "investor")
                .when()
                .get("/authorizations")
                .then()
                .statusCode(200)
                .extract().
                as(List.class);

        Assertions.assertEquals(list.size(),2);
    }

    @Test
    void createBadID() {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO(null, initializer.getStartTime(), initializer.getEndTime()
                , 99999L, initializer.getBroker().getId(), true);

        given()
                .contentType(ContentType.JSON)
                .body(authorizationDTO)
                .when()
                .post("/authorizations")
                .then()
                .statusCode(400);
    }
}