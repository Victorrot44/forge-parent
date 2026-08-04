package io.github.victorrot44.forge.web.core.response.validator;

import io.github.victorrot44.forge.web.core.response.ErrorResponse;
import io.github.victorrot44.forge.web.core.response.SuccessResponse;
import io.github.victorrot44.forge.web.core.util.Preconditions;

public final class ResponseValidator {

    private ResponseValidator() {
        throw new AssertionError("No instances. Utility class.");
    }

    public static void validateSuccessResponse(String message, String code, int httpStatus) {
        Preconditions.requireNotNullOrEmpty(message);
        Preconditions.requireNotNullOrEmpty(code);
        Preconditions.requirePositive(httpStatus);
    }

    public static void validateErrorResponse(String message, String code, int httpStatus) {
        Preconditions.requireNotNullOrEmpty(message);
        Preconditions.requireNotNullOrEmpty(code);
        Preconditions.requirePositive(httpStatus);
    }

}
