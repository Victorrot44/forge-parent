package io.github.victorrot44.forge.web.core.exception;

import io.github.victorrot44.forge.web.core.error.ErrorDescriptor;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract sealed class ForgeException extends RuntimeException
        permits AuthenticationException, AuthorizationException, BusinessException, ConfigurationException,
        ExternalServiceException, InternalException, ValidationException
{

    private final ErrorDescriptor errorDescriptor;
    private final Map<String, Object> details;

    protected ForgeException(ErrorDescriptor errorDescriptor, Map<String, Object> details, Throwable cause) {
        super(errorDescriptor.message(), cause);
        this.errorDescriptor = Objects.requireNonNull(errorDescriptor, "ErrorDescriptor must not be null.");
        this.details = (details == null) ? Map.of() : Map.copyOf(details);
    }

    protected ForgeException(ErrorDescriptor error, Map<String, Object> details) {
        this(error, details, null);
    }

    protected ForgeException(ErrorDescriptor error, Throwable cause) {
        this(error, null, cause);
    }

    protected ForgeException(ErrorDescriptor error) {
        this(error, null, null);
    }

    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public boolean hasDetails() {
        return !details.isEmpty();
    }

    public <T> Optional<T> findDetail(String key, Class<T> type) {
        Object value = details.get(key);
        return type.isInstance(value) ? Optional.of(type.cast(value)) : Optional.empty();
    }

    public boolean hasDetail(String key) {
        return details.containsKey(key);
    }

}
