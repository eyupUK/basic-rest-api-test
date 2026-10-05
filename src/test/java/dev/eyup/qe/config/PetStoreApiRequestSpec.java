package dev.eyup.qe.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class PetStoreApiRequestSpec {

    private static final String token = AuthConfig.getTokenPetStore();
    private static final String baseUri = AuthConfig.getBaseUriPetStore();

    public static RequestSpecification baseSpec(){
        return  new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setConfig(LogConfig.configPetApi())
//                .addHeaders()
//                .addCookie()
                .setBasePath("/v3")
//                .addParams()
//                .addQueryParams()
                .setBaseUri(baseUri)
                .build();
    }
    public static RequestSpecification specWithAuth(){
        return baseSpec()
                .auth().oauth2(token);
    }
}
