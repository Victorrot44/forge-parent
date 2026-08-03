package io.github.victorrot44.forge.web.core.response;

public enum Success {

    OK(200, "OK", "Operation completed successfully"),
    CREATED(201, "CREATED", "Resource created successfully"),
    NO_CONTENT(204, "NO_CONTENT", "Operation completed successfully");

    private int httpStatus;
    private String code;
    private String message;

    Success(int httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public int httpStatus() {
        return httpStatus;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

}
