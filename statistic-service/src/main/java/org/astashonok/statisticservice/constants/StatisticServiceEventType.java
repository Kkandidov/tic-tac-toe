package org.astashonok.statisticservice.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.astashonok.eventsmanagementstarter.models.DomainEventInfo;
import org.astashonok.eventsmanagementstarter.models.DomainEventType;

@RequiredArgsConstructor
@Getter
public enum StatisticServiceEventType implements DomainEventType {

    BATTLE_FINISHED(DomainNames.BATTLE_DOMAIN_NAME);

    private final String domainName;
    private final Class<? extends DomainEventInfo> eventInfoClass;

    StatisticServiceEventType(String domainName) {
        this.domainName = domainName;
        this.eventInfoClass = null;
    }
}
