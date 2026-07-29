package io.github.victorrot44.forge.web.core.context;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public record ForgeContext(
        String requestId,
        Instant startedAt,
        Map<String, Object> attributes
) {

    public ForgeContext {
        Objects.requireNonNull(requestId, "requestId must not be null.");
        Objects.requireNonNull(startedAt, "startedAt must not be null.");
        attributes = (attributes == null || attributes.isEmpty())
                ? Map.of()
                : Map.copyOf(attributes);
    }

    public static ForgeContext of(String requestId)  {
        return of(requestId, null);
    }

    public static ForgeContext of(String requestId, Map<String, Object> attributes) {
        return new ForgeContext(requestId, Instant.now(), attributes);
    }

    public Duration elapsedTime() {
        return Duration.between(startedAt, Instant.now());
    }

    public boolean hasAttributes()  {
        return !attributes.isEmpty();
    }

    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    public <T> Optional<T> getAttribute(String key, Class<T> type) {
        Objects.requireNonNull(key, "key must not be null.");
        Objects.requireNonNull(type, "type must not be null.");
        Object value = attributes.get(key);
        return type.isInstance(value) ? Optional.of(type.cast(value)) : Optional.empty();
    }

    public Optional<Object> getAttribute(String key) {
        Objects.requireNonNull(key, "key must not be null.");
        return hasAttribute(key) ? Optional.of(attributes.get(key)) : Optional.empty();
    }

}
