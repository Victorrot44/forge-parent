package io.github.victorrot44.forge.web.autoconfigure.exception;

import io.github.victorrot44.forge.web.core.error.ErrorType;
import org.springframework.http.HttpStatus;

import java.util.EnumMap;
import java.util.Map;

public final class ErrorTypeStatusMapper {

    private static final Map<ErrorType, HttpStatus> DEFAULT_STATUS;

    static {
        var  status = new EnumMap<ErrorType, HttpStatus>(ErrorType.class);
        status.put(ErrorType.INVALID_ARGUMENT, HttpStatus.BAD_REQUEST);
        status.put(ErrorType.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
        status.put(ErrorType.CONFLICT, HttpStatus.CONFLICT);
        status.put(ErrorType.AUTHENTICATION_REQUIRED, HttpStatus.UNAUTHORIZED);
        status.put(ErrorType.ACCESS_DENIED, HttpStatus.FORBIDDEN);
        status.put(ErrorType.EXTERNAL_SERVER_ERROR, HttpStatus.BAD_GATEWAY);
        status.put(ErrorType.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        status.put(ErrorType.TIMEOUT, HttpStatus.GATEWAY_TIMEOUT);
        DEFAULT_STATUS = Map.copyOf(status);
    }

    private ErrorTypeStatusMapper() {
        throw new AssertionError("No instances.");
    }

    static HttpStatus resolve(ErrorType errorType) {
        return DEFAULT_STATUS.getOrDefault(errorType, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
