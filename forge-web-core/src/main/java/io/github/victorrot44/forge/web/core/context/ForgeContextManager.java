package io.github.victorrot44.forge.web.core.context;

public interface ForgeContextManager extends ForgeContextProvider {
    void setContext(ForgeContext context);
    void clear();
}