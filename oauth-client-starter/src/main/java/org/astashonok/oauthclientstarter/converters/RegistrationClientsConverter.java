package org.astashonok.oauthclientstarter.converters;

import lombok.NonNull;
import org.astashonok.oauthclientstarter.properties.RegistrationClients;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

import java.util.List;

public interface RegistrationClientsConverter {

    List<ClientRegistration> convert(@NonNull RegistrationClients registrationClients);
}
