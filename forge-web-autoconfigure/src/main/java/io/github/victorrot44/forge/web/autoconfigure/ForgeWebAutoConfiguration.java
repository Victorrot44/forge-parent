package io.github.victorrot44.forge.web.autoconfigure;

import io.github.victorrot44.forge.web.core.context.ForgeContextProvider;
import io.github.victorrot44.forge.web.core.factory.DefaultResponseFactory;
import io.github.victorrot44.forge.web.core.factory.ResponseFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(ForgeWebProperties.class)
@ConditionalOnProperty(
        prefix = "forge.web",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class ForgeWebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    ForgeContextProvider forgeContextProvider() {
        return new SpringForgeContextProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    ResponseFactory responseFactory(ForgeContextProvider contextProvider) {
        return new DefaultResponseFactory(contextProvider);
    }

}
