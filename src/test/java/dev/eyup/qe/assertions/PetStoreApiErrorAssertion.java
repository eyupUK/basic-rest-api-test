package dev.eyup.qe.assertions;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

public class PetStoreApiErrorAssertion {


    public static void assertBadRequest(Response response){

        response
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("$", not(empty()))
                .body("code", equalTo(400));
    }
}
