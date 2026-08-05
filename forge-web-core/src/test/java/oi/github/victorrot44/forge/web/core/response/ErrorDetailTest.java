package oi.github.victorrot44.forge.web.core.response;

import io.github.victorrot44.forge.web.core.response.ErrorDetail;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 *
 * @author vrodriguezr
 */
public class ErrorDetailTest {
    
    @Test
    void shouldBuildErrorDetail() {
        var error = ErrorDetail.builder()
                .code("INVALID_EMAIL")
                .field("email")
                .message("Invalid email address.")
                .build();
        assertThat(error.code()).isEqualTo("INVALID_EMAIL");
        assertThat(error.field()).isEqualTo("email");
        assertThat(error.message()).isEqualTo("Invalid email address.");
        assertThat(error.details()).isEmpty();
    }
    
    @Test
    void shouldAddDetails() {
        var error = ErrorDetail.builder()
                .field("age")
                .message("Invalid age.")
                .addDetail("expectedType", "Integer")
                .addDetail("minimum", 18)
                .build();
        assertThat(error.details())
                .containsEntry("expectedType", "Integer")
                .containsEntry("minimum", 18);
    }
    
    @Test
    void shouldCreateDefensiveCopyOfDetails() {
        var details = new java.util.HashMap<String, Object>();
        details.put("expectedType", "Integer");
        var error = new ErrorDetail(
                null,
                "age",
                "Invalid age.",
                details
        );
        details.put("expectedType", "Long");
        details.put("another", "value");
        assertThat(error.details())
                .containsEntry("expectedType", "Integer")
                .doesNotContainKey("another");
    }
    
    @Test
    void shouldExposeImmutableDetails() {
        var error = ErrorDetail.builder()
                .addDetail("expectedType", "Integer")
                .build();
        assertThatThrownBy(() -> error.details().put("another", "value"))
                .isInstanceOf(UnsupportedOperationException.class);
    }
    
}
