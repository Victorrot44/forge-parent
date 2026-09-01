# Forge Web

> **A lightweight Spring Boot starter for standardized HTTP responses and centralized exception handling.**

Forge Web proporciona una base ligera para aplicaciones Spring Boot mediante respuestas HTTP estandarizadas, manejo centralizado de excepciones e integración transparente con Spring Boot.

El objetivo de Forge es reducir código repetitivo sin imponer una arquitectura de aplicación ni modificar innecesariamente el comportamiento existente.

---

## ¿Por qué existe Forge?

En las aplicaciones Spring Boot se repiten con frecuencia componentes como:

* formatos de respuesta HTTP;
* `@RestControllerAdvice`;
* manejo de excepciones;
* validación de requests;
* códigos y mensajes de error;
* configuración repetitiva entre servicios.

Forge proporciona estas capacidades de forma reutilizable y consistente.

La librería está diseñada para integrarse progresivamente en aplicaciones existentes y mantener una API pública pequeña.

---

# Características actuales

* Respuestas HTTP estandarizadas mediante `SuccessResponse` y `ErrorResponse`.
* Manejo centralizado de excepciones.
* Respuestas de error inspiradas en el estándar Problem Details / RFC 9457.
* Catálogo de tipos de error reutilizable.
* Manejo de excepciones comunes de Spring MVC.
* AutoConfiguration para Spring Boot.
* Starter para integración sencilla.
* Core independiente de Spring.
* Cero configuración para el caso común.
* Soporte opcional para respuestas exitosas mediante `SuccessResponse`.
* Serialización configurable de respuestas mediante Jackson cuando está disponible.
* Exclusión de propiedades vacías o nulas en las respuestas cuando la integración Jackson está habilitada.
* No modifica automáticamente las respuestas exitosas de la aplicación.

---

# Filosofía

Forge sigue algunos principios fundamentales:

* Simplicidad antes que complejidad.
* Convención antes que configuración.
* El Core no depende de Spring.
* Las APIs públicas deben ser pequeñas y estables.
* No introducir abstracciones sin una necesidad concreta.
* Utilizar estándares de Java, HTTP y Spring cuando resuelvan correctamente el problema.
* No modificar el comportamiento de la aplicación de forma inesperada.
* Las funcionalidades deben poder incorporarse de forma independiente.
* La extensibilidad debe responder a necesidades reales del consumidor.

Más información en `docs/PHILOSOPHY.md`.

---

# Arquitectura

```text
                +---------------------------+
                | forge-web-starter         |
                |                           |
                | Spring Boot Starter       |
                +-------------+-------------+
                              |
                +-------------v-------------+
                | forge-web-autoconfigure   |
                |                           |
                | AutoConfiguration         |
                | Exception Handling        |
                | Jackson Integration       |
                | Spring Integration        |
                +-------------+-------------+
                              |
                +-------------v-------------+
                | forge-web-core            |
                |                           |
                | Responses                 |
                | Errors                    |
                | Exceptions                |
                | Validation                |
                +---------------------------+
```

El módulo `forge-web-core` no depende de Spring ni de Jackson.

La integración específica con Spring Boot y Jackson se encuentra en los módulos correspondientes.

---

# Módulos

| Módulo                    | Descripción                                                                                |
| ------------------------- | ------------------------------------------------------------------------------------------ |
| `forge-web-core`          | Modelos, respuestas, errores, excepciones, validaciones y lógica independiente de Spring.  |
| `forge-web-autoconfigure` | AutoConfiguration, manejo de excepciones e integración opcional con Spring Boot y Jackson. |
| `forge-web-starter`       | Starter que simplifica la incorporación de Forge a una aplicación Spring Boot.             |

---

# Instalación

Agregar el starter:

