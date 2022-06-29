package org.astashonok.userservice.converters.client.impl;

import lombok.NonNull;
import org.astashonok.common.utils.CommonUtils;
import org.astashonok.userservice.constants.AuthenticationMethod;
import org.astashonok.userservice.constants.GrantType;
import org.astashonok.userservice.converters.client.ClientConverter;
import org.astashonok.userservice.models.Client;
import org.astashonok.userservice.utils.TokenSettingsUtils;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ClientConverterImpl implements ClientConverter {

    @Override
    public RegisteredClient convert(@NonNull Client client) {
        Set<ClientAuthenticationMethod> clientAuthenticationMethods =
                CommonUtils.mapSetOrEmpty(client.getAuthenticationMethods(), AuthenticationMethod::getMethod);
        Set<AuthorizationGrantType> authorizationGrantTypes =
                CommonUtils.mapSetOrEmpty(client.getGrantTypes(), GrantType::getType);

        return RegisteredClient.withId(client.getId())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientName(client.getClientName())
                .clientAuthenticationMethods(methods -> methods.addAll(clientAuthenticationMethods))
                .authorizationGrantTypes(authTypes -> authTypes.addAll(authorizationGrantTypes))
                .redirectUris(uris -> CommonUtils.addAllIfNotEmpty(uris, client.getRedirectUris()))
                .scope(OidcScopes.OPENID)
                .scopes(scopes -> CommonUtils.addAllIfNotEmpty(scopes, client.getScopes()))
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .tokenSettings(TokenSettingsUtils.fromClient(client))
                .build();
    }

    @Override
    public List<RegisteredClient> convert(@NonNull List<Client> clients) {
        return CommonUtils.mapList(clients, this::convert);
    }
}
