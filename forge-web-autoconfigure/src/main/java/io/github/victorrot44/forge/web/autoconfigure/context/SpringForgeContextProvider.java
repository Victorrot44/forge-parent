package io.github.victorrot44.forge.web.autoconfigure.context;

import io.github.victorrot44.forge.web.core.context.ForgeContext;
import io.github.victorrot44.forge.web.core.context.ForgeContextManager;

import java.util.Optional;

public class SpringForgeContextProvider implements ForgeContextManager {

    private static final ThreadLocal<ForgeContext> CONTEXT_HOLDER = new ThreadLocal<>();

    @Override
    public void setContext(ForgeContext context) {
        CONTEXT_HOLDER.set(context);
    }

    @Override
    public void clear() {
        CONTEXT_HOLDER.remove();
    }

    @Override
    public Optional<ForgeContext> currentContext() {
        return Optional.ofNullable(CONTEXT_HOLDER.get());
    }

}
