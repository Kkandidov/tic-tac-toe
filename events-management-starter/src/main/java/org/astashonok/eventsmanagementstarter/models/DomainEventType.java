package org.astashonok.eventsmanagementstarter.models;

public interface DomainEventType {

    String getDomainName();

    Class<? extends DomainEventInfo> getEventInfoClass();
}
