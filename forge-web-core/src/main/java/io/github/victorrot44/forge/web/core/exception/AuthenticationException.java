package io.github.victorrot44.forge.web.core.exception;

import io.github.victorrot44.forge.web.core.error.ErrorDescriptor;

import java.util.Map;

public final class AuthenticationException extends ForgeException {

    private static final long serialVersionUID = 1L;

    public AuthenticationException(ErrorDescriptor errorDescriptor, Map<String, Object> details, Throwable cause) {
        super(errorDescriptor, details, cause);
    }

    public AuthenticationException(ErrorDescriptor errorDescriptor, Map<String, Object> details) {
        super(errorDescriptor, details);
    }

    public AuthenticationException(ErrorDescriptor errorDescriptor, Throwable cause) {
        super(errorDescriptor, cause);
    }

    public AuthenticationException(ErrorDescriptor errorDescriptor) {
        super(errorDescriptor);
    }

}
