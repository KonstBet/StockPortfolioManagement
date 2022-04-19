package org.acme.resources;

    import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
    import io.restassured.response.Response;
    import org.acme.repositories.Initializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
    import javax.transaction.Transactional;
    import java.util.Arrays;
    import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
class StockResourceTest {

    @Inject
    Initializer initializer;

    @BeforeEach
    void initialize(){
        initializer.eraseData();
        initializer.seedData();
    }

    @Test
    void listStocks(){


        Response response = given().contentType(ContentType.JSON).when().get("/stocks");

        List<StockDTO> stockDTOList = Arrays.asList(response.getBody().as(StockDTO[].class));

        Assertions.assertEquals(stockDTOList.size(), 2);

    }

    @Test
    void getStockById(){

        // first we fetch all the stocks
        Response response = given().contentType(ContentType.JSON).when().get("/stocks");

        // convert to stockDTO list
        List<StockDTO> stockDTOList = Arrays.asList(response.getBody().as(StockDTO[].class));

        // fetch the first stock by its id
        response = given().contentType(ContentType.JSON)
                .pathParam("stock_id",stockDTOList.get(0).getId()).when().get("/stocks/{stock_id}");

        // convert to stockDTO
        StockDTO stockDTO = response.then().statusCode(200).extract().as(StockDTO.class);

        // verify that we received the same stock as the inital
        Assertions.assertEquals(stockDTOList.get(0).getId(), stockDTO.getId());
        Assertions.assertEquals(stockDTOList.get(0).getCompanyName(), stockDTO.getCompanyName());
    }

    @Test
    void getInvalidStockId(){
        Long id = Long.MAX_VALUE;
        Response response = given().pathParam("stock_id",id).get("/stocks/{stock_id}");
        Assertions.assertEquals(response.getStatusCode(), 404);


    }


    @Test
    @Transactional
    void createStock(){
        StockDTO stockDTO = new StockDTO(1L, "Lio Software", 10.0, 11.0, 15.0, 12.0, 1000.0);

       Response response = given().contentType(ContentType.JSON).body(stockDTO).when().post("/stocks");

       Assertions.assertEquals(200, response.getStatusCode());
    }

}
