package org.astashonok.eventsmanagementstarter.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.astashonok.eventsmanagementstarter.constants.DomainEventFieldNames;
import org.astashonok.eventsmanagementstarter.exceptions.DomainEventDeserializationException;
import org.astashonok.eventsmanagementstarter.models.DomainEvent;
import org.astashonok.eventsmanagementstarter.models.DomainEventInfo;
import org.astashonok.eventsmanagementstarter.models.DomainEventType;
import org.astashonok.eventsmanagementstarter.providers.DomainEventTypeProvider;
import org.astashonok.eventsmanagementstarter.providers.impl.DefaultDomainEventTypeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.astashonok.eventsmanagementstarter.constants.DomainEventFieldNames.DOMAIN_ID_FIELD_NAME;
import static org.astashonok.eventsmanagementstarter.constants.DomainEventFieldNames.EVENT_ID_FIELD_NAME;
import static org.astashonok.eventsmanagementstarter.constants.ErrorMessagePatterns.*;

@Component
public class DomainEventDeserializer extends JsonDeserializer<DomainEvent> {

    private DomainEventTypeProvider domainEventTypeProvider;

    public DomainEventDeserializer(@Autowired(required = false) DomainEventTypeProvider domainEventTypeProvider) {
        this.domainEventTypeProvider = domainEventTypeProvider;
    }

    @PostConstruct
    public void init() {
        if (domainEventTypeProvider == null) {
            domainEventTypeProvider = new DefaultDomainEventTypeProvider();
        }
    }

    @Override
    public DomainEvent deserialize(JsonParser parser, DeserializationContext context) {
        DomainEvent domainEvent = new DomainEvent();

        ObjectCodec codec = parser.getCodec();
        JsonNode rootNode = getObjectNode(parser, codec);
        if (rootNode != null) {
            domainEvent.setEventId(getIdOrThrow(rootNode, EVENT_ID_FIELD_NAME, EVENT_ID_NOT_FOUND));
            domainEvent.setDomainId(getIdOrThrow(rootNode, DOMAIN_ID_FIELD_NAME, DOMAIN_ID_NOT_FOUND));

            DomainEventType eventType = getDomainEventTypeOrThrow(rootNode);
            domainEvent.setDomainEventType(eventType);

            Class<? extends DomainEventInfo> eventInfoClass = eventType.getEventInfoClass();
            if (eventInfoClass != null) {
                domainEvent.setDomainEventInfo(getDomainEventInfo(rootNode, eventInfoClass, codec));
            }
        }

        return domainEvent;
    }

    private DomainEventInfo getDomainEventInfo(JsonNode rootNode, Class<? extends DomainEventInfo> eventInfoClass,
                                               ObjectCodec codec) {

        return Optional.ofNullable(rootNode.get(DomainEventFieldNames.DOMAIN_EVENT_INFO_FIELD_NAME))
                .map(eventInfoNode -> getDomainEventInfo(eventInfoClass, codec, eventInfoNode))
                .orElse(null);
    }

    private DomainEventInfo getDomainEventInfo(Class<? extends DomainEventInfo> eventInfoClass,
                                               ObjectCodec codec, JsonNode eventInfoNode) {

        try {
            return codec.treeToValue(eventInfoNode, eventInfoClass);
        } catch (JsonProcessingException e) {
            throw new DomainEventDeserializationException(e);
        }
    }

    private DomainEventType getDomainEventTypeOrThrow(JsonNode rootNode) {
        return Optional.ofNullable(rootNode.get(DomainEventFieldNames.DOMAIN_EVENT_TYPE_FIELD_NAME))
                .map(JsonNode::asText)
                .filter(StringUtils::isNotEmpty)
                .map(domainEventTypeProvider::getDomainEventType)
                .orElseThrow(() -> new DomainEventDeserializationException(DOMAIN_EVENT_TYPE_NOT_FOUND));
    }

    private UUID getIdOrThrow(JsonNode rootNode, String pathName, String errorMessage) {
        return Optional.of(pathName)
                .map(rootNode::get)
                .map(JsonNode::asText)
                .filter(StringUtils::isNotEmpty)
                .map(UUID::fromString)
                .orElseThrow(() -> new DomainEventDeserializationException(errorMessage));
    }

    private JsonNode getObjectNode(JsonParser parser, ObjectCodec objectCodec) {
        return Optional.ofNullable(objectCodec)
                .map(codec -> getJsonNodeOrThrow(parser, codec))
                .orElse(null);

    }

    private JsonNode getJsonNodeOrThrow(JsonParser parser, ObjectCodec codec) {
        try {
            return codec.readTree(parser);
        } catch (IOException e) {
            throw new DomainEventDeserializationException(e);
        }
    }
}
