package io.github.victorrot44.forge.web.starter.context;

import io.github.victorrot44.forge.web.core.context.RequestIdResolver;

import java.util.UUID;

public class DefaultRequestIdResolver implements RequestIdResolver {

    @Override
    public String resolve(String requestId) {
        return (requestId != null && !requestId.isEmpty())
                ? requestId
                : UUID.randomUUID().toString();
    }

}
