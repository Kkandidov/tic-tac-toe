package org.astashonok.userservice.converters.client.impl;

import org.astashonok.userservice.constants.ClientTypeNames;
import org.astashonok.userservice.converters.client.ClientConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

@Component(ClientCredentialsClientConverter.NAME)
@Qualifier(ClientConverter.TYPED_CLIENT_CONVERTER)
public class ClientCredentialsClientConverter extends AbstractClientConverter {

    public static final String NAME = ClientTypeNames.CLIENT_CREDENTIALS + ClientConverter.CLIENT_CONVERTER_NAME_POSTFIX;

    @Override
    protected RegisteredClient buildRegisteredClient(RegisteredClient.Builder registeredClientBuilder) {
        return registeredClientBuilder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .build();
    }
}
