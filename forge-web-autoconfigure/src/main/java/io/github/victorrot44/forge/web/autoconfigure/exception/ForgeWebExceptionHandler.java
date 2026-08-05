package io.github.victorrot44.forge.web.autoconfigure.exception;

import io.github.victorrot44.forge.web.core.exception.ForgeException;
import io.github.victorrot44.forge.web.core.exception.ForgeInternalException;
import io.github.victorrot44.forge.web.core.response.ErrorDetail;
import io.github.victorrot44.forge.web.core.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ForgeWebExceptionHandler {

    //  ----------------------------------------------------------------------------------------------------------------
    //      GENERAL EXCEPTION
    //  ----------------------------------------------------------------------------------------------------------------
    @ExceptionHandler(ForgeException.class)
    public ResponseEntity<ErrorResponse> handleException(ForgeException ex) {
        if (ex.hasDetails()) {
            log.error("ForgeWebExceptionHandler: handleException");
            log.error("Details[{}]", ex.detailsToString());
        }
        return ErrorResponseHelper.from(ex);
    }

    //  ----------------------------------------------------------------------------------------------------------------
    //      400 - BAD REQUEST
    //  ----------------------------------------------------------------------------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArguments(MethodArgumentNotValidException ex) {
        log.debug("ForgeWebExceptionHandler: handleInvalidArguments", ex);
        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> ErrorDetail.builder()
                        .field(err.getField())
                        .message(err.getDefaultMessage())
                        .build()
                ).toList();
        return ErrorResponseHelper.invalidArguments(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(HttpMessageNotReadableException ex) {
        log.debug("ForgeWebExceptionHandler: handleInvalidRequest", ex);
        return ErrorResponseHelper.invalidRequest("The request body could not be read.");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameters(MissingServletRequestParameterException ex) {
        log.debug("ForgeWebExceptionHandler: handleMissingParameters", ex);
        var error = ErrorDetail.builder()
                .field(ex.getParameterName())
                .message("Required request parameter '%s' is missing".formatted(ex.getParameterName()))
                .build();
        return ErrorResponseHelper.invalidArguments(List.of(error));
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.debug("ForgeWebExceptionHandler: handleMethodArgumentTypeMismatch", ex);
        var error = ErrorDetail.builder()
                .field(ex.getName())
                .message("Failed to convert parameter '%s'".formatted(ex.getName()))
                .build();
        return ErrorResponseHelper.invalidArguments(List.of(error));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeader(MissingRequestHeaderException ex) {
        log.debug("ForgeWebExceptionHandler: handleMissingRequestHeader", ex);
        var error = ErrorDetail.builder()
                .field(ex.getHeaderName())
                .message("Required request header '%s' is missing".formatted(ex.getHeaderName()))
                .build();
        return ErrorResponseHelper.invalidArguments(List.of(error));
    }

    //  ----------------------------------------------------------------------------------------------------------------
    //      404 - NOT FOUND
    //  ----------------------------------------------------------------------------------------------------------------
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoResourceFoundException ex) {
        return ErrorResponseHelper.notFound();
    }

    //  ----------------------------------------------------------------------------------------------------------------
    //      405 - METHOD NOT ALLOWED
    //  ----------------------------------------------------------------------------------------------------------------
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.error("ForgeWebExceptionHandler: handleMethodNotSupported", ex);
        var error = ErrorDetail.builder()
                .message("Request method '%s' is not supported.".formatted(ex.getMethod()))
                .addDetail("allowed", ex.getSupportedMethods())
                .build();
        return ErrorResponseHelper.methodNotAllowed(error);
    }

    //  ----------------------------------------------------------------------------------------------------------------
    //      500 - INTERNAL SERVER ERROR
    //  ----------------------------------------------------------------------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedError(Exception ex) {
        log.error("ForgeWebExceptionHandler: handleUnexpectedError", ex);
        return ErrorResponseHelper.internalServerError();
    }

    @ExceptionHandler(ForgeInternalException.class)
    public ResponseEntity<ErrorResponse> handleInternalException(ForgeInternalException ex) {
        if (ex.hasDetails()) {
            log.error("ForgeWebExceptionHandler: handleInternalException");
            log.error("Details[{}]", ex.detailsToString());
        }
        return  ErrorResponseHelper.internalServerError();
    }

}
