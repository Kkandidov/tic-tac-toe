package org.astashonok.userservice.converters.client.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.astashonok.common.utils.CommonUtils;
import org.astashonok.userservice.models.Client;
import org.astashonok.userservice.converters.client.ClientConverter;
import org.astashonok.userservice.providers.ClientConverterProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(ClientConverterImpl.NAME)
@Primary
@RequiredArgsConstructor
public class ClientConverterImpl implements ClientConverter {

    public static final String NAME = "clientConverterImpl";

    private final ClientConverterProvider clientConverterProvider;

    @Override
    public RegisteredClient convert(@NonNull Client client) {
        return clientConverterProvider.getByClientType(client.getClientType()).convert(client);
    }

    @Override
    public List<RegisteredClient> convert(@NonNull List<Client> clients) {
        return CommonUtils.mapList(clients, this::convert);
    }
}
