package dev.eyup.qe.client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PetStoreApiClient {
    private RequestSpecification requestSpecification;

    public PetStoreApiClient(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public Response createNewPet(){
        return given().spec(requestSpecification).when().post("/pet");
    }
}
