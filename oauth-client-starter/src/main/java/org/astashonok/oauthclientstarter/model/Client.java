package org.astashonok.oauthclientstarter.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.astashonok.oauthclientstarter.constants.AuthenticationMethod;
import org.astashonok.oauthclientstarter.constants.GrantType;

import java.util.Collections;
import java.util.Set;

@Getter
@Setter
public class Client {

    @NonNull
    private String registrationId;

    @NonNull
    private String clientId;

    @NonNull
    private String clientSecret;
    private AuthenticationMethod authenticationMethod;

    @NonNull
    private GrantType grantType;
    private String redirectUri;
    private Set<String> scopes = Collections.emptySet();

    @NonNull
    private String provider;
    private String clientName;
}
