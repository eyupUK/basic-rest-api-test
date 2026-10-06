package dev.eyup.qe.config;

import dev.eyup.qe.filter.CorrelationIdFilter;
import dev.eyup.qe.uath.Domain;
import dev.eyup.qe.uath.TokenManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class PetStoreApiRequestSpec {

    private static final String token = new TokenManager(Domain.PETSTORE).getAccessToken();
    private static final String baseUri = EnvConfig.getBaseUriPetStore();

    public static RequestSpecification baseSpec(){
        return  new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setConfig(LogConfig.configPetApi())
                .addFilter(new CorrelationIdFilter())
                .setBasePath("/v3")
                .setBaseUri(baseUri)
                .build();
    }
    public static RequestSpecification specWithAuth(){
        return baseSpec()
                .auth().oauth2(token);
    }
}
