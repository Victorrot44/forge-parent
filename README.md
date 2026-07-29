# Forge Web

> **A lightweight Spring Boot starter for consistent APIs, centralized exception handling, request tracing, and web infrastructure.**

Forge Web proporciona una base sólida para aplicaciones Spring Boot mediante respuestas estandarizadas, manejo centralizado de excepciones, trazabilidad con Request ID y una integración transparente con el ecosistema de Spring.

---

## ¿Por qué existe Forge?

En la mayoría de aplicaciones Spring Boot se repiten los mismos componentes:

* wrappers para respuestas HTTP;
* `@ControllerAdvice`;
* excepciones personalizadas;
* filtros para Request ID;
* logging;
* auditoría;
* validaciones;
* configuración repetitiva.

Cada equipo termina implementando estas piezas una y otra vez.

Forge nace para resolver ese problema una sola vez mediante una solución consistente, extensible y fácil de integrar.

---

# Características

* Respuestas HTTP estandarizadas.
* Manejo centralizado de excepciones.
* Catálogo de errores reutilizable.
* Request ID para trazabilidad.
* Contexto compartido durante la petición.
* Integración automática con Spring Boot.
* Configuración mediante AutoConfiguration.
* API pequeña y fácil de aprender.
* Arquitectura modular.
* Extensible mediante contratos e interfaces.

---

# Filosofía

Forge sigue algunos principios fundamentales:

* Simplicidad antes que complejidad.
* Convención antes que configuración.
* El Core no depende de Spring.
* Objetos públicos inmutables.
* APIs pequeñas y expresivas.
* Componentes fácilmente extensibles.
* Consistencia entre aplicaciones.

Más información en `docs/PHILOSOPHY.md`.

---

# Arquitectura

```text
                +---------------------------+
                | forge-web-starter         |
                | Filters                   |
                | ControllerAdvice          |
                | Jackson                   |
                +-------------+-------------+
                              |
                +-------------v-------------+
                | forge-web-autoconfigure   |
                | AutoConfiguration         |
                | Properties                |
                +-------------+-------------+
                              |
                +-------------v-------------+
                | forge-web-core            |
                | Response                  |
                | Errors                    |
                | Context                   |
                | Exceptions                |
                | Factory                   |
                +---------------------------+
```

---

# Módulos

| Módulo                    | Descripción                                                        |
| ------------------------- | ------------------------------------------------------------------ |
| `forge-web-core`          | Modelos, contratos y lógica independiente del framework.           |
| `forge-web-autoconfigure` | Configuración automática para Spring Boot.                         |
| `forge-web-starter`       | Integración HTTP, filtros, manejo global de excepciones y logging. |
| `forge-web-test`          | Utilidades para pruebas.                                           |
| `examples`                | Ejemplos de uso.                                                   |

---

# Instalación

Agregar la dependencia:

```xml
<dependency>
    <groupId>io.github.victorrot44</groupId>
    <artifactId>forge-web-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

# Primer ejemplo

```java
@RestController
@RequestMapping("/users")
public class UserController {

    private final ResponseFactory responses;

    public UserController(ResponseFactory responses) {
        this.responses = responses;
    }

    @GetMapping("/{id}")
    public SuccessResponse<UserDto> getUser(@PathVariable Long id) {
        UserDto user = service.findById(id);
        return responses.ok(user);
    }
}
```

Respuesta:

```json
{
  "requestId": "0b2fd6f6-d6d9-43f5-a642-624d4c4ec7f4",
  "timestamp": "2026-07-29T18:30:15Z",
  "httpStatus": 200,
  "code": "SUCCESS",
  "message": "Operation completed successfully.",
  "data": {
    "id": 1,
    "name": "Victor"
  }
}
```

---

# Manejo de excepciones

```java
throw new ValidationException(UserErrors.INVALID_EMAIL);
```

Forge convertirá automáticamente la excepción en una respuesta uniforme.

---

# Configuración

La mayoría de aplicaciones no requieren configuración adicional.

Cuando sea necesario, Forge podrá personalizarse mediante propiedades de Spring Boot.

---

# Documentación

La documentación completa se encuentra en la carpeta `docs/`.

* `ARCHITECTURE.md`
* `MODULES.md`
* `PHILOSOPHY.md`
* `DESIGN_DECISIONS.md`
* `ROADMAP.md`

---

# Estado del proyecto

Versión actual:

```
1.0.0-SNAPSHOT
```

El proyecto se encuentra en desarrollo activo.

---

# Compatibilidad

| Forge | Spring Boot | Java |
| ----- | ----------- | ---- |
| 1.x   | 3.x         | 21+  |

---

# Contribuciones

Las contribuciones son bienvenidas.

Consulta `CONTRIBUTING.md` para conocer el proceso de colaboración.

---

# Licencia

Este proyecto se distribuye bajo la licencia MIT.

Consulta el archivo `LICENSE` para más información.
