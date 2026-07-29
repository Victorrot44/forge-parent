package io.github.victorrot44.forge.web.core.exception;

import io.github.victorrot44.forge.web.core.error.ErrorDescriptor;

import java.util.Map;

public final class ConfigurationException extends ForgeException {

    private static final long serialVersionUID = 1L;

    public ConfigurationException(ErrorDescriptor errorDescriptor, Map<String, Object> details, Throwable cause) {
        super(errorDescriptor, details, cause);
    }

    public ConfigurationException(ErrorDescriptor errorDescriptor, Map<String, Object> details) {
        super(errorDescriptor, details);
    }

    public ConfigurationException(ErrorDescriptor errorDescriptor, Throwable cause) {
        super(errorDescriptor, cause);
    }

    public ConfigurationException(ErrorDescriptor errorDescriptor) {
        super(errorDescriptor);
    }

}
