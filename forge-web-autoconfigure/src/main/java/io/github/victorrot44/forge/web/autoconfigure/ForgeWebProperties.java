package io.github.victorrot44.forge.web.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "forge.web")
public record ForgeWebProperties(
        @DefaultValue("true") boolean enabled,
        @DefaultValue Context context,
        @DefaultValue Logging logging,
        @DefaultValue Sanitizer sanitizer
) {

    public record Context(
            @DefaultValue("X-Request-Id") String requestIdHeader
    ) {}

    public record Logging(
            @DefaultValue("true") boolean enabled,
            @DefaultValue("2000") int maxTextBodyLength,
            List<String> excludedUris
    ) {}

    public record Sanitizer(
            @DefaultValue("true") boolean enabled,
            Map<String, String> fields
    ) {}

}
