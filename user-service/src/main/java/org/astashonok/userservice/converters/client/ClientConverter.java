package org.astashonok.userservice.converters.client;

import lombok.NonNull;
import org.astashonok.userservice.models.Client;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.List;

public interface ClientConverter {

    String CLIENT_CONVERTER_NAME_POSTFIX = "ClientConverter";
    String TYPED_CLIENT_CONVERTER = "typedClientConverter";

    RegisteredClient convert(@NonNull Client client);

    List<RegisteredClient> convert(@NonNull List<Client> clients);
}
