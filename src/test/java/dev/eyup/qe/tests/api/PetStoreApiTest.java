package dev.eyup.qe.tests.api;

import dev.eyup.qe.client.PetStoreApiClient;
import dev.eyup.qe.model.request.PetApiCreatePetModel;
import dev.eyup.qe.model.response.PetApiErrorModel;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;

import static dev.eyup.qe.assertions.PetStoreApiErrorAssertion.assertBadRequest;
import static dev.eyup.qe.config.PetStoreApiRequestSpec.*;
import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PetStoreApiTest {

    String baseUri = "https://petstore3.swagger.io/api";


    @Test
    void shouldReturnSuccessfulWhenPostingValidBody(){

        String payLoad = """
                {
                  "id": 10,
                  "name": "doggie",
                  "category": {
                    "id": 1,
                    "name": "Dogs"
                  },
                  "photoUrls": [
                    "string"
                  ],
                  "tags": [
                    {
                      "id": 0,
                      "name": "string"
                    }
                  ],
                  "status": "available"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(baseUri)
                .config(config()
                        .logConfig(LogConfig.logConfig()
                                .enableLoggingOfRequestAndResponseIfValidationFails()
                                .blacklistDefaultSensitiveHeaders()
                        ))
                .body(payLoad)
                .when()
                .post("/v3/pet")
                .then().log().ifValidationFails()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(10))
                .body("name", equalTo("doggie"))
                .body("category.id",equalTo(1))
                .body("category.name",equalTo("Dogs"))
                .body("photoUrls", not(empty()))
                .body("tags[0].id", instanceOf(Integer.class))
                .body("tags[0].name", is("string"))
                .body("status", is("available"));
    }

    @Test
    void shouldReturnSuccessfulWhenPostingValidBodyPOJO(){
        // POJO serialization
        PetApiCreatePetModel payLoad = new PetApiCreatePetModel(
                996,
                "Puffy",
                Map.of("id", 1, "name", "Dogs"),
                new String[]{"https://image.com/puffy"},
                List.of(Map.of("id", 0, "name", "string")),
                "available"
        );

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .config(config()
                        .logConfig(LogConfig.logConfig()
                                .enableLoggingOfRequestAndResponseIfValidationFails()
                                .blacklistDefaultSensitiveHeaders()
                        ))
                .baseUri(baseUri)
                .body(payLoad)
                .when()
                .post("/v3/pet")
                .then().log().ifValidationFails()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(996))
                .body("name", equalTo("Puffy"))
                .body("category.id",equalTo(1))
                .body("category.name",equalTo("Dogs"))
                .body("photoUrls", not(empty()))
                .body("tags[0].id", instanceOf(Integer.class))
                .body("tags[0].name", is("string"))
                .body("status", is("available"));
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "abc" })
    void shouldReturnBadRequestWhenPostingInvalidPayload(String id){

        String payLoad = """
                {
                  "id": %s,
                  "name": "doggie",
                  "category": {
                    "id": 1,
                    "name": "Dogs"
                  },
                  "photoUrls": [
                    "string"
                  ],
                  "tags": [
                    {
                      "id": 0,
                      "name": "string"
                    }
                  ],
                  "status": "available"
                }
                """.formatted(id);

        Response response = new PetStoreApiClient(baseSpec()).createNewPet();

        assertBadRequest(response);

        response.then()
                .body("message",containsString("Input error:"));
    }

    @Test
    void shouldReturnBadRequestWhenPostingMalformedPayload(){

        String payLoad = """
                {
                  "id": 10,
                  "name": "doggie",
                  "category": {
                    "id": 1,
                    "name": "Dogs"
                  },
                  "photoUrls": [
                    "string"
                  ],
                  "tags": [
                    {
                      "id": 0,
                      "name": "string"
                    }
                  ],
                  "status": "available"
                
                """;

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(baseUri)
                .config(config()
                        .logConfig(LogConfig.logConfig()
                                .enableLoggingOfRequestAndResponseIfValidationFails()
                                .blacklistDefaultSensitiveHeaders()
                        ))
                .body(payLoad);

        Response response =
                request
                        .when()
                        .post("/v3/pet");
        // POJO deserialization
        PetApiErrorModel errorModel = response.as(PetApiErrorModel.class);
        assertEquals(400, errorModel.code(), "Status code should be 400");
        assertTrue(errorModel.message().contains("Input error"), "Error message should contain Input error");

        assertBadRequest(response);

        response.then()
                .body("message", containsString("Input error"));
    }
}
