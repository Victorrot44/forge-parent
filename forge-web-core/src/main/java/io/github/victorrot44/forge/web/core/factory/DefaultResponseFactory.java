package io.github.victorrot44.forge.web.core.factory;

import io.github.victorrot44.forge.web.core.context.ForgeContext;
import io.github.victorrot44.forge.web.core.context.ForgeContextProvider;
import io.github.victorrot44.forge.web.core.error.ErrorDescriptor;
import io.github.victorrot44.forge.web.core.exception.response.ForgeResponseException;
import io.github.victorrot44.forge.web.core.response.ErrorResponse;
import io.github.victorrot44.forge.web.core.response.SuccessResponse;

import java.util.Objects;

public final class DefaultResponseFactory implements ResponseFactory {

    private final ForgeContextProvider contextProvider;

    public DefaultResponseFactory(ForgeContextProvider contextProvider) {
        this.contextProvider = Objects.requireNonNull(
                contextProvider,
                "ForgeContextProvider must not be null."
        );
    }

    private ForgeContext currentContext() {
        return contextProvider.requireContext();
    }

    private <T> SuccessResponse.Builder<T> successBuilder(ResponseDescriptor descriptor) {
        return successBuilder(descriptor.httpStatus(), descriptor.code(), descriptor.message());
    }

    private <T> SuccessResponse.Builder<T> successBuilder(int status, String code, String message) {
        ForgeContext context = currentContext();
        SuccessResponse.Builder<T> builder = SuccessResponse.<T>builder()
                .requestId(context.requestId())
                .httpStatus(status)
                .code(code)
                .message(message);
        return builder;
    }

    private ErrorResponse.Builder errorBuilder(ErrorDescriptor descriptor) {
        return errorBuilder(descriptor.httpStatus(), descriptor.code(), descriptor.message());
    }

    private ErrorResponse.Builder errorBuilder(int status, String code, String message) {
        ForgeContext context = currentContext();
        ErrorResponse.Builder builder = ErrorResponse.builder();
        builder.requestId(context.requestId())
                .httpStatus(status)
                .code(code)
                .message(message);
        return builder;
    }

    @Override
    public <T> SuccessResponse<T> ok(T data) {
        SuccessResponse.Builder<T> builder = successBuilder(DefaultResponses.SUCCESS);
        return builder.data(data).build();
    }

    @Override
    public <T> SuccessResponse<T> ok(T data, String message) {
        SuccessResponse.Builder<T> builder = successBuilder(DefaultResponses.SUCCESS);
        return builder.data(data).message(message).build();
    }

    @Override
    public <T> SuccessResponse<T> ok(T data, String code, String message) {
        SuccessResponse.Builder<T> builder = successBuilder(DefaultResponses.SUCCESS);
        return builder.data(data).code(code).message(message).build();
    }

    @Override
    public <T> SuccessResponse<T> created(T data) {
        SuccessResponse.Builder<T> builder = successBuilder(DefaultResponses.CREATED);
        return builder.data(data).build();
    }

    @Override
    public <T> SuccessResponse<T> created(T data, String message) {
        SuccessResponse.Builder<T> builder = successBuilder(DefaultResponses.CREATED);
        return builder.data(data).message(message).build();
    }

    @Override
    public <T> SuccessResponse<T> created(T data, String code, String message) {
        SuccessResponse.Builder<T> builder = successBuilder(DefaultResponses.CREATED);
        return builder.data(data).code(code).message(message).build();
    }

    @Override
    public SuccessResponse<Void> noContent() {
        SuccessResponse.Builder<Void> builder = successBuilder(DefaultResponses.NO_CONTENT);
        return builder.build();
    }

    @Override
    public ErrorResponse error(ForgeResponseException exception) {
        ErrorResponse.Builder builder = errorBuilder(exception.getErrorDescriptor());
        exception.getDetails().forEach(builder::attribute);
        return builder.build();
    }

    @Override
    public ErrorResponse error(ErrorDescriptor errorDescriptor) {
        return errorBuilder(errorDescriptor).build();
    }

    @Override
    public ErrorResponse error(ErrorDescriptor errorDescriptor, String message) {
        return errorBuilder(errorDescriptor).message(message).build();
    }

    @Override
    public ErrorResponse error(ErrorDescriptor errorDescriptor, String code, String message) {
        return errorBuilder(errorDescriptor).message(message).code(code).build();
    }

}
