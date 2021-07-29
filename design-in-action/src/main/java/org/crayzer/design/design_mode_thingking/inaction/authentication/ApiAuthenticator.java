package org.crayzer.design.design_mode_thingking.inaction.authentication;

public interface ApiAuthenticator {
    void auth(String url);

    void auth(ApiRequest apiRequest);
}
