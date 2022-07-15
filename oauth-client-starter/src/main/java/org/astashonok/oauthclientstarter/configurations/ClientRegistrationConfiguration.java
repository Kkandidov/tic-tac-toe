package org.astashonok.oauthclientstarter.configurations;

import lombok.RequiredArgsConstructor;
import org.astashonok.oauthclientstarter.converters.RegistrationClientsConverter;
import org.astashonok.oauthclientstarter.properties.RegistrationClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
@RequiredArgsConstructor
public class ClientRegistrationConfiguration {

    private final RegistrationClients registrationClients;
    private final RegistrationClientsConverter registrationClientsConverter;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                registrationClientsConverter.convert(registrationClients)
        );
    }
}
