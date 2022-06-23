package org.astashonok.userservice.providers;

import lombok.NonNull;
import org.astashonok.userservice.constants.ClientType;
import org.astashonok.userservice.converters.client.ClientConverter;

public interface ClientConverterProvider {

    ClientConverter getByClientType(@NonNull ClientType clientType);
}
