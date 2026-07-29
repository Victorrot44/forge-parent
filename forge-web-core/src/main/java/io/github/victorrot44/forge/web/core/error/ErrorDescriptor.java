package io.github.victorrot44.forge.web.core.error;

import java.io.Serializable;

public interface ErrorDescriptor extends Serializable {

    String code();
    String message();
    int httpStatus();
    ErrorCategory category();
    ErrorSeverity severity();

    default boolean isInfo() {
        return severity() == ErrorSeverity.INFO;
    }
    default boolean isError() {
        return severity() == ErrorSeverity.ERROR;
    }
    default boolean isFatal() {
        return severity() == ErrorSeverity.FATAL;
    }
    default boolean isWarning() {
        return severity() == ErrorSeverity.WARNING;
    }
    default boolean isValidation() {
        return category() == ErrorCategory.VALIDATION;
    }
    default boolean isBusiness() {
        return category() == ErrorCategory.BUSINESS;
    }
    default boolean isExternal() {
        return category() == ErrorCategory.EXTERNAL;
    }
    default boolean isSystem() {
        return category() == ErrorCategory.SYSTEM;
    }
    default boolean isSecurity() {
        return category() == ErrorCategory.SECURITY;
    }
    default boolean isConfiguration() {
        return category() == ErrorCategory.CONFIGURATION;
    }

}
