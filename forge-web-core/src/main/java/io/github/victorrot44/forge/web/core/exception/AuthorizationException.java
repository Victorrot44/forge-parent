package io.github.victorrot44.forge.web.core.exception;

import io.github.victorrot44.forge.web.core.error.ErrorDescriptor;

import java.util.Map;

public final class AuthorizationException extends ForgeException {

    private static final long serialVersionUID = 1L;

    public AuthorizationException(ErrorDescriptor errorDescriptor, Map<String, Object> details, Throwable cause) {
        super(errorDescriptor, details, cause);
    }

    public AuthorizationException(ErrorDescriptor errorDescriptor, Map<String, Object> details) {
        super(errorDescriptor, details);
    }

    public AuthorizationException(ErrorDescriptor errorDescriptor, Throwable cause) {
        super(errorDescriptor, cause);
    }

    public AuthorizationException(ErrorDescriptor errorDescriptor) {
        super(errorDescriptor);
    }

}
