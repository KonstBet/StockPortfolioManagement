package org.acme.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.acme.domain.OrderType;
import org.acme.domain.Stock;
import org.acme.repositories.Initializer;
import org.acme.repositories.StockRepository;
import org.acme.services.AuthorizationService;
import org.acme.services.WalletService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
class OrderResourceTest {

    @Inject
    Initializer initializer;

    @Inject
    StockRepository stockRepository;

    @InjectMock
    @RestClient
    WalletService walletService;

    @InjectMock
    @RestClient
    AuthorizationService authorizationService;

    Long validBrokerId = 30L;
    Long notValidBrokerId = 45L;
    @BeforeEach
    void initialize(){
        initializer.eraseData();
        initializer.seedData();


        WalletDTO walletDTO1 = new WalletDTO(initializer.userId1, 500.0);
        WalletDTO walletDTO2 = new WalletDTO(initializer.userId2, 300.0);
        WalletDTO walletDTO3 = new WalletDTO(validBrokerId, 0.0);

        Mockito.when(walletService.getUserWallet(initializer.userId1)).
                thenReturn(javax.ws.rs.core.Response.ok(walletDTO1).build());

        Mockito.when(walletService.getUserWallet(initializer.userId2)).
                thenReturn(javax.ws.rs.core.Response.ok(walletDTO2).build());

        Mockito.when(walletService.getUserWallet(validBrokerId)).
                thenReturn(javax.ws.rs.core.Response.ok(walletDTO3).build());

        Mockito.when(authorizationService.verifyLink(initializer.userId1, validBrokerId))
                .thenReturn(javax.ws.rs.core.Response.ok().build());

        Mockito.when(authorizationService.verifyLink(initializer.userId1, notValidBrokerId))
                .thenReturn(javax.ws.rs.core.Response.status(403).build());


        // mask all wallet updates as succesfull
        Mockito.when(walletService.update(Mockito.any()))
                .thenReturn(javax.ws.rs.core.Response.ok().build());

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

    @Test
    void fetchOrderById(){
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("user_id", initializer.userId2)
                .when().get("/orders");

        List<OrderDTO> orderDTOList = Arrays.asList(response.getBody().as(OrderDTO[].class));

        OrderDTO orderDTO = orderDTOList.get(0);

        response = given().contentType(ContentType.JSON)
                .pathParam("order_id", orderDTO.getId())
                .when().get("/orders/{order_id}");

        OrderDTO databaseOrder = response.as(OrderDTO.class);

        Assertions.assertEquals(databaseOrder.getId(), orderDTO.getId());
        Assertions.assertEquals(databaseOrder.getStockAmount(), orderDTO.getStockAmount());
        Assertions.assertEquals(databaseOrder.getInvestorId(), orderDTO.getInvestorId());

    }

    @Test
    void searchInvalidOrderId(){
        Response response = given().contentType(ContentType.JSON)
                .pathParam("order_id", Long.MAX_VALUE)
                .when().get("/orders/{order_id}");


        Assertions.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    void searchOrdersOfInvalidUserId(){
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("user_id", Long.MAX_VALUE)
                .when().get("/orders");

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals(response.getBody().as(List.class).size(),0);
    }

    @Test
    void successfullOrderCreation(){
        OrderDTO orderDTO = prepareOrderDTO(initializer.userId1, null,1, OrderType.PURCHASE);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(orderDTO)
                .when()
                .post("/orders");

        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    void failedOrderCreationNotEnoughWalletBalance(){


        OrderDTO orderDTO = prepareOrderDTO(initializer.userId1, null,10, OrderType.PURCHASE);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(orderDTO)
                .when()
                .post("/orders");

        Assertions.assertEquals(400, response.getStatusCode());
    }

    @Test
    void successOrderCreationWithValidBrokerId(){


        OrderDTO orderDTO = prepareOrderDTO(initializer.userId1, validBrokerId,1, OrderType.PURCHASE);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(orderDTO)
                .when()
                .post("/orders");

        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    void failedOrderCreationBecauseOfNoValidBrokerId(){


        OrderDTO orderDTO = prepareOrderDTO(initializer.userId1, notValidBrokerId,1, OrderType.PURCHASE);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(orderDTO)
                .when()
                .post("/orders");

        Assertions.assertEquals(500, response.getStatusCode());
    }

    @Test
    void validBrokerIdInvalidWalletAmount(){


        OrderDTO orderDTO = prepareOrderDTO(initializer.userId1, validBrokerId,10, OrderType.PURCHASE);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(orderDTO)
                .when()
                .post("/orders");

        Assertions.assertEquals(400, response.getStatusCode());
    }

    @Test
    void sellStockNormally(){
        OrderDTO orderDTO = prepareOrderDTO(initializer.userId1, null,10, OrderType.SALE);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(orderDTO)
                .when()
                .post("/orders");

        Assertions.assertEquals(200, response.getStatusCode());
    }
    @Test
    void failToSellBecauseNotEnoughAmountAvailable(){
        OrderDTO orderDTO = prepareOrderDTO(initializer.userId1, null,16, OrderType.SALE);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(orderDTO)
                .when()
                .post("/orders");

        Assertions.assertEquals(400, response.getStatusCode());
    }
    @Test
    void sellStockNormallyWithBroker(){
        OrderDTO orderDTO = prepareOrderDTO(initializer.userId1, validBrokerId,10, OrderType.SALE);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(orderDTO)
                .when()
                .post("/orders");

        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    @Transactional
    void buyStockWithAStockThatWeDoNotOwn(){
        Stock stock = stockRepository.findByCompanyName("Meta"); // find it and get its id on db

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setInvestorId(initializer.userId2);
        orderDTO.setBrokerId(null);
        orderDTO.setType(OrderType.PURCHASE);
        orderDTO.setStockId(stock.getId());
        orderDTO.setStockAmount(1);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(orderDTO)
                .when()
                .post("/orders");

        Assertions.assertEquals(200, response.getStatusCode());
    }


    // Helper to prepare an order DTO for testing.
    OrderDTO prepareOrderDTO(Long investorId, Long brokerId, Integer quantity, OrderType type) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setInvestorId(investorId);
        orderDTO.setBrokerId(brokerId);
        orderDTO.setType(type);
        Stock stock = stockRepository.findByCompanyName("JPMorgan Chase & Co");
        orderDTO.setStockId(stock.getId());
        orderDTO.setStockAmount(quantity);

        return orderDTO;
    }



}
