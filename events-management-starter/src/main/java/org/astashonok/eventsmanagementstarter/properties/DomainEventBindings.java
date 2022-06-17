package org.astashonok.eventsmanagementstarter.properties;

import lombok.Data;
import lombok.NonNull;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.astashonok.eventsmanagementstarter.constants.ErrorMessagePatterns;
import org.astashonok.eventsmanagementstarter.exceptions.BindingException;
import org.astashonok.eventsmanagementstarter.models.DomainEventType;
import org.astashonok.shared.configurations.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "event")
@PropertySource(
        value = DomainEventBindings.EVENT_BINDINGS_CLASS_PATH,
        ignoreResourceNotFound = true,
        factory = YamlPropertySourceFactory.class
)
public class DomainEventBindings {

    public static final String EVENT_BINDINGS_CLASS_PATH = "event-bindings.yml";

    private Map<String, String> bindings;

    public String getRequiredBinding(@NonNull DomainEventType domainEventType) {
        if (MapUtils.isEmpty(bindings)) {
            throw new BindingException(ErrorMessagePatterns.NO_BINDINGS);
        }

        String domainName = domainEventType.getDomainName();
        String binding = bindings.get(domainName);
        if (StringUtils.isEmpty(binding)) {
            throw new BindingException(String.format(ErrorMessagePatterns.BINDING_NOT_FOUND, domainName));
        }

        return binding;
    }
}
