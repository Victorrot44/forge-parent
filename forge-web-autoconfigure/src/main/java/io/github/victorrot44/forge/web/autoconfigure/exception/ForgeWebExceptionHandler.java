package io.github.victorrot44.forge.web.autoconfigure.exception;

import io.github.victorrot44.forge.web.core.exception.ForgeException;
import io.github.victorrot44.forge.web.core.exception.ForgeInternalException;
import io.github.victorrot44.forge.web.core.response.ApiResponse;
import io.github.victorrot44.forge.web.core.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ForgeWebExceptionHandler {

    @ExceptionHandler(ForgeException.class)
    public ResponseEntity<ErrorResponse> handleException(ForgeException ex) {
        if (ex.hasDetails()) {
            log.info("ForgeWebExceptionHandler: handleException");
            log.info("Details[{}]", ex.detailsToString());
        }
        return ErrorResponseHelper.from(ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedError(Exception ex) {
        log.info("ForgeWebExceptionHandler: handleUnexpectedError", ex);
        return ErrorResponseHelper.internalServerError();
    }

    @ExceptionHandler(ForgeInternalException.class)
    public ResponseEntity<ErrorResponse> handleInternalException(ForgeInternalException ex) {
        if (ex.hasDetails()) {
            log.info("ForgeWebExceptionHandler: handleInternalException");
            log.info("Details[{}]", ex.detailsToString());
        }
        return  ErrorResponseHelper.internalServerError();
    }

}
