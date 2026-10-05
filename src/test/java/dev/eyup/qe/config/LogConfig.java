package dev.eyup.qe.config;

import io.restassured.config.RestAssuredConfig;

import static io.restassured.config.LogConfig.logConfig;

public class LogConfig {

    public static RestAssuredConfig configPetApi(){
        return RestAssuredConfig.config().logConfig(logConfig()
                .blacklistDefaultSensitiveHeaders()
                .enableLoggingOfRequestAndResponseIfValidationFails()
        );
    }
}
