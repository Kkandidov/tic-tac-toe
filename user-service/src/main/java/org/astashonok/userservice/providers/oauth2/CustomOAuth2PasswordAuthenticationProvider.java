package org.astashonok.userservice.providers.oauth2;

import org.apache.commons.collections4.CollectionUtils;
import org.astashonok.userservice.models.oauth2.CustomOAuth2PasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.ProviderContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.*;

public class CustomOAuth2PasswordAuthenticationProvider implements AuthenticationProvider {

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    private final AuthenticationManager authenticationManager;
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    public CustomOAuth2PasswordAuthenticationProvider(AuthenticationManager authenticationManager,
                                                      OAuth2AuthorizationService authorizationService,
                                                      OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {

        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");

        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var passwordAuthentication = (CustomOAuth2PasswordAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = getAuthenticatedClientOrThrow(passwordAuthentication);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

        assertsPasswordGrantType(registeredClient);

        Set<String> authorizedScopes = getAuthorizedScopes(passwordAuthentication, registeredClient);
        Authentication usernamePasswordAuthentication = getUsernamePasswordAuthentication(passwordAuthentication);
        DefaultOAuth2TokenContext.Builder tokenContextBuilder =
                getTokenContextBuilder(passwordAuthentication, registeredClient, authorizedScopes,
                        usernamePasswordAuthentication);

        DefaultOAuth2TokenContext tokenAccessContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = tokenGenerator.generate(tokenAccessContext);
        OAuth2AccessToken accessToken = newOAuth2AccessToken(tokenAccessContext, generatedAccessToken);
        OAuth2RefreshToken refreshToken = getRefreshToken(registeredClient, clientPrincipal, tokenContextBuilder);
        OAuth2Authorization authorization = getOAuth2Authorization(registeredClient, usernamePasswordAuthentication,
                authorizedScopes, accessToken, generatedAccessToken);

        this.authorizationService.save(authorization);

        return new OAuth2AccessTokenAuthenticationToken(
                registeredClient,
                clientPrincipal,
                accessToken,
                refreshToken,
                Collections.emptyMap()
        );
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return CustomOAuth2PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private OAuth2Authorization getOAuth2Authorization(RegisteredClient registeredClient,
                                                       Authentication usernamePasswordAuthentication,
                                                       Set<String> authorizedScopes,
                                                       OAuth2AccessToken accessToken,
                                                       OAuth2Token generatedAccessToken) {

        OAuth2Authorization.Builder authorizationBuilder =
                getAuthorizationBuilder(registeredClient, usernamePasswordAuthentication, authorizedScopes);

        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder.token(
                    accessToken, (metadata) -> metadata.put(
                            OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
                            ((ClaimAccessor) generatedAccessToken).getClaims()
                    )
            );
        } else {
            authorizationBuilder.accessToken(accessToken);
        }

        return authorizationBuilder.build();
    }

    private OAuth2Authorization.Builder getAuthorizationBuilder(RegisteredClient registeredClient,
                                                                Authentication usernamePasswordAuthentication,
                                                                Set<String> authorizedScopes) {

        return OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(usernamePasswordAuthentication.getName())
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .attribute(OAuth2Authorization.AUTHORIZED_SCOPE_ATTRIBUTE_NAME, authorizedScopes)
                .attribute(Principal.class.getName(), usernamePasswordAuthentication);
    }

    private OAuth2RefreshToken getRefreshToken(RegisteredClient registeredClient,
                                               OAuth2ClientAuthenticationToken clientPrincipal,
                                               DefaultOAuth2TokenContext.Builder tokenContextBuilder) {

        return !registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN)
                || clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)
                ? null
                : Optional.of(tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build())
                .map(tokenGenerator::generate)
                .map(this::castToRefreshTokenOrThrow)
                .orElse(null);
    }

    private OAuth2RefreshToken castToRefreshTokenOrThrow(OAuth2Token generatedRefreshToken) {
        if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the refresh token.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }

        return (OAuth2RefreshToken) generatedRefreshToken;
    }

    private OAuth2AccessToken newOAuth2AccessToken(OAuth2TokenContext tokenContext, OAuth2Token generatedAccessToken) {
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }

        return new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(),
                generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(),
                tokenContext.getAuthorizedScopes()
        );
    }

    private DefaultOAuth2TokenContext.Builder getTokenContextBuilder(
            CustomOAuth2PasswordAuthenticationToken passwordAuthentication,
            RegisteredClient registeredClient, Set<String> authorizedScopes,
            Authentication usernamePasswordAuthentication) {

        return DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(usernamePasswordAuthentication)
                .providerContext(ProviderContextHolder.getProviderContext())
                .authorizedScopes(authorizedScopes)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrant(passwordAuthentication);
    }

    private Set<String> getAuthorizedScopes(CustomOAuth2PasswordAuthenticationToken passwordAuthentication,
                                            RegisteredClient registeredClient) {

        Set<String> requestedScopes = passwordAuthentication.getScopes();

        return CollectionUtils.isEmpty(requestedScopes)
                ? registeredClient.getScopes()
                : getRequestedScopesOrThrow(registeredClient.getScopes(), requestedScopes);
    }

    private Set<String> getRequestedScopesOrThrow(Set<String> registeredScopes, Set<String> requestedScopes) {
        if (CollectionUtils.isNotEmpty(CollectionUtils.removeAll(requestedScopes, registeredScopes))) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
        }

        return new LinkedHashSet<>(requestedScopes);
    }

    private void assertsPasswordGrantType(RegisteredClient registeredClient) {
        if (registeredClient == null
                || !registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.PASSWORD)) {

            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }
    }

    private Authentication getUsernamePasswordAuthentication(CustomOAuth2PasswordAuthenticationToken authentication) {
        Map<String, Object> additionalParameters = authentication.getAdditionalParameters();
        String username = (String) additionalParameters.get(OAuth2ParameterNames.USERNAME);
        String password = (String) additionalParameters.get(OAuth2ParameterNames.PASSWORD);
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    private OAuth2ClientAuthenticationToken getAuthenticatedClientOrThrow(Authentication authentication) {
        return Optional.of(authentication)
                .map(Authentication::getPrincipal)
                .filter(principal -> OAuth2ClientAuthenticationToken.class.isAssignableFrom(principal.getClass()))
                .map(OAuth2ClientAuthenticationToken.class::cast)
                .filter(OAuth2ClientAuthenticationToken::isAuthenticated)
                .orElseThrow(() -> new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT));
    }
}
