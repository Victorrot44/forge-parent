package io.github.victorrot44.forge.web.core.factory;

public enum DefaultResponses implements ResponseDescriptor {

    SUCCESS(200, "SUCCESS", "Operation completed successfully."),
    CREATED(201, "CREATED", "Resource created successfully."),
    ACCEPTED(202, "ACCEPTED", "Resource accepted successfully."),
    NO_CONTENT(204, "NO_CONTENT", "Resource no content successfully.");

    private final int httpStatus;
    private final String code;
    private final String message;

    private DefaultResponses(int httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public int httpStatus() {
        return httpStatus;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}
