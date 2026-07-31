package io.github.victorrot44.forge.web.autoconfigure.sanitize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.victorrot44.forge.web.autoconfigure.ForgeWebProperties;
import io.github.victorrot44.forge.web.core.sanitize.JsonSanitizer;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class DefaultJsonSanitizer implements JsonSanitizer {

    private static final String DEFAULT_REPLACEMENT = "**********";

    private static final Map<String, String> DEFAULT_FIELDS = Map.of(
            "password", DEFAULT_REPLACEMENT,
            "token", DEFAULT_REPLACEMENT,
            "authorization", DEFAULT_REPLACEMENT
    );

    private final ObjectMapper objectMapper;
    private final boolean enabled;
    private final Map<String, String> fields;

    public DefaultJsonSanitizer(ObjectMapper objectMapper, ForgeWebProperties.Sanitizer properties) {
        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper is required");
        Objects.requireNonNull(properties, "sanitizer properties is required");
        this.enabled = properties.enabled();
        this.fields = buildFields(properties.fields());
    }

    @Override
    public String sanitize(String json) {
        if (!enabled || json == null || json.isBlank()) {
            return json;
        }
        try {
            JsonNode root = objectMapper.readTree(json);
            if (root == null) {
                return json;
            }
            sanitizeNode(root);
            return objectMapper.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            return "[invalid-json]";
        }
    }

    private void sanitizeNode(JsonNode node) {
        if (node.isObject()) {
            sanitizeObject((ObjectNode) node);
            return;
        }
        if (node.isArray()) {
            node.forEach(this::sanitizeNode);
        }
    }

    private void sanitizeObject(ObjectNode objectNode) {
        objectNode.fieldNames().forEachRemaining(fieldName -> {
            JsonNode value = objectNode.get(fieldName);
            String replacement = fields.get(normalize(fieldName));
            if (replacement != null) {
                objectNode.put(fieldName, replacement);
                return;
            }
            sanitizeNode(value);
        });
    }

    private Map<String, String> buildFields(Map<String, String> customFields) {
        Map<String, String> result = new HashMap<>();
        DEFAULT_FIELDS.forEach(
                (field, replacement) ->
                        result.put(normalize(field), replacement)
        );
        if (customFields != null) {
            customFields.forEach(
                    (field, replacement) -> {
                        if (field != null && replacement != null) {
                            result.put(normalize(field), replacement);
                        }
                    }
            );
        }
        return Map.copyOf(result);
    }

    private String normalize(String fieldName) {
        return fieldName.toLowerCase(Locale.ROOT);
    }

}
