package io.github.victorrot44.forge.web.autoconfigure.fixtures;

import io.github.victorrot44.forge.web.autoconfigure.exception.ForgeWebExceptionHandler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author vrodriguezr
 */
@TestConfiguration(proxyBeanMethods = false)
public class CustomExceptionHandlerConfiguration {
    @Bean
    ForgeWebExceptionHandler forgeWebExceptionHandler() {
        return new ForgeWebExceptionHandler();
    }
}
