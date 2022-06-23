package org.astashonok.userservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientTypeNames {
    public static final String AUTHORIZATION_CODE = "authorizationCode";
    public static final String CLIENT_CREDENTIALS = "clientCredentials";
    public static final String PASSWORD = "password";
}
