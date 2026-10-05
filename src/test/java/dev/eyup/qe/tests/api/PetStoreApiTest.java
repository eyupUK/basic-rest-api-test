package dev.eyup.qe.tests.api;

import dev.eyup.qe.client.PetStoreApiClient;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static dev.eyup.qe.assertions.PetStoreApiErrorAssertion.assertBadRequest;
import static dev.eyup.qe.config.PetStoreApiRequestSpec.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
//                .spec()
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

        Response response = new PetStoreApiClient(specWithAuth()).createNewPet();

        assertBadRequest(response);

        response.then()
                .body("message",equalTo("Input error: unable to convert input to io.swagger.petstore.model.Pet"));
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
                .body(payLoad);

        Response response =
                request
                        .when()
                        .post("/v3/pet");

        assertBadRequest(response);

        response.then()
                .body("message", containsString("Input error"));
    }
}
