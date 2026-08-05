package io.github.victorrot44.forge.web.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.victorrot44.forge.web.autoconfigure.exception.ForgeWebExceptionHandler;
import io.github.victorrot44.forge.web.autoconfigure.fixtures.CustomExceptionHandlerConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

public class ForgeWebAutoConfigurationTest {

    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
            .withConfiguration(
                    AutoConfigurations.of(ForgeWebAutoConfiguration.class)
            );

    @Test
    public void shouldRegisterExceptionHandlerByDefault() {
        this.contextRunner.run((context) -> {
            assertThat(context).hasSingleBean(ForgeWebExceptionHandler.class);
        });
    }
    
    @Test
    void shouldNotRegisterExceptionHandlerWhenForgeWebIsDisabled() {
        contextRunner
                .withPropertyValues("forge.web.enabled=false")
                .run(context -> {
                    assertThat(context)
                            .doesNotHaveBean(ForgeWebExceptionHandler.class);
                });
    }
    
    @Test
    void shouldNotRegisterExceptionHandlerWhenExceptionHandlerIsDisabled() {
        contextRunner
                .withPropertyValues("forge.web.exception-handler=false")
                .run(context -> {
                    assertThat(context)
                            .doesNotHaveBean(ForgeWebExceptionHandler.class);
                });
    }
    
    @Test
    void shouldBackOffWhenExceptionHandlerAlreadyExists() {
        contextRunner
                .withUserConfiguration(CustomExceptionHandlerConfiguration.class)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(ForgeWebExceptionHandler.class);
                });
    }

}
