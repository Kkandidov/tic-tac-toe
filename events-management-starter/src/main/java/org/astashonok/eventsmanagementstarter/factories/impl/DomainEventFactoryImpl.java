package org.astashonok.eventsmanagementstarter.factories.impl;

import lombok.NonNull;
import org.astashonok.eventsmanagementstarter.factories.DomainEventFactory;
import org.astashonok.eventsmanagementstarter.models.DomainEvent;
import org.astashonok.eventsmanagementstarter.models.DomainEventInfo;
import org.astashonok.eventsmanagementstarter.models.DomainEventType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DomainEventFactoryImpl implements DomainEventFactory {

    @Override
    public DomainEvent newDomainEvent(@NonNull UUID domainId, @NonNull DomainEventType domainEventType,
                                      DomainEventInfo domainEventInfo) {

        return DomainEvent.builder()
                .eventId(UUID.randomUUID())
                .domainId(domainId)
                .domainEventType(domainEventType)
                .domainEventInfo(domainEventInfo)
                .build();
    }

    @Override
    public DomainEvent newDomainEvent(@NonNull UUID domainId, @NonNull DomainEventType domainEventType) {
        return newDomainEvent(domainId, domainEventType, null);
    }
}
