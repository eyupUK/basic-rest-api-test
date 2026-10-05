package dev.eyup.qe.config;

import dev.eyup.qe.support.ConfigurationReader;

public class AuthConfig {

    public static String getTokenPetStore(){
        return System.getenv("PETSTORE_TOKEN") != null ? System.getenv("TOKEN") : (System.getProperty("PETSTORE_TOKEN") != null ? System.getProperty("PETSTORE_TOKEN") : ConfigurationReader.getProperty("PETSTORE_TOKEN"));
    }

    public static String getBaseUriPetStore(){
        return System.getenv("PETSTORE_BASE_URI") != null ? System.getenv("PETSTORE_BASE_URI") : (System.getProperty("PETSTORE_BASE_URI") != null ? System.getProperty("PETSTORE_BASE_URI") : ConfigurationReader.getProperty("PETSTORE_BASE_URI"));
    }

}
