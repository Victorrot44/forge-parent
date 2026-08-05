package io.github.victorrot44.forge.web.autoconfigure.exception;

import io.github.victorrot44.forge.web.core.error.ErrorType;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.http.HttpStatus;

/**
 *
 * @author vrodriguezr
 */
public class ErrorTypeStatusMapperTest {
    
    @Test
    void shouldMapErrorTypesToHttpStatus() {
        assertThat(ErrorTypeStatusMapper.resolve(ErrorType.INVALID_ARGUMENT))
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ErrorTypeStatusMapper.resolve(ErrorType.RESOURCE_NOT_FOUND))
                .isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(ErrorTypeStatusMapper.resolve(ErrorType.CONFLICT))
                .isEqualTo(HttpStatus.CONFLICT);
        assertThat(ErrorTypeStatusMapper.resolve(ErrorType.AUTHENTICATION_REQUIRED))
                .isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(ErrorTypeStatusMapper.resolve(ErrorType.ACCESS_DENIED))
                .isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(ErrorTypeStatusMapper.resolve(ErrorType.EXTERNAL_SERVER_ERROR))
                .isEqualTo(HttpStatus.BAD_GATEWAY);
        assertThat(ErrorTypeStatusMapper.resolve(ErrorType.INTERNAL_SERVER_ERROR))
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(ErrorTypeStatusMapper.resolve(ErrorType.TIMEOUT))
                .isEqualTo(HttpStatus.GATEWAY_TIMEOUT);
    }
    
}
