package io.github.victorrot44.forge.web.core.response.validator;

import io.github.victorrot44.forge.web.core.response.ErrorResponse;
import io.github.victorrot44.forge.web.core.response.SuccessResponse;
import io.github.victorrot44.forge.web.core.util.Preconditions;

public final class ResponseValidator {

    private ResponseValidator() {
        throw new AssertionError("No instances. Utility class.");
    }

    public static void validateSuccessResponse(SuccessResponse response) {
        Preconditions.requireNotNullOrEmpty(response.message());
        Preconditions.requireNotNullOrEmpty(response.code());
        Preconditions.requirePositiveGreaterZero(response.httpStatus());
    }

    public static void validateErrorResponse(ErrorResponse response) {
        Preconditions.requireNotNullOrEmpty(response.message());
        Preconditions.requireNotNullOrEmpty(response.code());
        Preconditions.requirePositiveGreaterZero(response.httpStatus());    }

}
