package io.github.victorrot44.forge.web.core.context;

import java.util.Objects;
import java.util.Optional;

public final class ForgeContextHolder {

    private static ForgeContextStrategy strategy;

    private ForgeContextHolder() {
        throw new AssertionError("Utility class should not be instantiated.");
    }

    public static synchronized void initialize(ForgeContextStrategy strategy) {
        Objects.requireNonNull(strategy, "strategy cannot be null.");

        if (ForgeContextHolder.strategy != null) {
            throw new IllegalStateException("ForgeContextHolder has already been initialized.");
        }

        ForgeContextHolder.strategy = strategy;
    }

    public static void setContext(ForgeContext context) {
        requireStrategy().setContext(context);
    }

    public static Optional<ForgeContext> getContext() {
        return requireStrategy().getContext();
    }

    public static ForgeContext requireContext() {
        return requireStrategy().requireContext();
    }

    public static boolean hasContext() {
        return requireStrategy().hasContext();
    }

    public static void clearContext() {
        requireStrategy().clearContext();
    }

    private static ForgeContextStrategy requireStrategy() {
        return Objects.requireNonNull(strategy, "ForgeContextHolder has not been initialized.");
    }

}
