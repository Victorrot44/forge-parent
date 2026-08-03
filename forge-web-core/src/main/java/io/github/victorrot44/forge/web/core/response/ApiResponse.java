package io.github.victorrot44.forge.web.core.response;

public final class ApiResponse {

    private ApiResponse() {
    }

    public static <T> SuccessResponse.Builder<T> success() {
        return success(Success.OK);
    }

    public static <T> SuccessResponse.Builder<T> success(Success success) {
        return SuccessResponse.<T>builder()
                .httpStatus(success.httpStatus())
                .code(success.code())
                .message(success.message());
    }

    public static ErrorResponse.Builder error() {
        return ErrorResponse.builder();
    }

}