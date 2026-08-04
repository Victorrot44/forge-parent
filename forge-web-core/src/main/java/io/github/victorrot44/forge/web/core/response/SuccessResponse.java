package io.github.victorrot44.forge.web.core.response;

import io.github.victorrot44.forge.web.core.response.builder.AbstractResponseBuilder;
import io.github.victorrot44.forge.web.core.response.validator.ResponseValidator;

import java.time.Instant;

public record SuccessResponse<T>(
        String requestId,
        Instant timestamp,
        int httpStatus,
        String code,
        String message,
        T data,
        ApiMetadata metadata
) {

    public static final class Builder<T> extends AbstractResponseBuilder<Builder<T>> {

        private T data;
        private Pagination pagination;

        @Override
        protected Builder<T> self() {
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> pagination(Pagination pagination) {
            this.pagination = pagination;
            return this;
        }

        public SuccessResponse<T> build() {
            validate();
            return new SuccessResponse<>(requestId, timestamp, httpStatus, code, message, data, buildMetadata(pagination));
        }

    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public SuccessResponse {
        ResponseValidator.validateSuccessResponse(message, code, httpStatus);
    }

}
