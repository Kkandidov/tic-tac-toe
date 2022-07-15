package org.astashonok.userservice.models.oauth2;

import com.sun.istack.Nullable;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class CustomOAuth2PasswordAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private final Set<String> scopes;

    public CustomOAuth2PasswordAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                                   Authentication clientPrincipal,
                                                   @Nullable Set<String> scopes,
                                                   @Nullable Map<String, Object> additionalParameters) {

        super(authorizationGrantType, clientPrincipal, additionalParameters);
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
    }
}
