package org.astashonok.userservice.properties;

import lombok.Data;
import org.astashonok.shared.configurations.YamlPropertySourceFactory;
import org.astashonok.userservice.models.Client;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "registered")
@PropertySource(
        value = Clients.REGISTERED_CLIENTS_CLASS_PATH,
        ignoreResourceNotFound = true,
        factory = YamlPropertySourceFactory.class
)
public class Clients {

    public static final String REGISTERED_CLIENTS_CLASS_PATH = "registered-clients.yml";

    private final List<Client> clients;
}
