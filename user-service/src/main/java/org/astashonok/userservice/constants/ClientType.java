package org.astashonok.userservice.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClientType {

    AUTHORIZATION_CODE(ClientTypeNames.AUTHORIZATION_CODE),
    CLIENT_CREDENTIALS(ClientTypeNames.CLIENT_CREDENTIALS),
    PASSWORD(ClientTypeNames.PASSWORD);

    private final String name;
}
