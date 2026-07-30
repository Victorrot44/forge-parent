package io.github.victorrot44.forge.web.core.context;

import java.util.Optional;

public interface ForgeContextStrategy {

    void setContext(ForgeContext context);
    Optional<ForgeContext> getContext();
    void clearContext();

    default boolean hasContext() {
        return getContext().isPresent();
    }

    default ForgeContext requireContext() {
        return getContext().orElseThrow(NoForgeContextException::new);
    }

}
