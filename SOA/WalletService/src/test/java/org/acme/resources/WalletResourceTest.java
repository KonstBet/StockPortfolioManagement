package org.acme.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.repositories.Initializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class WalletResourceTest {

    @Inject
    Initializer initializer;

    @BeforeEach
    void initialize() {
        initializer.EraseData();
        initializer.Initialize();
    }

    @Test
    void get() {

        given()
                .pathParam("id",initializer.getDeposit().getWallet().getUserid())
                .when()
                .get("/balance/{id}")
                .then()
                .statusCode(200)
                .body("balance",is(1000.0F)); //RETURNS FLOAT
    }

    @Test
    void update() {
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setUserid(initializer.getWallet().getUserid());
        walletDTO.setBalance(50000.0);


        given()
                .contentType(ContentType.JSON)
                .body(walletDTO)
                .when()
                .put("/balance")
                .then()
                .statusCode(200);
    }
}