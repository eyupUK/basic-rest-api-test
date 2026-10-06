package dev.eyup.qe.config;

import dev.eyup.qe.support.ConfigurationReader;

public class EnvConfig {

    public static String getBaseUriPetStore(){
        return System.getenv("PETSTORE_BASE_URI") != null ? System.getenv("PETSTORE_BASE_URI") : (System.getProperty("PETSTORE_BASE_URI") != null ? System.getProperty("PETSTORE_BASE_URI") : ConfigurationReader.getProperty("PETSTORE_BASE_URI"));
    }

}
