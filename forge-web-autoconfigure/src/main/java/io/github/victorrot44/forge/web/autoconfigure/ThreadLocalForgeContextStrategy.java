package io.github.victorrot44.forge.web.autoconfigure;

import io.github.victorrot44.forge.web.core.context.ForgeContext;
import io.github.victorrot44.forge.web.core.context.ForgeContextStrategy;

import java.util.Optional;

public class ThreadLocalForgeContextStrategy implements ForgeContextStrategy {

    private final ThreadLocal<ForgeContext> contextHolder = ThreadLocal.withInitial(() -> null);

    @Override
    public void setContext(ForgeContext context) {
        contextHolder.set(context);
    }

    @Override
    public Optional<ForgeContext> getContext() {
        return Optional.ofNullable(contextHolder.get());
    }

    @Override
    public void clearContext() {
        contextHolder.remove();
    }

}
