package org.astashonok.statisticservice.providers;

import lombok.NonNull;
import org.astashonok.eventsmanagementstarter.models.DomainEventType;
import org.astashonok.eventsmanagementstarter.providers.DomainEventTypeProvider;
import org.astashonok.statisticservice.constants.StatisticServiceEventType;
import org.springframework.stereotype.Component;

@Component
public class DomainEventTypeProviderImpl implements DomainEventTypeProvider {

    @Override
    public DomainEventType getDomainEventType(@NonNull String domainEventTypeName) {
        return StatisticServiceEventType.valueOf(domainEventTypeName);
    }
}
