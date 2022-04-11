package org.acme.resources;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.acme.repositories.Initializer;
import org.acme.repositories.StockHoldingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
class StockHoldingResourceTest {

    @Inject
    Initializer initializer;

    @Inject
    StockHoldingRepository stockHoldingRepository;

    @BeforeEach
    void initialize(){
        initializer.eraseData();
        initializer.seedData();
    }

    @Test
    void listUserStockHoldings(){


        Response response = given().contentType(ContentType.JSON)
                .queryParam("user_id", initializer.userId1)
                .when().get("/stockholdings");

        Response response2 = given().contentType(ContentType.JSON)
                .queryParam("user_id", initializer.userId2)
                .when().get("/stockholdings");

        List<StockHoldingDTO> stockHoldingDTOList = Arrays.asList(response.getBody().as(StockHoldingDTO[].class));

        List<StockHoldingDTO> stockHoldingDTOList2 = Arrays.asList(response2.getBody().as(StockHoldingDTO[].class));

        Assertions.assertEquals(stockHoldingDTOList.size(), 2);
        Assertions.assertEquals(stockHoldingDTOList2.size(), 1);

    }

    @Test
    void listUserWithoutStockHoldings(){
        Response response = given().contentType(ContentType.JSON)
                .queryParam("user_id", Long.MAX_VALUE)
                .when().get("/stockholdings");
        List<StockHoldingDTO> stockHoldingDTOList = Arrays.asList(response.getBody().as(StockHoldingDTO[].class));
        Assertions.assertEquals(stockHoldingDTOList.size(), 0);

    }

    @Test
    void getStockHoldingById(){

        // first we fetch all the users stockholdings
        Response response = given().contentType(ContentType.JSON)
                .queryParam("user_id", initializer.userId1)
                .when().get("/stockholdings");

        // convert to dto list
        List<StockHoldingDTO> stockHoldingDTOList = Arrays.asList(response.getBody().as(StockHoldingDTO[].class));

        // fetch the first stock by its id
        response = given().contentType(ContentType.JSON)
                .pathParam("stockholding_id",stockHoldingDTOList.get(0).getId()).when().get("/stockholdings/{stockholding_id}");

        // convert to stockDTO
        StockHoldingDTO stockHoldingDTO = response.then().statusCode(200).extract().as(StockHoldingDTO.class);


        // verify that we received the same stock as the inital
        Assertions.assertEquals(stockHoldingDTOList.get(0).getId(), stockHoldingDTO.getId());
        Assertions.assertEquals(stockHoldingDTOList.get(0).getStockId(), stockHoldingDTO.getStockId());
    }

    @Test
    void getInvalidStockId(){
        Long id = Long.MAX_VALUE;
        Response response = given().pathParam("stockholding_id",id).get("/stockholdings/{stockholding_id}");
        Assertions.assertEquals(response.getStatusCode(), 404);
    }

}
