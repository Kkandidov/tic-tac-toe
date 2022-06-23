package org.astashonok.userservice.converters.client.impl;

import org.astashonok.userservice.constants.ClientTypeNames;
import org.astashonok.userservice.converters.client.ClientConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.stereotype.Component;

@Component(AuthorizationCodeClientConverter.NAME)
@Qualifier(ClientConverter.TYPED_CLIENT_CONVERTER)
public class AuthorizationCodeClientConverter extends AbstractClientConverter {

    public static final String NAME = ClientTypeNames.AUTHORIZATION_CODE + ClientConverter.CLIENT_CONVERTER_NAME_POSTFIX;

    @Override
    protected RegisteredClient buildRegisteredClient(RegisteredClient.Builder registeredClientBuilder) {
        return registeredClientBuilder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
    }
}
