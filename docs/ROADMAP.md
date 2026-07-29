# Roadmap

> Proyecto: **Forge Web**

Este documento describe la evolución planificada de Forge. Su propósito es mantener una dirección clara para el proyecto y evitar incorporar funcionalidades fuera del alcance de cada versión.

---

# Filosofía

Forge evoluciona por etapas.

Cada versión debe ser estable, pequeña y resolver un conjunto específico de problemas.

No se agregarán nuevas funcionalidades si comprometen la estabilidad o simplicidad del proyecto.

---

# Estado del Proyecto

| Versión | Estado           |
| ------- | ---------------- |
| 1.0     | 🚧 En desarrollo |
| 1.1     | 📋 Planificada   |
| 1.2     | 📋 Planificada   |
| 2.0     | 💡 Visión futura |

---

# Versión 1.0

## Objetivo

Proporcionar una base sólida para aplicaciones Spring Boot con respuestas uniformes, manejo centralizado de excepciones y trazabilidad mediante Request ID.

---

## forge-web-core

### Response API

* ✅ ApiResponse
* ✅ SuccessResponse
* ✅ ErrorResponse
* ✅ ErrorDetail
* ✅ ApiMetadata
* ✅ Pagination
* ✅ PaginationLinks

---

### Error API

* ✅ ErrorDescriptor
* ✅ ErrorCategory
* ✅ ErrorSeverity
* ✅ DefaultErrors

---

### Exceptions

* ✅ ForgeException
* ✅ ValidationException
* ✅ BusinessException
* ✅ AuthenticationException
* ✅ AuthorizationException
* ✅ ConfigurationException
* ✅ ExternalServiceException
* ✅ InternalException

---

### Context

* ✅ ForgeContext
* ✅ ForgeContextProvider

---

### Factories

* ✅ ResponseFactory
* ✅ DefaultResponseFactory

---

### Validation

* ✅ Preconditions
* ✅ ResponseValidations

---

## forge-web-autoconfigure

### Auto Configuration

* AutoConfiguration
* Conditional Beans
* Configuration Properties
* Registro automático de componentes

---

## forge-web-starter

### HTTP

* RequestId Filter
* ForgeContextProvider
* Global Exception Handler
* Respuestas automáticas
* Integración con Jackson

---

### Logging

* MDC
* Correlación mediante Request ID
* Logging uniforme

---

### Configuración

* Properties
* Activación y desactivación de módulos
* Personalización de mensajes

---

## forge-web-test

* Test Builders
* Mock Context
* Assertions
* Helpers

---

## Examples

* CRUD básico
* Paginación
* Validaciones
* Excepciones
* RequestId
* Logging

---

# Versión 1.1

## Objetivo

Incrementar las capacidades de personalización.

### Planeado

* Internacionalización (i18n)
* Mensajes configurables
* Catálogos de errores externos
* Estrategias personalizadas de ResponseFactory
* Extensión del contexto

---

# Versión 1.2

## Objetivo

Mejorar observabilidad e integración.

### Planeado

* Micrometer
* OpenTelemetry
* Métricas HTTP
* Correlation ID distribuido
* Integración con clientes HTTP

---

# Versión 2.0

## Objetivo

Expandir Forge más allá del modelo Servlet.

### Planeado

* Soporte para WebFlux
* Reactor Context
* Contexto reactivo
* Compatibilidad nativa con aplicaciones reactivas

---

# Ideas futuras

Estas funcionalidades son candidatas para futuras versiones, pero aún no forman parte del roadmap oficial.

* Cliente HTTP unificado
* Integración con Problem Details (RFC 9457)
* Starter para GraphQL
* Starter para gRPC
* Documentación OpenAPI automática
* Auditoría distribuida
* Integración con Spring Security
* Sistema de eventos internos
* Observabilidad avanzada
* Soporte para Virtual Threads
* Integración con Kotlin

---

# Criterios para liberar la versión 1.0

Antes de publicar Forge 1.0 deberán cumplirse los siguientes requisitos:

* API pública estable.
* Cobertura de pruebas adecuada.
* Documentación completa.
* Ejemplos funcionales.
* Compatibilidad con las versiones objetivo de Spring Boot.
* Validación de escenarios comunes.
* Publicación en Maven Central.

---

# Principio de evolución

Forge prioriza la estabilidad sobre la velocidad.

Cada nueva funcionalidad deberá aportar un valor claro sin aumentar innecesariamente la complejidad de la librería.
