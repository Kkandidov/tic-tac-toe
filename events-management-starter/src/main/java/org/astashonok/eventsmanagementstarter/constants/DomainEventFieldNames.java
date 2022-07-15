package org.astashonok.eventsmanagementstarter.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DomainEventFieldNames {

    public static final String EVENT_ID_FIELD_NAME = "eventId";
    public static final String DOMAIN_ID_FIELD_NAME = "domainId";
    public static final String DOMAIN_EVENT_TYPE_FIELD_NAME = "domainEventType";
    public static final String DOMAIN_EVENT_INFO_FIELD_NAME = "domainEventInfo";
}
