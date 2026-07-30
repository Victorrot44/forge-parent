package io.github.victorrot44.forge.web.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "forge.web")
public record ForgeWebProperties(
        @DefaultValue("true") boolean enabled,
        @DefaultValue Context context
) {

    public record Context(
            @DefaultValue("X-Request-Id") String requestIdHeader,
            @DefaultValue("true") boolean generateRequestId
    ) {}

}
