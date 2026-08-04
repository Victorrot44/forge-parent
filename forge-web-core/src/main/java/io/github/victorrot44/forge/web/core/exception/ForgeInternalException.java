package io.github.victorrot44.forge.web.core.exception;

import io.github.victorrot44.forge.web.core.util.Preconditions;

import java.util.Map;
import java.util.Optional;

public class ForgeInternalException extends RuntimeException {

    private final Map<String, Object> details;

    public ForgeInternalException(String message, Throwable cause, Map<String, Object> details) {
        super(message, cause);
        this.details = Preconditions.immutableMap(details);
    }

    public ForgeInternalException(String message, Throwable cause) {
        this(message, cause, null);
    }

    public ForgeInternalException(String message, Map<String, Object> details) {
        this(message, null, details);
    }
    
    public ForgeInternalException(String message) {
        this(message, null, null);
    }

    public boolean hasDetails() {
        return !details.isEmpty();
    }

    public <T> Optional<T> getDetail(String key, Class<T> type) {
        Object value = details.get(key);
        return type.isInstance(value) ? Optional.of(type.cast(value)) : Optional.empty();
    }

    public String detailsToString() {
        if (!hasDetails()) {
            return "";
        }
        return details.toString();
    }

}
