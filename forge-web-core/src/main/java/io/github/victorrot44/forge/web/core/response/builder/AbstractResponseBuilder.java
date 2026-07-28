package io.github.victorrot44.forge.web.core.response.builder;

import io.github.victorrot44.forge.web.core.response.ApiMetadata;
import io.github.victorrot44.forge.web.core.response.Pagination;
import io.github.victorrot44.forge.web.core.util.Preconditions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractResponseBuilder<T extends AbstractResponseBuilder<T>> {

    protected String requestId;
    protected final Instant timestamp;
    protected int httpStatus;
    protected String code;
    protected String message;
    protected final Map<String, Object> attributes;

    protected AbstractResponseBuilder() {
        this.timestamp = Instant.now();
        this.attributes = new HashMap<>();
    }

    protected abstract T self();

    public T requestId(String requestId) {
        this.requestId = requestId;
        return self();
    }

    public T httpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
        return self();
    }

    public T code(String code) {
        this.code = code;
        return self();
    }

    public T message(String message) {
        this.message = message;
        return self();
    }

    public T attribute(String key, Object value) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("key is null or empty");
        }
        this.attributes.put(key, value);
        return self();
    }

    protected ApiMetadata buildMetadata(Pagination pagination) {
        return (pagination != null || !attributes.isEmpty())
                ? new ApiMetadata(pagination, attributes)
                : null;
    }

    protected void validate() {
        Preconditions.requireNotNullOrEmpty(this.code);
        Preconditions.requireNotNullOrEmpty(this.message);
        Preconditions.requireNotNullOrEmpty(this.requestId);
        Preconditions.requirePositiveGreaterZero(this.httpStatus);
    }

}
