package org.astashonok.userservice.properties;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.astashonok.common.utils.CommonUtils;
import org.astashonok.shared.configurations.YamlPropertySourceFactory;
import org.astashonok.userservice.constants.ClientType;
import org.astashonok.userservice.models.Client;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
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

    private final List<Client> clientCredentialsClients;
    private final List<Client> authorizationCodeClients;
    private final List<Client> passwordClients;
    private List<Client> allClients;

    @PostConstruct
    public void init() {
        setClientType(clientCredentialsClients, ClientType.CLIENT_CREDENTIALS);
        setClientType(authorizationCodeClients, ClientType.AUTHORIZATION_CODE);
        setClientType(passwordClients, ClientType.PASSWORD);
        allClients = CommonUtils.joinNotEmpty(
                List.of(clientCredentialsClients, authorizationCodeClients, passwordClients));
    }

    private void setClientType(List<Client> clients, ClientType clientType) {
        if (CollectionUtils.isNotEmpty(clients)) {
            clients.forEach(client -> client.setClientType(clientType));
        }
    }
}
