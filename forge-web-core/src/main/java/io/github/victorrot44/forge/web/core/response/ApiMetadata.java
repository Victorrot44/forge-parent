package io.github.victorrot44.forge.web.core.response;

import io.github.victorrot44.forge.web.core.util.Preconditions;
import java.util.Map;

public record ApiMetadata(
        Pagination pagination,
        Map<String, Object> attributes
) {
    public ApiMetadata {
        attributes = Preconditions.immutableMap(attributes);
    }

}
