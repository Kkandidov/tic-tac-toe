package org.astashonok.eventsmanagementstarter.exceptions;

public class DomainEventDeserializationException extends RuntimeException {

    public DomainEventDeserializationException(String message) {
        super(message);
    }

    public DomainEventDeserializationException(Throwable cause) {
        super(cause);
    }
}
