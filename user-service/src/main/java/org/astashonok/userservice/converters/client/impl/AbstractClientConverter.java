package org.astashonok.userservice.converters.client.impl;

import lombok.NonNull;
import org.astashonok.common.utils.CommonUtils;
import org.astashonok.userservice.converters.client.ClientConverter;
import org.astashonok.userservice.models.Client;
import org.astashonok.userservice.properties.CustomTokensSettings;
import org.astashonok.userservice.utils.TokenSettingsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.List;

public abstract class AbstractClientConverter implements ClientConverter {


    private CustomTokensSettings customTokensSettings;

    @Autowired
    public void setCustomTokensSettings(CustomTokensSettings customTokensSettings) {
        this.customTokensSettings = customTokensSettings;
    }

    @Override
    public RegisteredClient convert(@NonNull Client client) {
        return buildRegisteredClient(
                getRegisteredClientBuilder(client)
        );
    }

    @Override
    public List<RegisteredClient> convert(@NonNull List<Client> clients) {
        return CommonUtils.mapList(clients, this::convert);
    }

    protected abstract RegisteredClient buildRegisteredClient(RegisteredClient.Builder registeredClientBuilder);

    private RegisteredClient.Builder getRegisteredClientBuilder(Client client) {
        return RegisteredClient.withId(client.getId().toString())
                .clientId(client.getClientId())
                .clientName(client.getName())
                .clientSecret(client.getSecret())
                .scopes(scopes -> CommonUtils.addAllIfNotEmpty(scopes, client.getScope()))
                .redirectUris(uris -> CommonUtils.addAllIfNotEmpty(uris, client.getRedirectUri()))
                .tokenSettings(TokenSettingsUtils.fromCustomTokensSettings(customTokensSettings));
    }
}
