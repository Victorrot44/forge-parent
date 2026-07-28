package io.github.victorrot44.forge.web.core.response;

public record ErrorDetail(
        String code,
        String location,
        String field,
        String message,
        Object rejectedValue
) {

}
