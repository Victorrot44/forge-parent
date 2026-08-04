package io.github.victorrot44.forge.web.core.util;

import java.util.Map;

public final class Preconditions {

    private Preconditions() {
        throw new AssertionError("No instances. Util class.");
    }

    public static <K, V> Map<K, V> immutableMap(Map<K, V> map) {
        return (map == null) ? Map.of() : Map.copyOf(map);
    }

    public static String requireNotNullOrEmpty(String value) {
        return requireNotNullOrEmpty(value, "value is null or empty");
    }

    public static String requireNotNullOrEmpty(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static <T extends Number> T requirePositive(T value) {
        return requirePositive(value, "value must be greater than or equal to zero.");
    }

    public static <T extends Number> T requirePositive(T value, String message) {
        if (value == null || value.intValue() <= 0) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

}
