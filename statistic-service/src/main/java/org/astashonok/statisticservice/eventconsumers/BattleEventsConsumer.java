package org.astashonok.statisticservice.eventconsumers;

import lombok.extern.slf4j.Slf4j;
import org.astashonok.eventsmanagementstarter.models.DomainEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class BattleEventsConsumer {

    @Bean
    public Consumer<DomainEvent> consumeBattleFinishedEvent() {
        // while doing nothing (there is no repositories and services)
        return domainEvent -> {};
    }
}
