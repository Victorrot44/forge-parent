# Roadmap

> **Proyecto:** Forge Web

Este documento describe la evolución y el alcance de Forge Web.

Forge evoluciona de forma incremental y únicamente incorpora funcionalidades que resuelven problemas concretos relacionados con la estandarización de respuestas HTTP y el manejo de errores en aplicaciones Spring Boot.

El roadmap no constituye un compromiso de implementación. Las funcionalidades futuras podrán cambiar, posponerse o eliminarse cuando exista una justificación técnica.

---

# Estado del proyecto

| Versión | Estado                       |
| ------- | ---------------------------- |
| 1.0.x   | ✅ Base estable               |
| 1.1.x   | 📋 Evolución según necesidad |
| 2.x     | 💡 Sin planificación         |

Forge Web se considera **estable en su alcance actual**.

Las versiones futuras no representan funcionalidades comprometidas y solo deberán crearse cuando exista una necesidad real que pertenezca al propósito de Forge.

---

# Versión 1.0.x

## Estado

**Completada.**

## Objetivo

Establecer una base sencilla y reutilizable para estandarizar las respuestas HTTP y el manejo global de errores en aplicaciones Spring Boot.

La versión incluye:

* modelos estandarizados de respuesta;
* construcción sencilla de respuestas;
* respuestas exitosas y de error consistentes;
* manejo centralizado de excepciones Spring MVC;
* catálogo básico de tipos de error;
* validaciones del contrato de respuesta;
* integración automática con Spring Boot;
* arquitectura modular;
* Core independiente de Spring;
* serialización configurable mediante integración opcional con Jackson;
* exclusión de propiedades `null` y valores vacíos cuando corresponde;
* soporte de `data` en respuestas exitosas incluso cuando su valor es `null`;
* utilización de Problem Details como referencia para el diseño de errores HTTP.

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
* ✅ integración opcional con Jackson

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
* ✅ pruebas de serialización de respuestas exitosas
* ✅ pruebas de serialización de respuestas de error

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

# Fuera del alcance actual

Las siguientes funcionalidades fueron evaluadas durante el desarrollo, pero no forman parte de Forge Web actualmente.

## Request ID y trazabilidad

Forge Web **no proporciona mecanismos propios de generación, validación o propagación de Request ID**.

La trazabilidad de solicitudes pertenece al ámbito de observabilidad y tracing, donde existen estándares y soluciones especializadas.

Forge tampoco incorpora:

* generación automática de Request ID;
* propagación de Request ID;
* integración con MDC;
* almacenamiento de Request ID;
* contexto global de ejecución;
* correlación distribuida.

Estas responsabilidades pueden ser proporcionadas por la aplicación consumidora mediante las herramientas de observabilidad que considere apropiadas.

## Logging y observabilidad

Forge Web no pretende convertirse en una librería general de logging u observabilidad.

Quedan fuera de su alcance:

* logging HTTP;
* métricas HTTP;
* integración específica con Micrometer;
* integración específica con OpenTelemetry;
* auditoría distribuida;
* sistemas de correlación;
* tracing distribuido.

Estas funcionalidades deberán utilizar las herramientas y estándares apropiados cuando sean necesarias.

---

# Evolución futura

No existen funcionalidades comprometidas para una versión posterior.

Una nueva versión de Forge deberá originarse a partir de una necesidad concreta identificada en aplicaciones consumidoras.

Antes de agregar una nueva funcionalidad deberá determinarse si:

1. pertenece realmente al propósito de Forge;
2. no existe una solución adecuada en Java, Spring o un estándar de la industria;
3. proporciona un beneficio observable al consumidor;
4. puede incorporarse sin aumentar innecesariamente la API pública;
5. puede implementarse de forma independiente;
6. no introduce abstracciones especulativas.

Si una funcionalidad pertenece claramente a otro ámbito, deberá mantenerse fuera de Forge.

---

# Criterios para nuevas funcionalidades

Antes de incorporar una funcionalidad deberá responderse:

* ¿Resuelve un problema real?
* ¿Está relacionado directamente con la estandarización HTTP o el manejo de errores?
* ¿Reduce código repetitivo?
* ¿Aporta un beneficio observable al consumidor?
* ¿Puede implementarse utilizando APIs existentes de Java, Spring o estándares de la industria?
* ¿Mantiene pequeña la API pública?
* ¿Evita abstracciones especulativas?
* ¿Respeta la independencia del Core?
* ¿Puede incorporarse sin afectar funcionalidades existentes?

Si la respuesta no justifica claramente la incorporación, la funcionalidad no deberá agregarse únicamente porque sea técnicamente posible.

---

# Criterios para una nueva versión

Una nueva versión debe considerarse lista cuando:

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

El roadmap no debe convertirse en una lista de funcionalidades que deban implementarse únicamente porque fueron escritas con anterioridad.

Forge deberá evolucionar únicamente cuando exista una necesidad real y esa necesidad pertenezca al propósito del proyecto.
