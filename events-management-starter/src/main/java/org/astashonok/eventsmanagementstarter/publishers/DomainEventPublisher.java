package org.astashonok.eventsmanagementstarter.publishers;

import lombok.NonNull;
import org.astashonok.eventsmanagementstarter.models.DomainEvent;

import java.util.List;

public interface DomainEventPublisher {

    void publish(@NonNull DomainEvent domainEvent);

    void publish(@NonNull List<DomainEvent> domainEvents);
}
