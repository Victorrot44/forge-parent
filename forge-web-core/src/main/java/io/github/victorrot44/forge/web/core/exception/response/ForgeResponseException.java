package io.github.victorrot44.forge.web.core.exception.response;

import io.github.victorrot44.forge.web.core.error.ErrorDescriptor;
import io.github.victorrot44.forge.web.core.exception.ForgeException;

import java.util.Map;
import java.util.Objects;

public abstract sealed class ForgeResponseException extends ForgeException
        permits AuthenticationException, AuthorizationException, BusinessException, ConfigurationException,
        ExternalServiceException, InternalException, ValidationException
{
    private final ErrorDescriptor errorDescriptor;

    protected ForgeResponseException(ErrorDescriptor errorDescriptor, String message, Map<String, Object> details, Throwable cause) {
        super(Objects.requireNonNullElse(message, Objects.requireNonNull(errorDescriptor.defaultMessage(), "defaultMessage cannot be null.")), cause, details);
        this.errorDescriptor = Objects.requireNonNull(errorDescriptor, "errorDescriptor cannot be null.");
    }

    protected ForgeResponseException(ErrorDescriptor errorDescriptor, Map<String, Object> details, Throwable cause) {
        this(errorDescriptor, null, details, cause);
    }

    protected ForgeResponseException(ErrorDescriptor errorDescriptor, Map<String, Object> details) {
        this(errorDescriptor, null, details, null);
    }

    protected ForgeResponseException(ErrorDescriptor errorDescriptor, Throwable cause) {
        this(errorDescriptor, null, null, cause);
    }

    protected ForgeResponseException(ErrorDescriptor errorDescriptor) {
        this(errorDescriptor, null, null, null);
    }


    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }

    public String getCode() {
        return errorDescriptor.code();
    }

    public int httpStatus() {
        return errorDescriptor.httpStatus();
    }

}
