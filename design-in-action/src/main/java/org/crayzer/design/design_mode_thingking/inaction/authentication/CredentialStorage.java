package org.crayzer.design.design_mode_thingking.inaction.authentication;

public interface CredentialStorage {
    String getPasswordByAppId(String appId);
}
