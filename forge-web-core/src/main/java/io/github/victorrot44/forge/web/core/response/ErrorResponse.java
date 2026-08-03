package io.github.victorrot44.forge.web.core.response;

import io.github.victorrot44.forge.web.core.response.builder.AbstractResponseBuilder;
import io.github.victorrot44.forge.web.core.util.Preconditions;
import io.github.victorrot44.forge.web.core.response.validator.ResponseValidator;

import java.time.Instant;
import java.util.*;

public record ErrorResponse(
        String requestId,
        Instant timestamp,
        int httpStatus,
        String code,
        String message,
        List<ErrorDetail> errors,
        ApiMetadata metadata
) {

    public static final class Builder extends AbstractResponseBuilder<Builder> {

        private final List<ErrorDetail> errors = new ArrayList<>();

        @Override
        protected Builder self() {
            return this;
        }

        public Builder addError(ErrorDetail errorDetail) {
            this.errors.add(errorDetail);
            return this;
        }

        public Builder addError(ErrorDetail.Builder errorDetailBuilder) {
            this.errors.add(errorDetailBuilder.build());
            return this;
        }

        public ErrorResponse build() {
            validate();
            return new ErrorResponse(requestId, timestamp, httpStatus, code, message, errors, buildMetadata(null));
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    public ErrorResponse {
        errors = (errors == null) ? List.of() : List.copyOf(errors);
        ResponseValidator.validateErrorResponse(this);
    }

}
