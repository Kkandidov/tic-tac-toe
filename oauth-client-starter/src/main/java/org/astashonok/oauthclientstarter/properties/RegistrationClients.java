package org.astashonok.oauthclientstarter.properties;

import lombok.Data;
import org.astashonok.oauthclientstarter.model.Client;
import org.astashonok.shared.configurations.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "registration")
@PropertySource(
        value = {
                RegistrationClients.DEFAULT_REGISTRATION_CLIENTS_CLASS_PATH,
                RegistrationClients.REGISTRATION_CLIENTS_CLASS_PATH
        },
        ignoreResourceNotFound = true,
        factory = YamlPropertySourceFactory.class
)
public class RegistrationClients {

    public static final String DEFAULT_REGISTRATION_CLIENTS_CLASS_PATH = "default-registration-clients.yml";
    public static final String REGISTRATION_CLIENTS_CLASS_PATH = "registration-clients.yml";

    private final List<Client> clients;
}