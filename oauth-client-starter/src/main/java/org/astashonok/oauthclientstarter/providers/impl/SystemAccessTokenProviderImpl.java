package org.astashonok.oauthclientstarter.providers.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.astashonok.oauthclientstarter.providers.SystemAccessTokenProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SystemAccessTokenProviderImpl implements SystemAccessTokenProvider {

    private final OAuth2AuthorizedClientManager manager;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Getter
    private OAuth2AccessToken systemAccessToken;

    @PostConstruct
    public void init() {
        OAuth2AuthorizeRequest systemAuthorizeRequest = getSystemAuthorizeRequest();
        OAuth2AuthorizedClient systemAuthorizedClient = manager.authorize(systemAuthorizeRequest);
        Assert.notNull(systemAuthorizedClient, "System authorized client must not be null");
        this.systemAccessToken = systemAuthorizedClient.getAccessToken();
        Assert.notNull(systemAccessToken, "System access token must not be null");
    }

    private OAuth2AuthorizeRequest getSystemAuthorizeRequest() {
        return Optional.ofNullable("system-client")
                .map(clientRegistrationRepository::findByRegistrationId)
                .map(this::buildSystemAuthorizeRequest)
                .orElseThrow(() -> new IllegalStateException("system-client not found"));
    }

    private OAuth2AuthorizeRequest buildSystemAuthorizeRequest(ClientRegistration systemClientRegistration) {
        return OAuth2AuthorizeRequest
                .withClientRegistrationId(systemClientRegistration.getRegistrationId())
                .principal(systemClientRegistration.getClientName())
                .build();
    }
}
