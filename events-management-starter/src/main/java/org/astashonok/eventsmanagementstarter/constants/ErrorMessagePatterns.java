package org.astashonok.eventsmanagementstarter.constants;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessagePatterns {

    public static final String NO_BINDINGS = "There are no bindings";
    public static final String BINDING_NOT_FOUND = "Binding to passed domain '%s' not found";

    public static final String EVENT_ID_NOT_FOUND = "Event id not found";
    public static final String DOMAIN_ID_NOT_FOUND = "Domain id not found";
    public static final String DOMAIN_EVENT_TYPE_NOT_FOUND = "Domain event type not found";
    public static final String DOMAIN_EVENT_TYPE_PROVIDER_NOT_FOUND = "No implementation of DomainEventTypeProvider bean";
}
