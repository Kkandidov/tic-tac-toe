package org.astashonok.eventsmanagementstarter.publishers.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.astashonok.eventsmanagementstarter.models.DomainEvent;
import org.astashonok.eventsmanagementstarter.properties.DomainEventBindings;
import org.astashonok.eventsmanagementstarter.publishers.DomainEventPublisher;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DomainEventPublisherImpl implements DomainEventPublisher {

    private final StreamBridge streamBridge;

    private final DomainEventBindings domainEventBindings;

    @Override
    public void publish(@NonNull DomainEvent domainEvent) {
        streamBridge.send(domainEventBindings.getRequiredBinding(domainEvent.getDomainEventType()), domainEvent);
    }

    @Override
    public void publish(@NonNull List<DomainEvent> domainEvents) {
        domainEvents.stream()
                .filter(Objects::nonNull)
                .forEach(this::publish);
    }
}
