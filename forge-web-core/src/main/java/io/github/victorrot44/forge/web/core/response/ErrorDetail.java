package io.github.victorrot44.forge.web.core.response;

import java.util.HashMap;
import java.util.Map;

public record ErrorDetail(
        String code,
        String field,
        String message,
        Map<String, Object> details
) {

    public final static class Builder {
        private String code;
        private String field;
        private String message;
        private Map<String, Object> details = new HashMap<>();

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder addDetail(String key, Object value) {
            this.details.put(key, value);
            return this;
        }

        public ErrorDetail build() {
            return new ErrorDetail(code, field, message, details.isEmpty() ? Map.of() : Map.copyOf(details));
        }

    }

    public ErrorDetail {
        details = details == null
                ? Map.of()
                : Map.copyOf(details);
    }

    public static Builder builder() {
        return new Builder();
    }

}
