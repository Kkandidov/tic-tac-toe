package org.astashonok.oauthclientstarter.converters.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.astashonok.common.utils.CommonUtils;
import org.astashonok.oauthclientstarter.constants.AuthenticationMethod;
import org.astashonok.oauthclientstarter.converters.RegistrationClientsConverter;
import org.astashonok.oauthclientstarter.model.Client;
import org.astashonok.oauthclientstarter.model.OAuth2Provider;
import org.astashonok.oauthclientstarter.properties.OAuth2Providers;
import org.astashonok.oauthclientstarter.properties.RegistrationClients;
import org.javatuples.Pair;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RegistrationClientsConverterImpl implements RegistrationClientsConverter {

    private final OAuth2Providers oAuth2Providers;

    @Override
    public List<ClientRegistration> convert(@NonNull RegistrationClients registrationClients) {
        List<Client> clients = CommonUtils.getOrSupplyDefault(registrationClients,
                RegistrationClients::getClients, Collections::emptyList);

        Map<String, OAuth2Provider> idToProvider = CommonUtils.getOrSupplyDefault(oAuth2Providers,
                OAuth2Providers::getProviders, Collections::emptyMap);

        return clients.stream()
                .map(client -> new Pair<>(client, getProvider(client, idToProvider)))
                .map(triplet -> buildClientRegistration(triplet.getValue0(), triplet.getValue1()))
                .collect(Collectors.toList());
    }

    private ClientRegistration buildClientRegistration(Client client, OAuth2Provider provider) {
        return ClientRegistration.withRegistrationId(client.getRegistrationId())
                .clientName(client.getClientName())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethod(CommonUtils.getOrDefault(client.getAuthenticationMethod(),
                        AuthenticationMethod::getMethod, ClientAuthenticationMethod.CLIENT_SECRET_BASIC))
                .authorizationGrantType(client.getGrantType().getType())
                .scope(client.getScopes())
                .redirectUri(client.getRedirectUri())
                .authorizationUri(CommonUtils.getOrDefault(provider, OAuth2Provider::getAuthorizationUri,
                        CommonUtils.applyOrNull(provider.getIssuerUri(), baseUri -> baseUri + "/oauth2/authorize")))
                .tokenUri(CommonUtils.getOrDefault(provider, OAuth2Provider::getTokenUri,
                        CommonUtils.applyOrNull(provider.getIssuerUri(), baseUri -> baseUri + "/oauth2/token")))
                .jwkSetUri(CommonUtils.getOrDefault(provider, OAuth2Provider::getTokenUri,
                        CommonUtils.applyOrNull(provider.getIssuerUri(), baseUri -> baseUri + "/oauth2/jwks")))
                .issuerUri(provider.getIssuerUri())
                .build();
    }

    private OAuth2Provider getProvider(Client client, Map<String, OAuth2Provider> idToProvider) {
        OAuth2Provider provider = idToProvider.get(client.getProvider());
        Assert.notNull(provider, "Provider for '" + client.getProvider() + "' not found");
        return provider;
    }
}
