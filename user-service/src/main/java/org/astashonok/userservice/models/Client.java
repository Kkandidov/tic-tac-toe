package org.astashonok.userservice.models;

import lombok.Getter;
import lombok.Setter;
import org.astashonok.userservice.constants.AuthenticationMethod;
import org.astashonok.userservice.constants.GrantType;
import org.astashonok.userservice.constants.TimeUnitType;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class Client {

    private String id = UUID.randomUUID().toString();
    private String clientId;
    private String clientSecret;
    private String clientName;
    private Set<AuthenticationMethod> authenticationMethods;
    private Set<GrantType> grantTypes;
    private Set<String> redirectUris;
    private Set<String> scopes;
    private Long accessTokenDuration;
    private TimeUnitType accessTimeUnitType;
    private Long refreshTokenDuration;
    private TimeUnitType refreshTimeUnitType;
}
