package org.astashonok.userservice.services;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.List;

public interface RegisteredClientService {

    List<RegisteredClient> getRegisteredClient();
}