```xml
<dependency>
    <groupId>io.github.victorrot44</groupId>
    <artifactId>forge-web-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

Forge está diseñado para funcionar con cero configuración en el caso común.

---

# Respuestas exitosas

Forge no obliga a envolver automáticamente las respuestas exitosas de una aplicación.

Por ejemplo, un endpoint puede continuar utilizando una respuesta normal de Spring:

```java
@PostMapping("/users")
CreateUserResponse create(@Valid @RequestBody CreateUserRequest request) {
    // ...
}
```

También puede utilizar directamente el modelo de respuesta de Forge:

```java
@PostMapping("/users")
SuccessResponse<CreateUserResponse> create(
        @Valid @RequestBody CreateUserRequest request) {

    // ...
}
```

Y cuando se necesita controlar explícitamente el `ResponseEntity`:

```java
@PostMapping("/users")
ResponseEntity<SuccessResponse<CreateUserResponse>> create(
        @Valid @RequestBody CreateUserRequest request) {

    // ...
}
```

Forge respeta el contrato elegido por la aplicación.

Cuando se utiliza `SuccessResponse`, Forge proporciona la estructura estandarizada correspondiente.

---

# Manejo de excepciones

Forge registra automáticamente un manejador global de excepciones para las aplicaciones Spring Boot que utilizan el starter.

Por ejemplo:

```java
throw new ForgeException(
        ErrorType.RESOURCE_NOT_FOUND,
        "Información solicitada no encontrada."
);
```

Puede producir una respuesta como:

```json
{
  "timestamp": "2026-09-01T16:39:29Z",
  "httpStatus": 404,
  "code": "RESOURCE_NOT_FOUND",
  "message": "Información solicitada no encontrada.",
  "errors": []
}
```

Los campos opcionales pueden omitirse durante la serialización cuando la integración correspondiente está habilitada.

Las excepciones comunes de Spring MVC también son manejadas cuando corresponde.

Por ejemplo:

* `MethodArgumentNotValidException` → `400`
* `HttpMessageNotReadableException` → `400`
* `MissingServletRequestParameterException` → `400`
* `MethodArgumentTypeMismatchException` → `400`
* `MissingRequestHeaderException` → `400`
* `NoResourceFoundException` → `404`
* `HttpRequestMethodNotSupportedException` → `405`
* excepciones no esperadas → `500`

Las excepciones específicas de Forge utilizan `ErrorType` para determinar su representación HTTP.

La aplicación puede proporcionar su propio `@RestControllerAdvice` cuando necesite un comportamiento diferente.

---

# Respuestas de error

Forge utiliza `ErrorResponse` para representar errores HTTP de forma consistente.

Una respuesta puede contener:

* `timestamp`;
* `httpStatus`;
* `code`;
* `message`;
* `errors`;
* `metadata`.

Los campos opcionales pueden permanecer ausentes cuando no sean necesarios.

Por ejemplo, un error de recurso no encontrado puede representarse como:

```json
{
  "timestamp": "2026-09-01T16:39:29Z",
  "httpStatus": 404,
  "code": "RESOURCE_NOT_FOUND",
  "message": "Información solicitada no encontrada."
}
```

La estructura de errores de Forge toma como referencia los principios de Problem Details para HTTP APIs definidos por RFC 9457, pero mantiene su propio contrato de respuesta.

---

# Jackson

La integración con Jackson es opcional.

Forge no incorpora Jackson como dependencia del Core.

Cuando la integración Jackson está disponible, Forge puede utilizar sus mecanismos de serialización para evitar la inclusión de propiedades vacías o nulas en las respuestas.

Esto permite mantener el Core independiente del mecanismo concreto de serialización.

La aplicación mantiene el control sobre la configuración de Jackson y Forge no debe imponer configuraciones globales innecesarias.

---

# Configuración

La mayoría de las aplicaciones no requieren configuración adicional.

Forge está diseñado para proporcionar un comportamiento útil mediante convenciones y valores predeterminados razonables.

Las propiedades de configuración solamente se introducen cuando existe una necesidad concreta de personalización.

La configuración de Forge debe permanecer opcional y no invasiva.

---

# Documentación

La documentación se encuentra en la carpeta `docs/`.

Documentos principales:

* `ARCHITECTURE.md`
* `MODULES.md`
* `PHILOSOPHY.md`
* `DESIGN_DECISIONS.md`
* `ROADMAP.md`
* `CODING_STANDARDS.md`
* `CONTRIBUTING.md`

---

# Estado del proyecto

Versión actual:

```text
1.0.0
```

La versión `1.0.0` establece una base estable para Forge:

* modelos de respuestas;
* modelos de error;
* excepciones;
* validaciones;
* manejo global de excepciones;
* integración con Spring Boot;
* soporte opcional de Jackson;
* starter;
* pruebas de la funcionalidad base.

Forge no incorpora funcionalidades de observabilidad, tracing, Request ID, correlation ID o logging HTTP como parte de su contrato base.

Las funcionalidades futuras deberán evaluarse de forma independiente y solamente incorporarse cuando exista una necesidad concreta que justifique su inclusión.

---

# Compatibilidad

| Forge | Spring Boot | Java |
| ----- | ----------- | ---- |
| 1.x   | 4.x         | 21+  |

---

# Contribuciones

Las contribuciones son bienvenidas.

Consulta `CONTRIBUTING.md` para conocer el proceso de colaboración.

---

# Licencia

Este proyecto se distribuye bajo la licencia MIT.

Consulta el archivo `LICENSE` para más información.
