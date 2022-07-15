package org.astashonok.eventsmanagementstarter.providers.impl;

import lombok.NonNull;
import org.astashonok.eventsmanagementstarter.constants.ErrorMessagePatterns;
import org.astashonok.eventsmanagementstarter.exceptions.DomainEventTypeProviderException;
import org.astashonok.eventsmanagementstarter.models.DomainEventType;
import org.astashonok.eventsmanagementstarter.providers.DomainEventTypeProvider;

public class DefaultDomainEventTypeProvider implements DomainEventTypeProvider {

    @Override
    public DomainEventType getDomainEventType(@NonNull String domainEventTypeName) {
        throw new DomainEventTypeProviderException(ErrorMessagePatterns.DOMAIN_EVENT_TYPE_PROVIDER_NOT_FOUND);
    }
}
