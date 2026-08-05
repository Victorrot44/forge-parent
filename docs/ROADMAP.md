# Roadmap

> **Proyecto:** Forge Web

Este documento describe la evolución planificada de Forge.

El roadmap representa una dirección de desarrollo y no constituye un compromiso de implementación. Las funcionalidades pueden cambiar, dividirse, posponerse o eliminarse cuando exista una justificación técnica.

Forge evoluciona de forma incremental: cada versión debe resolver problemas concretos sin introducir complejidad innecesaria.

---

# Estado del proyecto

| Versión | Estado           |
| ------- | ---------------- |
| 1.0.0   | ✅ Base estable   |
| 1.1.x   | 📋 Planificada   |
| 1.2.x   | 💡 Candidata     |
| 2.x     | 💡 Visión futura |

---

# Versión 1.0.0

## Estado

**Completada.**

## Objetivo

Establecer los cimientos de Forge mediante:

* modelos estandarizados de respuesta;
* construcción sencilla de respuestas;
* manejo centralizado de excepciones Spring MVC;
* catálogo básico de tipos de error;
* validaciones del contrato de respuesta;
* integración automática con Spring Boot;
* arquitectura modular;
* Core independiente de Spring.

---

## forge-web-core

### Response API

* ✅ `ApiResponse`
* ✅ `SuccessResponse`
* ✅ `ErrorResponse`
* ✅ `ErrorDetail`
* ✅ `ApiMetadata`
* ✅ `Pagination`
* ✅ `PaginationLinks`

### Error API

* ✅ `ErrorType`

### Exceptions

* ✅ `ForgeException`
* ✅ `ForgeInternalException`

### Builders

* ✅ Builders para respuestas
* ✅ Construcción fluida mediante `ApiResponse`

### Validation

* ✅ `Preconditions`
* ✅ `ResponseValidator`

---

## forge-web-autoconfigure

### Spring Boot Integration

* ✅ `@AutoConfiguration`
* ✅ `@ConfigurationProperties`
* ✅ configuración condicional
* ✅ registro automático mediante `AutoConfiguration.imports`
* ✅ integración del manejo global de excepciones

### Exception Handling

* ✅ `ForgeWebExceptionHandler`
* ✅ manejo de errores de validación
* ✅ manejo de parámetros faltantes
* ✅ manejo de errores de conversión
* ✅ manejo de headers requeridos
* ✅ manejo de recursos no encontrados
* ✅ manejo de métodos HTTP no soportados
* ✅ manejo de excepciones de Forge
* ✅ manejo de excepciones inesperadas

---

## forge-web-starter

### Integración

* ✅ Starter para incorporación sencilla de Forge
* ✅ composición de dependencias necesarias

---

## Testing

* ✅ pruebas unitarias del Core
* ✅ pruebas de respuestas
* ✅ pruebas de builders
* ✅ pruebas de validaciones
* ✅ pruebas del exception handler
* ✅ pruebas de AutoConfiguration
* ✅ pruebas de escenarios HTTP representativos

---

## Documentación

* ✅ `README.md`
* ✅ `PHILOSOPHY.md`
* ✅ `ARCHITECTURE.md`
* ✅ `MODULES.md`
* ✅ `DESIGN_DECISIONS.md`
* ✅ `CODING_STANDARDS.md`
* ✅ `CONTRIBUTING.md`
* ✅ `ROADMAP.md`

---

# Versión 1.1.x

## Objetivo

Incorporar funcionalidades de infraestructura web que proporcionen valor directo sin alterar innecesariamente el contrato base de Forge.

### Candidatos

* Request ID
* contexto de ejecución de la petición
* propagación de Request ID
* integración con MDC
* logging HTTP
* configuración opcional de Request ID
* personalización controlada del comportamiento HTTP

Estas funcionalidades deberán diseñarse individualmente.

No se debe crear una arquitectura completa de contexto, logging u observabilidad antes de conocer los requisitos concretos de cada funcionalidad.

---

# Versión 1.2.x

## Objetivo

Mejorar las capacidades de observabilidad e integración cuando exista una necesidad concreta.

### Candidatos

* Micrometer
* métricas HTTP
* integración con OpenTelemetry
* correlation ID
* propagación distribuida de contexto
* integración con clientes HTTP

La inclusión de cada funcionalidad deberá evaluarse de forma independiente.

---

# Versión 2.x

## Visión

Explorar soporte para aplicaciones reactivas y otros modelos de ejecución cuando la arquitectura actual y la demanda del proyecto lo justifiquen.

### Candidatos

* WebFlux
* Reactor Context
* contexto reactivo
* integración con aplicaciones reactivas

No se introducirán abstracciones en la versión 1.x únicamente para anticipar este escenario.

---

# Ideas futuras

Estas funcionalidades son ideas y no forman parte del roadmap comprometido:

* internacionalización avanzada;
* catálogos de errores configurables;
* integración con Problem Details (RFC 9457);
* cliente HTTP unificado;
* integración con Spring Security;
* auditoría distribuida;
* OpenAPI;
* GraphQL;
* gRPC;
* sistema de eventos internos;
* soporte específico para Kotlin;
* integraciones adicionales de observabilidad.

Cada idea deberá evaluarse individualmente antes de convertirse en una funcionalidad planificada.

---

# Criterios para nuevas funcionalidades

Antes de incorporar una funcionalidad deberá responderse:

* ¿Resuelve un problema real?
* ¿Reduce código repetitivo?
* ¿Aporta un beneficio observable al consumidor?
* ¿Puede implementarse con APIs existentes de Java, Spring o estándares de la industria?
* ¿Mantiene pequeña la API pública?
* ¿Evita abstracciones especulativas?
* ¿Respeta la independencia del Core?
* ¿Puede incorporarse sin afectar funcionalidades existentes?

Si la funcionalidad no aporta suficiente valor, no deberá incorporarse únicamente porque sea técnicamente posible.

---

# Criterios para una nueva versión

Una versión debe considerarse lista cuando:

* la API pública está definida;
* las pruebas cubren los escenarios relevantes;
* la documentación está actualizada;
* no existen regresiones conocidas;
* las dependencias son las mínimas necesarias;
* la funcionalidad está integrada de forma consistente;
* el comportamiento está validado en escenarios reales.

---

# Principio de evolución

Forge prioriza:

**estabilidad > simplicidad > consistencia > cantidad de funcionalidades.**

Una versión pequeña y confiable es preferible a una versión grande que introduzca abstracciones innecesarias.

El roadmap debe evolucionar junto con las necesidades reales del proyecto y no convertirse en una lista de funcionalidades que deban implementarse únicamente porque fueron escritas con anterioridad.
