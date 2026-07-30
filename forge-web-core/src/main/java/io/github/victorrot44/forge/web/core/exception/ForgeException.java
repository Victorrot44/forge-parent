package io.github.victorrot44.forge.web.core.exception;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class ForgeException extends RuntimeException {

    private final Map<String, Object> details;

    protected ForgeException(String message, Throwable cause, Map<String, Object> details) {
        super(Objects.requireNonNull(message, "message cannot be null."), cause);
        this.details = inmutableDetails(details);
    }

    protected ForgeException(String message, Map<String, Object> details) {
        this(message, null, details);
    }

    protected ForgeException(String message, Throwable cause) {
        this(message, cause, Map.of());
    }

    protected ForgeException(String message) {
        this(message, null, Map.of());
    }

    private static Map<String, Object> inmutableDetails(Map<String, Object> details) {
        return (details == null) ? Map.of() : Map.copyOf(details);
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public boolean hasDetails() {
        return !details.isEmpty();
    }

    public <T> Optional<T> getDetail(String key, Class<T> type) {
        Object value = details.get(key);
        return type.isInstance(value) ? Optional.of(type.cast(value)) : Optional.empty();
    }

}
