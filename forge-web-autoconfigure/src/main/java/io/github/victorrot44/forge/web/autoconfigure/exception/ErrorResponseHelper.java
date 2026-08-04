package io.github.victorrot44.forge.web.autoconfigure.exception;

import io.github.victorrot44.forge.web.core.exception.ForgeException;
import io.github.victorrot44.forge.web.core.response.ApiResponse;
import io.github.victorrot44.forge.web.core.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

final class ErrorResponseHelper {

    private ErrorResponseHelper() {
        throw new AssertionError("No instances.");
    }

    static ResponseEntity<ErrorResponse> from(ForgeException exception) {
        var status = ErrorTypeStatusMapper.resolve(exception.errorType());
        var response = ApiResponse.error()
                .httpStatus(status.value())
                .code(exception.errorType().name())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(status).body(response);
    }

    static ResponseEntity<ErrorResponse> internalServerError() {
        var response = ApiResponse.error()
                .httpStatus(500)
                .code("INTERNAL_SERVER_ERROR")
                .message("An unexpected error occurred.")
                .build();
        return ResponseEntity.status(500).body(response);
    }

}
