package io.github.victorrot44.forge.web.core.exception.response;

import io.github.victorrot44.forge.web.core.error.ErrorDescriptor;

import java.util.Map;

public abstract non-sealed class BusinessException extends ForgeResponseException {

    private static final long serialVersionUID = 1L;

    protected BusinessException(ErrorDescriptor errorDescriptor, Map<String, Object> details, Throwable cause) {
        super(errorDescriptor, details, cause);
    }

    protected BusinessException(ErrorDescriptor errorDescriptor, Map<String, Object> details) {
        super(errorDescriptor, details);
    }

    protected BusinessException(ErrorDescriptor errorDescriptor, Throwable cause) {
        super(errorDescriptor, cause);
    }

    protected BusinessException(ErrorDescriptor errorDescriptor) {
        super(errorDescriptor);
    }

}
