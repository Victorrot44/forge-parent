package io.github.victorrot44.forge.web.autoconfigure;

import io.github.victorrot44.forge.web.autoconfigure.exception.ForgeWebExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnProperty(
        prefix = "forge.web",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@ConditionalOnWebApplication
@EnableConfigurationProperties(ForgeWebProperties.class)
public class ForgeWebAutoConfiguration {

    @Bean
    @ConditionalOnProperty(
            prefix = "forge.web",
            name = "exception-handler",
            havingValue = "true",
            matchIfMissing = true
    )
    @ConditionalOnMissingBean(ForgeWebExceptionHandler.class)
    ForgeWebExceptionHandler forgeWebExceptionHandler() {
        return new ForgeWebExceptionHandler();
    }

}
