package org.astashonok.userservice.configurations;

import lombok.RequiredArgsConstructor;
import org.astashonok.userservice.converters.client.ClientConverter;
import org.astashonok.userservice.properties.Clients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RegisteredClientConfiguration {

    private final Clients clients;
    private final ClientConverter clientConverter;

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        List<RegisteredClient> registeredClients = clientConverter.convert(clients.getClients());

//        JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
//        registeredClients.forEach(registeredClient -> {
//            registeredClientRepository.save(registeredClient);
//        });

        return new InMemoryRegisteredClientRepository(registeredClients);
    }
}
