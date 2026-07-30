package io.github.victorrot44.forge.web.core.exception.response;

import io.github.victorrot44.forge.web.core.error.ErrorDescriptor;

import java.util.Map;

public final class InternalException extends ForgeResponseException {

    public InternalException(ErrorDescriptor errorDescriptor, Map<String, Object> details, Throwable cause) {
        super(errorDescriptor, details, cause);
    }

    public InternalException(ErrorDescriptor errorDescriptor, Map<String, Object> details) {
        super(errorDescriptor, details);
    }

    public InternalException(ErrorDescriptor errorDescriptor, Throwable cause) {
        super(errorDescriptor, cause);
    }

    public InternalException(ErrorDescriptor errorDescriptor) {
        super(errorDescriptor);
    }

}
