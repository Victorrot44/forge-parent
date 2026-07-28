package io.github.victorrot44.forge.web.core.response;

import java.util.Map;

public record ApiMetadata(
        Pagination pagination,
        Map<String, Object> attributes
) {
    public ApiMetadata {
        attributes = (attributes == null || attributes.isEmpty())
                ? Map.of()
                : Map.copyOf(attributes);
    }

}
