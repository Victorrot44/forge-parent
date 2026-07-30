package io.github.victorrot44.forge.web.core.context;

import io.github.victorrot44.forge.web.core.exception.ForgeException;

import java.util.Map;

public final class NoForgeContextException extends ForgeException {

    public NoForgeContextException () {
        super("No hay ningún ForgeContext disponible para la ejecución actual.\n" +
                "Asegúrese de haber ejecutado ForgeContextFilter antes de acceder al contexto.", null, null);
    }

    public NoForgeContextException(String message) {
        super(message, null, null);
    }

    public NoForgeContextException(String message, Throwable cause) {
        super(message, cause, null);
    }

}
