package org.astashonok.oauthclientstarter.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@RequiredArgsConstructor
@Getter
public enum GrantType {

    AUTHORIZATION_CODE(AuthorizationGrantType.AUTHORIZATION_CODE),
    IMPLICIT(AuthorizationGrantType.IMPLICIT),
    REFRESH_TOKEN(AuthorizationGrantType.REFRESH_TOKEN),
    CLIENT_CREDENTIALS(AuthorizationGrantType.CLIENT_CREDENTIALS),
    PASSWORD(AuthorizationGrantType.PASSWORD),
    JWT_BEARER(AuthorizationGrantType.JWT_BEARER);

    private final AuthorizationGrantType type;
}
