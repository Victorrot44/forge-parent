package io.github.victorrot44.forge.web.autoconfigure.exception;

import io.github.victorrot44.forge.web.core.error.ErrorCategory;
import io.github.victorrot44.forge.web.core.error.ErrorType;
import io.github.victorrot44.forge.web.core.exception.ForgeException;
import io.github.victorrot44.forge.web.core.exception.ForgeInternalException;
import io.github.victorrot44.forge.web.core.response.ErrorDetail;
import io.github.victorrot44.forge.web.core.response.ErrorResponse;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

class ForgeWebExceptionHandlerTest {

    private final ForgeWebExceptionHandler handler = new ForgeWebExceptionHandler();

    @Test
    void shouldHandleResourceNotFound() {
        var exception = new ForgeException(
                ErrorCategory.BUSINESS,
                ErrorType.RESOURCE_NOT_FOUND,
                "Información solicitada no encontrada."
        );
        ResponseEntity<ErrorResponse> response = handler.handleException(exception);
        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().httpStatus()).isEqualTo(404);
        assertThat(response.getBody().code()).isEqualTo("RESOURCE_NOT_FOUND");
        assertThat(response.getBody().message()).isEqualTo("Información solicitada no encontrada.");
    }

    @Test
    void shouldHandleForgeInternalException() {
        var exception = new ForgeInternalException("Error inesperado de Forge.");
        ResponseEntity<ErrorResponse> response = handler.handleInternalException(exception);
        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().httpStatus()).isEqualTo(500);
        assertThat(response.getBody().code()).isEqualTo("INTERNAL_SERVER_ERROR");
        assertThat(response.getBody().message()).isEqualTo("An unexpected error occurred.");
    }

    @Test
    void shouldHandleUnexpectedException() {
        var exception = new IllegalStateException("Internal database failure.");
        ResponseEntity<ErrorResponse> response = handler.handleUnexpectedError(exception);
        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().httpStatus()).isEqualTo(500);
        assertThat(response.getBody().code()).isEqualTo("INTERNAL_SERVER_ERROR");
        assertThat(response.getBody().message()).isEqualTo("An unexpected error occurred.");
    }
    
    @Test
    void shouldHandleMethodArgumentNotValidException() {
        var bindingResult = new BeanPropertyBindingResult(new Object(), "request");
        bindingResult.addError(
                new FieldError(
                        "request",
                        "name",
                        "",
                        false,
                        null,
                        null,
                        "no debe estar vacío"
                )
        );
        bindingResult.addError(
                new FieldError(
                        "request",
                        "email",
                        "correo-invalido",
                        false,
                        null,
                        null,
                        "debe ser una dirección de correo electrónico válida"
                )
        );
        var exception = new MethodArgumentNotValidException(null, bindingResult);
        ResponseEntity<ErrorResponse> response = handler.handleInvalidArguments(exception);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.httpStatus()).isEqualTo(400);
        assertThat(body.code()).isEqualTo("INVALID_ARGUMENT");
        assertThat(body.errors()).hasSize(2);
        assertThat(body.errors()).extracting(ErrorDetail::field)
                .containsExactly("name", "email");
        assertThat(body.errors()).extracting(ErrorDetail::message)
                .containsExactly(
                        "no debe estar vacío",
                        "debe ser una dirección de correo electrónico válida"
                );
    }
    
    @Test
    void shouldHandleUnreadableRequest() {
        var exception = new HttpMessageNotReadableException("Request body could not be read.", null);
        ResponseEntity<ErrorResponse> response = handler.handleInvalidRequest(exception);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.httpStatus()).isEqualTo(400);
        assertThat(body.code()).isEqualTo("INVALID_ARGUMENT");
        assertThat(body.message()).isEqualTo("The request body could not be read.");
    }

    @Test
    void shouldHandleMissingRequestParameter() {
        var exception = new MissingServletRequestParameterException("page", "Integer");
        ResponseEntity<ErrorResponse> response = handler.handleMissingParameters(exception);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.httpStatus()).isEqualTo(400);
        assertThat(body.code()).isEqualTo("INVALID_ARGUMENT");
        assertThat(body.errors())
                .singleElement()
                .satisfies(error -> {
                    assertThat(error.field()).isEqualTo("page");
                    assertThat(error.message()).isEqualTo("Required request parameter 'page' is missing");
                });
    }
    
    @Test
    void shouldHandleMethodArgumentTypeMismatch() {
        var exception = new MethodArgumentTypeMismatchException(
                "abc", Long.class, "id", null, new NumberFormatException("For input string: abc")
        );
        ResponseEntity<ErrorResponse> response = handler.handleMethodArgumentTypeMismatch(exception);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.httpStatus()).isEqualTo(400);
        assertThat(body.code()).isEqualTo("INVALID_ARGUMENT");
        assertThat(body.errors()).singleElement()
                .satisfies(error -> {
                    assertThat(error.field()).isEqualTo("id");
                    assertThat(error.message()).isEqualTo("Failed to convert parameter 'id'");
                });
    }
    
    @Test
    void shouldHandleMissingRequestHeader() {
        var exception = new MissingRequestHeaderException("X-Request-Id",null);
        ResponseEntity<ErrorResponse> response = handler.handleMissingRequestHeader(exception);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.httpStatus()).isEqualTo(400);
        assertThat(body.code()).isEqualTo("INVALID_ARGUMENT");
        assertThat(body.errors()).singleElement()
                .satisfies(error -> {
                    assertThat(error.field()).isEqualTo("X-Request-Id");
                    assertThat(error.message())
                            .isEqualTo("Required request header 'X-Request-Id' is missing");
                    assertThat(error.details()).isEmpty();
                });
    }
    
    @Test
    void shouldHandleNotFound() {
        var exception = new NoResourceFoundException(HttpMethod.GET, "/api/users/999", "/api/users/999");
        ResponseEntity<ErrorResponse> response = handler.handleNotFound(exception);
        assertThat(response.getStatusCode().value()).isEqualTo(404);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.httpStatus()).isEqualTo(404);
        assertThat(body.code()).isEqualTo("RESOURCE_NOT_FOUND");
    }

    @Test
    void shouldHandleMethodNotSupported() {
        var exception = new HttpRequestMethodNotSupportedException("PATCH", Set.of("GET", "POST"));
        ResponseEntity<ErrorResponse> response = handler.handleMethodNotSupported(exception);
        assertThat(response.getStatusCode().value()).isEqualTo(405);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.httpStatus()).isEqualTo(405);
        assertThat(body.code()).isEqualTo("METHOD_NOT_ALLOWED");
        assertThat(body.errors()).singleElement()
                .satisfies(error -> {
                    assertThat(error.message()).isEqualTo("Request method 'PATCH' is not supported.");
                    assertThat(error.details())
                            .containsEntry(
                                    "allowed",
                                    Set.of("GET", "POST").toArray()
                            );
                });
    }

}