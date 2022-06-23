package org.astashonok.userservice.providers.impl;

import lombok.NonNull;
import org.astashonok.userservice.constants.ClientType;
import org.astashonok.userservice.constants.ErrorMessages;
import org.astashonok.userservice.converters.client.ClientConverter;
import org.astashonok.userservice.providers.ClientConverterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

@Component
public class ClientConverterProviderImpl implements ClientConverterProvider {

    private final Map<String, ClientConverter> nameToClientConverter;

    @Autowired
    public ClientConverterProviderImpl(@Qualifier(ClientConverter.TYPED_CLIENT_CONVERTER)
                                       Map<String, ClientConverter> nameToClientConverter) {

        this.nameToClientConverter = nameToClientConverter;
    }

    @Override
    public ClientConverter getByClientType(@NonNull ClientType clientType) {
        String converterName = clientType.getName() + ClientConverter.CLIENT_CONVERTER_NAME_POSTFIX;
        ClientConverter clientConverter = nameToClientConverter.get(converterName);
        Assert.notNull(clientConverter, String.format(ErrorMessages.CLIENT_CONVERTER_NOT_FOUND, clientType.getName()));

        return clientConverter;
    }
}
