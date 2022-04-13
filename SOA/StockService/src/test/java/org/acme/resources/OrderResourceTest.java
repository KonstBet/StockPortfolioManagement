package org.acme.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.acme.domain.OrderType;
import org.acme.repositories.Initializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
class OrderResourceTest {

    @Inject
    Initializer initializer;

    @BeforeEach
    void initialize(){
        initializer.eraseData();
        initializer.seedData();
    }

    @Test
    void listUserOrders(){

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("user_id", initializer.userId1)
                .when().get("/orders");

        List<OrderDTO> orderDTOList = Arrays.asList(response.getBody().as(OrderDTO[].class));

        Assertions.assertEquals(orderDTOList.size(), 2);

        response = given()
                .contentType(ContentType.JSON)
                .queryParam("user_id", initializer.userId2)
                .when().get("/orders");

        orderDTOList = Arrays.asList(response.getBody().as(OrderDTO[].class));

        Assertions.assertEquals(orderDTOList.size(), 1);
    }

    @Test
    void listPurchaseOrders(){
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("user_id", initializer.userId1)
                .queryParam("type", OrderType.PURCHASE)
                .when().get("/orders");

        List<OrderDTO> orderDTOList = Arrays.asList(response.getBody().as(OrderDTO[].class));

        Assertions.assertEquals(orderDTOList.size(), 2);
    }

    @Test
    void listSaleOrders(){
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("user_id", initializer.userId1)
                .queryParam("type", OrderType.SALE)
                .when().get("/orders");

        List<OrderDTO> orderDTOList = Arrays.asList(response.getBody().as(OrderDTO[].class));

        Assertions.assertEquals(orderDTOList.size(), 0);
    }




}
