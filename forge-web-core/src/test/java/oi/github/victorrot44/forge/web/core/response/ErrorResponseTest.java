package oi.github.victorrot44.forge.web.core.response;

import io.github.victorrot44.forge.web.core.response.ErrorDetail;
import io.github.victorrot44.forge.web.core.response.ErrorResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

public class ErrorResponseTest {

    @Test
    void shouldBuildErrorResponse() {
        var error = ErrorResponse.builder()
                .httpStatus(404)
                .code("USER_NOT_FOUND")
                .message("Usuario no encontrado.")
                .build();
        assertThat(error.httpStatus()).isEqualTo(404);
        assertThat(error.code()).isEqualTo("USER_NOT_FOUND");
        assertThat(error.message()).isEqualTo("Usuario no encontrado.");
        assertThat(error.errors()).isEmpty();
        assertThat(error.metadata()).isNull();
        assertThat(error.requestId()).isNull();
        assertThat(error.timestamp()).isNotNull();
    }

    @Test
    void shouldBuildErrorWithRequestId() {
        var error = ErrorResponse.builder()
                .httpStatus(404)
                .code("USER_NOT_FOUND")
                .message("Usuario no encontrado.")
                .requestId("request-123")
                .build();
        assertThat(error.requestId()).isEqualTo("request-123");
    }

    @Test
    void shouldBuildErrorWithErrors() {
        var errorDetail = ErrorDetail.builder()
                .code("ADEA-404-001")
                .addDetail("user", "Jose")
                .build();
        var error = ErrorResponse.builder()
                .httpStatus(404)
                .code("USER_NOT_FOUND")
                .message("Usuario no encontrado.")
                .addError(errorDetail)
                .build();
        assertThat(error.errors()).isNotEmpty();
    }

    @Test
    void shouldBuildErrorWithMetadata() {
        var error = ErrorResponse.builder()
                .httpStatus(400)
                .code("BAD_REQUEST")
                .message("Datos Incorrectos.")
                .addError(
                        ErrorDetail.builder()
                                .code("ADEA-400-001")
                                .message("Campo obligatorio.")
                                .field("name")
                )
                .addError(
                        ErrorDetail.builder()
                                .code("ADEA-400-002")
                                .message("Correo electrónico invalido.")
                                .field("email")
                )
                .addError(
                        ErrorDetail.builder()
                                .code("ADEA-400-003")
                                .message("El usuario debe de ser único.")
                                .field("username")
                )
                .attribute("localed", "web")
                .attribute("errors", 3)
                .build();
        assertThat(error.metadata()).isNotNull();
        assertThat(error.metadata().attributes()).containsEntry("localed", "web")
                .containsEntry("errors", 3);
        assertThat(error.metadata().pagination()).isNull();
    }

    @Test
    void shouldRejectThrownBy() {
        assertThatThrownBy(() -> ErrorResponse.builder()
                    .httpStatus(400)
                    .code(null)
                    .message("Datos Incorrectos.")
                    .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value is null or empty");
    }

    @Test
    void shouldRejectEmptyCode() {
        assertThatThrownBy(() -> {
            ErrorResponse.builder()
                    .httpStatus(400)
                    .code("")
                    .message("Datos Incorrectos.")
                    .build();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value is null or empty");
    }

    @Test
    void shouldRejectNullMessage() {
        assertThatThrownBy(() ->
            ErrorResponse.builder()
                    .httpStatus(400)
                    .code("BAD_REQUEST")
                    .message(null)
                    .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value is null or empty");
    }

    @Test
    void shouldRejectEmptyMessage() {
        assertThatThrownBy(() ->
            ErrorResponse.builder()
                    .httpStatus(400)
                    .code("BAD_REQUEST")
                    .message("")
                    .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value is null or empty");
    }

    @Test
    void shouldRejectNegativeHttpStatus() {
        assertThatThrownBy(() -> ErrorResponse.builder()
                    .httpStatus(-1)
                    .code("BAD_REQUEST")
                    .message("Valor incorrecto.")
                    .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value must be greater than or equal to zero.");
    }

}
