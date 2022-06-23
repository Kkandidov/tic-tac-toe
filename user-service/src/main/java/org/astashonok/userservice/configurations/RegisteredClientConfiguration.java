package org.astashonok.userservice.configurations;

import lombok.RequiredArgsConstructor;
import org.astashonok.userservice.converters.client.ClientsConverter;
import org.astashonok.userservice.properties.Clients;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RegisteredClientConfiguration {

    private final Clients clients;

    private final ClientsConverter clientsConverter;

    @PostConstruct
    public void init() {
        List<RegisteredClient> convert = clientsConverter.convert(clients);
        System.out.println("werwrer");
    }
}
