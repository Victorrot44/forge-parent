package io.github.victorrot44.forge.web.core.exception;

import io.github.victorrot44.forge.web.core.error.ErrorDescriptor;

import java.util.Map;

public final class ExternalServiceException extends ForgeException {

    private static final long serialVersionUID = 1L;

    public ExternalServiceException(ErrorDescriptor errorDescriptor, Map<String, Object> details, Throwable cause) {
        super(errorDescriptor, details, cause);
    }

    public ExternalServiceException(ErrorDescriptor errorDescriptor, Map<String, Object> details) {
        super(errorDescriptor, details);
    }

    public ExternalServiceException(ErrorDescriptor errorDescriptor, Throwable cause) {
        super(errorDescriptor, cause);
    }

    public ExternalServiceException(ErrorDescriptor errorDescriptor) {
        super(errorDescriptor);
    }

}
