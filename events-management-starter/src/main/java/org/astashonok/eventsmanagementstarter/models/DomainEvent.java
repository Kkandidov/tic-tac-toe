package org.astashonok.eventsmanagementstarter.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.astashonok.eventsmanagementstarter.deserializers.DomainEventDeserializer;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = DomainEventDeserializer.class)
public class DomainEvent {

    private UUID eventId;
    private UUID domainId;
    private DomainEventType domainEventType;
    private DomainEventInfo domainEventInfo;
}
