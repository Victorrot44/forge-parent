package io.github.victorrot44.forge.web.core.exception;

import io.github.victorrot44.forge.web.core.error.ErrorCategory;
import io.github.victorrot44.forge.web.core.error.ErrorType;
import io.github.victorrot44.forge.web.core.util.Preconditions;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ForgeException extends RuntimeException {

    private final ErrorCategory errorCategory;
    private final ErrorType errorType;
    private final Map<String, Object> details;

    public ForgeException(ErrorCategory errorCategory, ErrorType errorType, Map<String, Object> details, String message, Throwable cause) {
        super(message, cause);
        this.errorCategory = Objects.requireNonNull(errorCategory, "errorCategory must not be null.");
        this.errorType = Objects.requireNonNull(errorType, "errorType must not be null.");
        this.details = Preconditions.immutableMap(details);
    }

    public ForgeException(ErrorCategory errorCategory, ErrorType errorType, Map<String, Object> details, String message) {
        this(errorCategory, errorType, details, message, null);
    }

    public ForgeException(ErrorCategory errorCategory, ErrorType errorType, String message, Throwable cause) {
        this(errorCategory, errorType, Map.of(), message, cause);
    }

    public ForgeException(ErrorCategory errorCategory, ErrorType errorType, String message) {
        this(errorCategory, errorType, Map.of(), message, null);
    }

    public ErrorCategory errorCategory() {
        return errorCategory;
    }

    public ErrorType errorType() {
        return errorType;
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
