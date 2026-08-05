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

* Respuestas HTTP estandarizadas.
* Manejo centralizado de excepciones.
* Respuestas de error basadas en Problem Details / RFC 9457 como referencia conceptual.
* Catálogo de tipos de error reutilizable.
* Manejo de excepciones comunes de Spring MVC.
* AutoConfiguration para Spring Boot.
* Starter para integración sencilla.
* Core independiente de Spring.
* Cero configuración para el caso común.
* Respuestas exitosas opcionales mediante `SuccessResponse`.
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

El módulo `forge-web-core` no depende de Spring.

La integración específica con Spring Boot se encuentra en los módulos correspondientes.

---

# Módulos

| Módulo                    | Descripción                                                                    |
| ------------------------- | ------------------------------------------------------------------------------ |
| `forge-web-core`          | Modelos, respuestas, errores, excepciones y lógica independiente de Spring.    |
| `forge-web-autoconfigure` | AutoConfiguration e integración con Spring Boot.                               |
| `forge-web-starter`       | Starter que simplifica la incorporación de Forge a una aplicación Spring Boot. |

---

# Instalación

Agregar el starter:

```xml
<dependency>
    <groupId>io.github.victorrot44</groupId>
    <artifactId>forge-web-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Forge está diseñado para funcionar con cero configuración en el caso común.

---

# Respuestas exitosas

Forge no obliga a envolver automáticamente las respuestas exitosas de una aplicación.

Por ejemplo, un endpoint puede continuar utilizando una respuesta normal de Spring:

```java
@PostMapping("/users")
CreateUserRequest create(@Valid @RequestBody CreateUserRequest request) {
    // ...
}
```

También puede utilizar directamente el modelo de respuesta de Forge:

```java
@PostMapping("/users")
SuccessResponse<CreateUserRequest> create(
        @Valid @RequestBody CreateUserRequest request) {

    // ...
}
```

Y cuando se necesita controlar explícitamente el `ResponseEntity`:

```java
@PostMapping("/users")
ResponseEntity<SuccessResponse<CreateUserRequest>> create(
        @Valid @RequestBody CreateUserRequest request) {

    // ...
}
```

Forge respeta el contrato elegido por la aplicación.

---

# Manejo de excepciones

Forge registra automáticamente un manejador global de excepciones para las aplicaciones Spring Boot que utilizan el starter.

Por ejemplo:

```java
throw new ForgeException(
        ErrorCategory.BUSINESS,
        ErrorType.RESOURCE_NOT_FOUND,
        "Información solicitada no encontrada."
);
```

Puede producir una respuesta como:

```json
{
  "requestId": null,
  "timestamp": "2026-08-04T16:28:53Z",
  "httpStatus": 404,
  "code": "RESOURCE_NOT_FOUND",
  "message": "Información solicitada no encontrada.",
  "errors": [],
  "metadata": null
}
```

Las excepciones comunes de Spring MVC también son manejadas cuando corresponda.

Por ejemplo:

* `MethodArgumentNotValidException` → `400`
* `HttpMessageNotReadableException` → `400`
* `MissingServletRequestParameterException` → `400`
* `MethodArgumentTypeMismatchException` → `400`
* `MissingRequestHeaderException` → `400`
* `NoResourceFoundException` → `404`
* `HttpRequestMethodNotSupportedException` → `405`
* excepciones no esperadas → `500`

La aplicación puede proporcionar su propio `@RestControllerAdvice` cuando necesite un comportamiento diferente.

---

# Configuración

La mayoría de las aplicaciones no requieren configuración adicional.

Forge está diseñado para proporcionar un comportamiento útil mediante convenciones y valores predeterminados razonables.

Las propiedades de configuración solamente se introducirán cuando exista una necesidad concreta de personalización.

---

# Documentación

La documentación se encuentra en la carpeta `docs/`.

Documentos principales:

* `ARCHITECTURE.md`
* `MODULES.md`
* `PHILOSOPHY.md`
* `DESIGN_DECISIONS.md`
* `ROADMAP.md`

---

# Estado del proyecto

Versión actual:

```text
1.0.0-SNAPSHOT
```

Esta versión establece los cimientos de Forge:

* modelo de respuestas;
* modelo de errores;
* excepciones;
* manejo global de excepciones;
* integración con Spring Boot;
* starter;
* testing de la funcionalidad base.

Las funcionalidades adicionales se incorporarán de forma independiente en versiones posteriores.

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
