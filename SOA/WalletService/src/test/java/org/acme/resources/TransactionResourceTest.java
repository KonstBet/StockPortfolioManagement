package org.acme.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.repositories.Initializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TransactionResourceTest {

    @Inject
    Initializer initializer;

    @BeforeEach
    void initialize() {
        initializer.EraseData();
        initializer.Initialize();
    }

    @Test
    void list() {
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setUserid(initializer.getWallet().getUserId());

        List transactionDTOList =
                given()
                        .queryParam("user_id",initializer.getWallet().getUserId())
                        .when()
                        .get("/transaction")
                        .then()
                        .statusCode(200)
                        .extract().
                        as(List.class);

        Assertions.assertEquals(transactionDTOList.size(),2);
    }

    @Test
    void get() {
        TransactionDTO transactionDTO =
                given()
                        .pathParam("id",initializer.getDeposit().getId())
                        .when()
                        .get("/transaction/{id}")
                        .then()
                        .statusCode(200)
                        .extract().as(TransactionDTO.class);

        Assertions.assertEquals(transactionDTO.getUserId(),initializer.getDeposit().getWallet().getUserId());
        Assertions.assertEquals(transactionDTO.getAmount(),initializer.getDeposit().getAmount());
        Assertions.assertEquals(transactionDTO.getType(),"deposit");
    }

    @Test
    void create() {
        TransactionDTO transactionDTO = new TransactionDTO(1L,200.0,"withdraw", LocalDateTime.now());

        given()
                .contentType(ContentType.JSON)
                .body(transactionDTO)
                .when()
                .post("/transaction")
                .then()
                .statusCode(201);
    }

    @Test
    void createBroken() {
        TransactionDTO transactionDTO = new TransactionDTO(1L,200000.0,"withdraw", LocalDateTime.now());

        given()
                .contentType(ContentType.JSON)
                .body(transactionDTO)
                .when()
                .post("/transaction")
                .then()
                .statusCode(400);
    }
}