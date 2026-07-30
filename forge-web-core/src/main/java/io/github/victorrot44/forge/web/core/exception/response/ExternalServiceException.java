package io.github.victorrot44.forge.web.core.exception.response;

import io.github.victorrot44.forge.web.core.error.ErrorDescriptor;

import java.util.Map;

public abstract non-sealed class ExternalServiceException extends ForgeResponseException {

    private static final long serialVersionUID = 1L;

    protected ExternalServiceException(ErrorDescriptor errorDescriptor, Map<String, Object> details, Throwable cause) {
        super(errorDescriptor, details, cause);
    }

    protected ExternalServiceException(ErrorDescriptor errorDescriptor, Map<String, Object> details) {
        super(errorDescriptor, details);
    }

    protected ExternalServiceException(ErrorDescriptor errorDescriptor, Throwable cause) {
        super(errorDescriptor, cause);
    }

    protected ExternalServiceException(ErrorDescriptor errorDescriptor) {
        super(errorDescriptor);
    }

}
