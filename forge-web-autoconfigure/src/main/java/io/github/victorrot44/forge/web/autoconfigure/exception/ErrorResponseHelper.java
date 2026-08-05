package io.github.victorrot44.forge.web.autoconfigure.exception;

import io.github.victorrot44.forge.web.core.error.ErrorType;
import io.github.victorrot44.forge.web.core.exception.ForgeException;
import io.github.victorrot44.forge.web.core.response.ApiResponse;
import io.github.victorrot44.forge.web.core.response.ErrorDetail;
import io.github.victorrot44.forge.web.core.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

final class ErrorResponseHelper {

    private ErrorResponseHelper() {
        throw new AssertionError("No instances.");
    }

    //  ----------------------------------------------------------------------------------------------------------------
    //      GENERAL EXCEPTION HANDLER
    //  ----------------------------------------------------------------------------------------------------------------
    static ResponseEntity<ErrorResponse> from(ForgeException exception) {
        var status = ErrorTypeStatusMapper.resolve(exception.errorType());
        var response = ApiResponse.error()
                .httpStatus(status.value())
                .code(exception.errorType().name())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(status).body(response);
    }

    //  ----------------------------------------------------------------------------------------------------------------
    //      400 - BAD REQUEST
    //  ----------------------------------------------------------------------------------------------------------------
    static ResponseEntity<ErrorResponse> invalidArguments(List<ErrorDetail> errors) {
        var response = ApiResponse.error()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .code(ErrorType.INVALID_ARGUMENT.name())
                .message("An invalid arguments were provided.")
                .errors(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    static ResponseEntity<ErrorResponse> invalidRequest(String message) {
        var response = ApiResponse.error()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .code(ErrorType.INVALID_ARGUMENT.name())
                .message(message)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //  ----------------------------------------------------------------------------------------------------------------
    //      404 - NOT FOUND
    //  ----------------------------------------------------------------------------------------------------------------
    static ResponseEntity<ErrorResponse> notFound() {
        var response = ApiResponse.error()
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .code(ErrorType.RESOURCE_NOT_FOUND.name())
                .message("The requested resource was not found.")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //  ----------------------------------------------------------------------------------------------------------------
    //      405 - METHOD NOT ALLOWED
    //  ----------------------------------------------------------------------------------------------------------------
    static ResponseEntity<ErrorResponse> methodNotAllowed(ErrorDetail error) {
        var response = ApiResponse.error()
                .httpStatus(HttpStatus.METHOD_NOT_ALLOWED.value())
                .code(HttpStatus.METHOD_NOT_ALLOWED.name())
                .message("The request HTTP method is not supported.")
                .addError(error)
                .build();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    //  ----------------------------------------------------------------------------------------------------------------
    //      500 - INTERNAL SERVER ERROR
    //  ----------------------------------------------------------------------------------------------------------------
    static ResponseEntity<ErrorResponse> internalServerError() {
        var response = ApiResponse.error()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(ErrorType.INTERNAL_SERVER_ERROR.name())
                .message("An unexpected error occurred.")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
