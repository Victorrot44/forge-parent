package io.github.victorrot44.forge.web.autoconfigure.exception;

import io.github.victorrot44.forge.web.core.error.ErrorCategory;
import io.github.victorrot44.forge.web.core.error.ErrorType;
import io.github.victorrot44.forge.web.core.exception.ForgeException;
import io.github.victorrot44.forge.web.core.response.ErrorDetail;
import io.github.victorrot44.forge.web.core.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorResponseHelperTest {

    @Test
    void shouldCreateErrorResponseFromForgeException() {
        var exception = new ForgeException(
                ErrorCategory.VALIDATION,
                ErrorType.RESOURCE_NOT_FOUND,
                "User was not found."
        );
        ResponseEntity<ErrorResponse> response = ErrorResponseHelper.from(exception);
        assertThat(response.getStatusCode().value()).isEqualTo(404);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.httpStatus()).isEqualTo(404);
        assertThat(body.code()).isEqualTo("RESOURCE_NOT_FOUND");
        assertThat(body.message()).isEqualTo("User was not found.");
    }

    @Test
    void shouldCreateInvalidArgumentsResponse() {
        var error = ErrorDetail.builder()
                .field("email")
                .message("Invalid email.")
                .build();
        ResponseEntity<ErrorResponse> response = ErrorResponseHelper.invalidArguments(List.of(error));
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.httpStatus()).isEqualTo(400);
        assertThat(body.code()).isEqualTo("INVALID_ARGUMENT");
        assertThat(body.message()).isEqualTo("An invalid arguments were provided.");
        assertThat(body.errors()).containsExactly(error);
    }

    @Test
    void shouldCreateInvalidRequestResponse() {
        ResponseEntity<ErrorResponse> response = ErrorResponseHelper.invalidRequest(
                "The request body could not be read."
        );
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.httpStatus()).isEqualTo(400);
        assertThat(body.code()).isEqualTo("INVALID_ARGUMENT");
        assertThat(body.message()).isEqualTo("The request body could not be read.");
        assertThat(body.errors()).isEmpty();
    }

    @Test
    void shouldCreateNotFoundResponse() {
        ResponseEntity<ErrorResponse> response = ErrorResponseHelper.notFound();
        assertThat(response.getStatusCode().value()).isEqualTo(404);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.httpStatus()).isEqualTo(404);
        assertThat(body.code()).isEqualTo("RESOURCE_NOT_FOUND");
        assertThat(body.message()).isEqualTo("The requested resource was not found.");
        assertThat(body.errors()).isEmpty();
    }

    @Test
    void shouldCreateMethodNotAllowedResponse() {
        var error = ErrorDetail.builder()
                .message("Request method 'PATCH' is not supported.")
                .addDetail("allowed", List.of("GET", "POST"))
                .build();
        ResponseEntity<ErrorResponse> response = ErrorResponseHelper.methodNotAllowed(error);
        assertThat(response.getStatusCode().value()).isEqualTo(405);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.httpStatus()).isEqualTo(405);
        assertThat(body.code()).isEqualTo("METHOD_NOT_ALLOWED");
        assertThat(body.message()).isEqualTo("The request HTTP method is not supported.");
        assertThat(body.errors()).containsExactly(error);
    }

    @Test
    void shouldCreateInternalServerErrorResponse() {
        ResponseEntity<ErrorResponse> response = ErrorResponseHelper.internalServerError();
        assertThat(response.getStatusCode().value()).isEqualTo(500);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.httpStatus()).isEqualTo(500);
        assertThat(body.code()).isEqualTo("INTERNAL_SERVER_ERROR");
        assertThat(body.message()).isEqualTo("An unexpected error occurred.");
        assertThat(body.errors()).isEmpty();
    }

}
