package io.github.victorrot44.forge.web.core.error;

public enum ErrorCategory {
    VALIDATION,     // Datos inválidos, reglas de validación de entrada.
    BUSINESS,       // Regla de negocio incumplida.
    SECURITY,       // Problemas relacionados con autenticación/autorización y seguridad.
    EXTERNAL,       // Dependencias externas: API, servicio, proveedor, etc.
    SYSTEM,         // Error interno no atribuible a la solicitud.
    CONFIGURATION;  // Configuración incorrecta o incompleta.
}
