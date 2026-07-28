package io.github.victorrot44.forge.web.core.response;

import java.time.Instant;

public sealed interface ApiResponse permits SuccessResponse, ErrorResponse {
    String requestId();
    Instant timestamp();
    int httpStatus();
    String code();
    String message();
}
