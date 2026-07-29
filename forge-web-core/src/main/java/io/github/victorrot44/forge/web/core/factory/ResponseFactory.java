package io.github.victorrot44.forge.web.core.factory;

import io.github.victorrot44.forge.web.core.error.ErrorDescriptor;
import io.github.victorrot44.forge.web.core.exception.ForgeException;
import io.github.victorrot44.forge.web.core.response.ErrorResponse;
import io.github.victorrot44.forge.web.core.response.SuccessResponse;

public interface ResponseFactory {

    <T> SuccessResponse<T> ok(T data);
    <T> SuccessResponse<T> ok(T data, String message);
    <T> SuccessResponse<T> ok(T data, String code, String message);

    <T> SuccessResponse<T> created(T data);
    <T> SuccessResponse<T> created(T data, String message);
    <T> SuccessResponse<T> created(T data, String code, String message);

    SuccessResponse<Void> noContent();

    ErrorResponse error(ForgeException exception);

    ErrorResponse error(ErrorDescriptor errorDescriptor);
    ErrorResponse error(ErrorDescriptor errorDescriptor, String message);
    ErrorResponse error(ErrorDescriptor errorDescriptor, String code, String message);

}
