package io.github.victorrot44.forge.web.core.util;

import java.util.List;

public final class Preconditions {

    private Preconditions() {
        throw new AssertionError("No instances. Util class.");
    }

    public static String requireNotNullOrEmpty(String value) {
        return requireNotNullOrEmpty(value, "value is null or empty");
    }

    public static String requireNotNullOrEmpty(String value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static <T> List<T> requireNotNullOrEmpty(List<T> value) {
        return requireNotNullOrEmpty(value, "value is null or empty");
    }

    public static <T> List<T> requireNotNullOrEmpty(List<T> values, String message) {
        if  (values == null || values.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return values;
    }

    public static <T extends Number> T requirePositiveGreaterZero(T value) {
        return requirePositiveGreaterZero(value, "value is null or less than or equal to 0");
    }

    public static <T extends Number> T requirePositiveGreaterZero(T value, String message) {
        if (value == null || value.doubleValue() <= 0) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

}
