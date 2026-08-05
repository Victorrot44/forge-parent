package oi.github.victorrot44.forge.web.core.response;

import io.github.victorrot44.forge.web.core.response.ApiResponse;
import io.github.victorrot44.forge.web.core.response.ErrorResponse;
import io.github.victorrot44.forge.web.core.response.Success;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author vrodriguezr
 */
public class ApiResponseTest {
    
    @Test
    void shouldCreateSuccessResponseWithDefaultSuccess() {
        var response = ApiResponse.<String>success()
                .data("test")
                .build();
        assertThat(response.httpStatus()).isEqualTo(Success.OK.httpStatus());
        assertThat(response.code()).isEqualTo(Success.OK.code());
        assertThat(response.message()).isEqualTo(Success.OK.message());
        assertThat(response.data()).isEqualTo("test");
    }

    @Test
    void shouldCreateSuccessResponseFromSuccess() {
        var success = Success.CREATED;
        var response = ApiResponse.<String>success(success)
                .data("test")
                .build();
        assertThat(response.httpStatus()).isEqualTo(success.httpStatus());
        assertThat(response.code()).isEqualTo(success.code());
        assertThat(response.message()).isEqualTo(success.message());
        assertThat(response.data()).isEqualTo("test");
    }

    @Test
    void shouldCreateErrorResponseBuilder() {
        var builder = ApiResponse.error();
        assertThat(builder).isInstanceOf(ErrorResponse.Builder.class);
    }
    
}
