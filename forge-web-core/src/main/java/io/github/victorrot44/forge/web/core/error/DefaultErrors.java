package io.github.victorrot44.forge.web.core.error;

public enum DefaultErrors implements ErrorDescriptor {

    BAD_REQUEST("FRG-400-001", 400, "The request is invalid", ErrorCategory.VALIDATION, ErrorSeverity.WARNING),
    UNAUTHORIZED("FRG-401-001", 401, "Unauthorized", ErrorCategory.SECURITY, ErrorSeverity.WARNING),
    FORBIDDEN("FRG-403-001", 403, "Forbidden", ErrorCategory.SECURITY, ErrorSeverity.WARNING),
    NOT_FOUND("FRG-404-001", 404, "Resource not found", ErrorCategory.BUSINESS, ErrorSeverity.ERROR),
    METHOD_NOT_ALLOWED("FRG-405-001", 405, "Method Not Allowed", ErrorCategory.CONFIGURATION, ErrorSeverity.ERROR),
    NOT_ACCEPTABLE("FRG-406-001", 406, "Not Acceptable", ErrorCategory.VALIDATION, ErrorSeverity.ERROR),
    REQUEST_TIMEOUT("FRG-408-001", 408, "Request Timeout", ErrorCategory.SYSTEM, ErrorSeverity.ERROR),
    CONFLICT("FRG-409-001", 409, "Conflict", ErrorCategory.BUSINESS, ErrorSeverity.WARNING),
    GONE("FRG-410-001", 410, "Gone", ErrorCategory.BUSINESS, ErrorSeverity.INFO),
    PRECONDITION_FAILED("FRG-412-001", 412, "Precondition Failed", ErrorCategory.VALIDATION, ErrorSeverity.ERROR),
    PAYLOAD_TOO_LARGE("FRG-413-001", 413,  "Payload Too Large", ErrorCategory.VALIDATION, ErrorSeverity.WARNING),
    UNSUPPORTED_MEDIA_TYPE("FRG-415-001", 415, "Unsupported Media Type", ErrorCategory.VALIDATION, ErrorSeverity.WARNING),
    UNPROCESSABLE_ENTITY("FRG-422-001", 422, "Unprocessable Entity", ErrorCategory.VALIDATION, ErrorSeverity.ERROR),
    TOO_MANY_REQUESTS("FRG-429-001", 429, "Too Many Requests", ErrorCategory.EXTERNAL, ErrorSeverity.WARNING),

    INTERNAL_SERVER_ERROR("FRG-500-001", 500, "Internal Server Error", ErrorCategory.SYSTEM, ErrorSeverity.ERROR),
    NOT_IMPLEMENTED("FRG-501-001", 501, "Not Implemented", ErrorCategory.SYSTEM, ErrorSeverity.ERROR),
    BAD_GATEWAY("FRG-502-001", 502, "Bad Gateway", ErrorCategory.EXTERNAL, ErrorSeverity.ERROR),
    GATEWAY_TIMEOUT("FRG-504-001", 504, "Gateway Timeout", ErrorCategory.EXTERNAL, ErrorSeverity.ERROR);

    private final String code;
    private final String message;
    private final int  httpStatus;
    private final ErrorCategory category;
    private final ErrorSeverity severity;

    DefaultErrors(String code, int httpStatus, String message, ErrorCategory category, ErrorSeverity severity) {
        this.code = code;
        this.message = message;
        this.category = category;
        this.severity = severity;
        this.httpStatus = httpStatus;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public int httpStatus() {
        return httpStatus;
    }

    @Override
    public ErrorCategory category() {
        return category;
    }

    @Override
    public ErrorSeverity severity() {
        return severity;
    }

    @Override
    public String toString() {
        return "%s (%s): %s".formatted(code(), severity(), message());
    }

}
