package io.github.victorrot44.forge.web.core.context;

import java.util.Optional;

public interface ForgeContextProvider {

    Optional<ForgeContext> currentContext();

    default ForgeContext requireContext() {
        return currentContext().orElseThrow(() -> new IllegalStateException("No ForgeContext available"));
    }

}
