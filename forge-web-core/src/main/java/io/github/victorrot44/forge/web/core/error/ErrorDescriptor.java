package io.github.victorrot44.forge.web.core.error;

public interface ErrorDescriptor {

    String code();
    String message();
    int httpStatus();
    ErrorCategory category();
    ErrorSeverity severity();

}
