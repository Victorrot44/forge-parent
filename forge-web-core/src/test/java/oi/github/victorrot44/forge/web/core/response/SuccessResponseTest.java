package oi.github.victorrot44.forge.web.core.response;

import io.github.victorrot44.forge.web.core.response.Pagination;
import io.github.victorrot44.forge.web.core.response.PaginationLinks;
import io.github.victorrot44.forge.web.core.response.SuccessResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class SuccessResponseTest {

    @Test
    void shouldBuildSuccessResponse() {
        SuccessResponse<String> response = SuccessResponse.<String>builder()
                .httpStatus(200)
                .code("USER_FOUND")
                .message("Usuario encontrado.")
                .data("Victor")
                .build();

        assertThat(response.httpStatus()).isEqualTo(200);
        assertThat(response.message()).isEqualTo("Usuario encontrado.");
        assertThat(response.data()).isEqualTo("Victor");
        assertThat(response.code()).isEqualTo("USER_FOUND");
        assertThat(response.timestamp()).isNotNull();
        assertThat(response.metadata()).isNull();
    }

    @Test
    void shouldBuildResponseWithRequestId() {
        SuccessResponse<String> response = SuccessResponse.<String>builder()
                .requestId("request-123")
                .httpStatus(200)
                .code("USER_FOUND")
                .message("Usuario encontrado.")
                .data("Victor")
                .build();
        assertThat(response.requestId()).isEqualTo("request-123");
    }

    @Test
    void shouldBuildResponseWithNullData() {
        SuccessResponse<String> response = SuccessResponse.<String>builder()
                .requestId("request-123")
                .httpStatus(200)
                .code("USER_FOUND")
                .message("Usuario encontrado.")
                .data(null)
                .build();
        assertThat(response.data()).isNull();
    }

    @Test
    void shouldBuildResponseWithPagination() {
        var pagination = new Pagination(
                0,
                10,
                25,
                3,
                new PaginationLinks(
                        "/users?page=0",
                        null,
                        "/users?page=1",
                        "/users?page=2"
                )
        );
        var response = SuccessResponse.<String>builder()
                .httpStatus(200)
                .code("USERS_FOUND")
                .message("Usuarios encontrados.")
                .pagination(pagination)
                .build();

    }

    @Test
    void shouldBuildResponseWithAttributes() {
        var response = SuccessResponse.<String>builder()
                .httpStatus(200)
                .code("USER_FOUND")
                .message("Usuario encontrado.")
                .attribute("source", "database")
                .attribute("cached", true)
                .build();
        assertThat(response.metadata()).isNotNull();
        assertThat(response.metadata().pagination()).isNull();
        assertThat(response.metadata().attributes()).containsEntry("source", "database")
                .containsEntry("cached", true);
    }

    @Test
    void shouldBuildResponseWithPaginationAndAttributes() {
        var pagination = new Pagination( 0, 10, 25, 3, null );
        var response = SuccessResponse.<String>builder()
                .httpStatus(200)
                .code("USERS_FOUND")
                .message("Usuarios encontrados")
                .pagination(pagination)
                .attribute("source", "database")
                .build();
        assertThat(response.metadata()).isNotNull();
        assertThat(response.metadata().pagination()).isEqualTo(pagination);
        assertThat(response.metadata().attributes()).containsEntry("source", "database");
    }

    @Test
    void shouldCreateImmutableMetadataAttributes() {
        var response = SuccessResponse.<String>builder()
                .httpStatus(200) .code("USER_FOUND")
                .message("Usuario encontrado")
                .attribute("source", "database")
                .build();
        assertThatThrownBy(() -> response.metadata().attributes().put("newKey", "value"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldRejectNullCode() {
        assertThatThrownBy(() -> SuccessResponse.<String>builder()
                .httpStatus(200)
                .code(null)
                .message("Usuario encontrado")
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value is null or empty");
    }

    @Test
    void shouldRejectEmptyCode() {
        assertThatThrownBy(() -> SuccessResponse.<String>builder()
                .httpStatus(200)
                .code("")
                .message("Usuario encontrado")
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value is null or empty");
    }

    @Test
    void shouldRejectNullMessage() {
        assertThatThrownBy(() -> SuccessResponse.<String>builder()
                .httpStatus(200)
                .code("USER_FOUND")
                .message(null)
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value is null or empty");
    }

    @Test
    void shouldRejectEmptyMessage() {
        assertThatThrownBy(() -> SuccessResponse.<String>builder()
                .httpStatus(200)
                .code("USER_FOUND")
                .message("")
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value is null or empty");
    }

    @Test
    void shouldRejectInvalidHttpStatus() {
        assertThatThrownBy(() -> SuccessResponse.<String>builder()
                .httpStatus(0)
                .code("USER_FOUND")
                .message("Usuario encontrado")
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value must be greater than or equal to zero.");
    }

    @Test
    void shouldRejectNegativeHttpStatus() {
        assertThatThrownBy(() -> SuccessResponse.<String>builder()
                .httpStatus(-1)
                .code("USER_FOUND")
                .message("Usuario encontrado")
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("value must be greater than or equal to zero.");
    }

}
