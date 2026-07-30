package io.github.victorrot44.forge.web.core.exception.response;

import io.github.victorrot44.forge.web.core.error.ErrorDescriptor;

import java.util.Map;

public final class ValidationException extends ForgeResponseException {

    public ValidationException(ErrorDescriptor errorDescriptor, Map<String, Object> details, Throwable cause) {
        super(errorDescriptor, details, cause);
    }

    public ValidationException(ErrorDescriptor errorDescriptor, Map<String, Object> details) {
        super(errorDescriptor, details);
    }

    public ValidationException(ErrorDescriptor errorDescriptor, Throwable cause) {
        super(errorDescriptor, cause);
    }

    public ValidationException(ErrorDescriptor errorDescriptor) {
        super(errorDescriptor);
    }

}
