package org.acme.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.acme.repositories.Initializer;
import org.acme.services.WalletService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class AuthorizationResourceTest {

    @Inject
    Initializer initializer;

    @InjectMock
    @RestClient
    WalletService walletService;

    @BeforeEach
    void initialize() {
        initializer.EraseData();
        initializer.Initialize();


        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setUserId(initializer.getInvestor().getId());
        walletDTO.setBalance(1000.0);

        Mockito.when(walletService.get(initializer.getInvestor().getId())).thenReturn(javax.ws.rs.core.Response.ok(walletDTO).build());

        walletDTO.setBalance(800.0);
        Mockito.when(walletService.update(walletDTO)).thenReturn(javax.ws.rs.core.Response.ok().build());

    }

    @Test
    void list() {

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
//    @Test
//    void get() {
//
//        AuthorizationDTO authorizationDTO =
//                given()
//                        .pathParam("id",initializer.getAuthCapital().getId())
//                        .when()
//                        .get("/authorizations/{id}")
//                        .then()
//                        .statusCode(200)
//                        .extract().as(AuthorizationDTO.class);
//
//        Assertions.assertEquals(authorizationDTO.getType(),"AuthCapital");
//        Assertions.assertEquals(authorizationDTO.getAmount(),initializer.getAuthCapital().getAmount());
//        Assertions.assertEquals(authorizationDTO.getInvestorid(),initializer.getInvestor().getId());
//    }

//    @Test
//    void create() {
//        AuthorizationDTO authorizationDTO = new AuthorizationDTO(null, LocalDateTime.now(),LocalDateTime.now().plusDays(30),initializer.getInvestor().getId()
//                ,initializer.getBroker().getId(),"AuthStock",200.0,2);
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(authorizationDTO)
//                .when()
//                .post("/authorizations")
//                .then()
//                .statusCode(200);
//
//        Integer userid = initializer.getInvestor().getId();
//
//        List list = given()
//                .contentType(ContentType.JSON)
//                .queryParam("userid",initializer.getInvestor().getId())
//                .when()
//                .get("/authorizations")
//                .then()
//                .statusCode(200)
//                .extract().
//                as(List.class);
//
//        Assertions.assertEquals(list.size(),3);
//    }
}