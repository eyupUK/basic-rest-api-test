package dev.eyup.qe.uath;

import dev.eyup.qe.support.ConfigurationReader;

public class TokenManager implements TokenProvider {

    private Domain domain;

    public TokenManager(Domain domain){
        this.domain = domain;
    }

    @Override
    public String getAccessToken() {
        return  System.getenv(domain + "_TOKEN") != null ? System.getenv(domain + "_TOKEN") : (System.getProperty(domain + "_TOKEN") != null ? System.getProperty(domain + "_TOKEN") : ConfigurationReader.getProperty(domain + "_TOKEN"));
    }
}
