package org.astashonok.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.astashonok.userservice.converters.client.ClientConverter;
import org.astashonok.userservice.models.Client;
import org.astashonok.userservice.properties.Clients;
import org.astashonok.userservice.services.RegisteredClientService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisteredClientServiceImpl implements RegisteredClientService {

    private final Clients clients;
    private final ClientConverter clientConverter;

    @Override
    public List<RegisteredClient> getRegisteredClient() {
        List<Client> allClients = clients.getAllClients();

        return CollectionUtils.isEmpty(allClients)
                ? Collections.emptyList()
                : clientConverter.convert(allClients);
    }
}
