package io.github.victorrot44.forge.web.core.response;

import io.github.victorrot44.forge.web.core.response.builder.AbstractResponseBuilder;
import io.github.victorrot44.forge.web.core.response.validator.ResponseValidator;
import io.github.victorrot44.forge.web.core.util.Preconditions;

import java.time.Instant;
import java.util.*;

public record ErrorResponse(
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

        public Builder errors(List<ErrorDetail> errors) {
            this.errors.addAll(errors);
            return this;
        }

        public ErrorResponse build() {
            validate();
            return new ErrorResponse(timestamp, httpStatus, code, message, errors, buildMetadata(null));
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    public ErrorResponse {
        errors = Preconditions.immutableList(errors);
        ResponseValidator.validateErrorResponse(message, code, httpStatus);
    }

}
