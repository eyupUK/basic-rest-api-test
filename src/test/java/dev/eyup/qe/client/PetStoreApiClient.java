package dev.eyup.qe.client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PetStoreApiClient {
    private RequestSpecification requestSpecification;

    public PetStoreApiClient(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public Response createNewPet(){
        return requestSpecification.when().post("/v3/pet");
    }
}
