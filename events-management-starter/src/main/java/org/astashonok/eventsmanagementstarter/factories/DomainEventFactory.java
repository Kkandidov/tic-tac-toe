package org.astashonok.eventsmanagementstarter.factories;

import lombok.NonNull;
import org.astashonok.eventsmanagementstarter.models.DomainEvent;
import org.astashonok.eventsmanagementstarter.models.DomainEventInfo;
import org.astashonok.eventsmanagementstarter.models.DomainEventType;

import java.util.UUID;

public interface DomainEventFactory {

    DomainEvent newDomainEvent(@NonNull UUID domainId, @NonNull DomainEventType domainEventType, DomainEventInfo domainEventInfo);
    DomainEvent newDomainEvent(@NonNull UUID domainId, @NonNull DomainEventType domainEventType);
}
