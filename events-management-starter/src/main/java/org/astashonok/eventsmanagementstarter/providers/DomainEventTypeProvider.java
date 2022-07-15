package org.astashonok.eventsmanagementstarter.providers;

import lombok.NonNull;
import org.astashonok.eventsmanagementstarter.models.DomainEventType;

public interface DomainEventTypeProvider {

    DomainEventType getDomainEventType(@NonNull String domainEventTypeName);
}
