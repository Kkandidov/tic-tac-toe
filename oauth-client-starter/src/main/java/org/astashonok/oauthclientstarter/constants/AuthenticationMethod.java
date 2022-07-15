package org.astashonok.oauthclientstarter.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@RequiredArgsConstructor
@Getter
public enum AuthenticationMethod {

    BASIC(ClientAuthenticationMethod.BASIC),
    CLIENT_SECRET_BASIC(ClientAuthenticationMethod.CLIENT_SECRET_BASIC),
    POST(ClientAuthenticationMethod.POST),
    CLIENT_SECRET_POST(ClientAuthenticationMethod.CLIENT_SECRET_POST),
    CLIENT_SECRET_JWT(ClientAuthenticationMethod.CLIENT_SECRET_JWT),
    PRIVATE_KEY_JWT(ClientAuthenticationMethod.PRIVATE_KEY_JWT),
    NONE(ClientAuthenticationMethod.NONE);

    private final ClientAuthenticationMethod method;
}
