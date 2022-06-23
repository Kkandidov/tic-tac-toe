package org.astashonok.userservice.models;

import lombok.Getter;
import lombok.Setter;
import org.astashonok.userservice.constants.ClientType;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class Client {

    private UUID id = UUID.randomUUID();
    private ClientType clientType;
    private String clientId;
    private String name;
    private String secret;
    private Set<String> scope;
    private Set<String> redirectUri;
}
