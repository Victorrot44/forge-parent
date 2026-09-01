# Design Decisions

> **Proyecto:** Forge Web
> **Versión:** 1.0.x
> **Estado:** Estable

Este documento registra las principales decisiones de diseño tomadas durante el desarrollo de Forge.

Su objetivo es preservar el contexto técnico de las decisiones relevantes y evitar que decisiones previamente acordadas sean replanteadas sin una justificación concreta.

Los ADR describen el estado actual del proyecto. Las funcionalidades futuras deben documentarse mediante nuevos ADR cuando exista una decisión técnica concreta.

---

# ADR-001: Arquitectura modular

## Estado

Aceptado.

## Decisión

Forge se divide en módulos con responsabilidades independientes:

```text
forge-parent
│
├── forge-web-core
├── forge-web-autoconfigure
└── forge-web-starter
```

## Justificación

La separación permite mantener el Core independiente de Spring y aislar la integración específica del framework.

La arquitectura debe crecer agregando módulos únicamente cuando exista una funcionalidad que justifique dicha separación.

No se crearán módulos anticipadamente.

---

# ADR-002: El Core no depende de Spring

## Estado

Aceptado.

## Decisión

`forge-web-core` no depende de:

* Spring Framework;
* Spring Boot;
* Jakarta Servlet;
* WebFlux;
* Reactor;
* Jackson;
* implementaciones específicas de servidores HTTP.

## Justificación

El Core contiene modelos, contratos y lógica reutilizable que no requieren conocimiento del framework.

La integración con Spring debe permanecer fuera del Core.

Esto permite mantener estable la base de Forge y evita acoplar sus componentes fundamentales a una tecnología específica.

---

# ADR-003: Separación entre respuestas exitosas y respuestas de error

## Estado

Aceptado.

## Decisión

Forge utiliza dos modelos independientes:

* `SuccessResponse<T>`;
* `ErrorResponse`.

`ApiResponse` proporciona métodos de entrada para facilitar la construcción de estas respuestas.

## Justificación

Las respuestas exitosas y los errores tienen estructuras y responsabilidades diferentes.

Separarlas permite mantener contratos claros y evita introducir campos mutuamente excluyentes en un único modelo.

---

# ADR-004: No imponer el uso de ForgeResponse

## Estado

Aceptado.

## Decisión

Forge no envolverá automáticamente las respuestas de los controladores para imponer `SuccessResponse`.

Una aplicación puede utilizar:

```java
@PostMapping("/users")
CreateUserResponse create(@RequestBody CreateUserRequest request)
```

o utilizar explícitamente:

```java
@PostMapping("/users")
SuccessResponse<CreateUserResponse> create(
        @RequestBody CreateUserRequest request)
```

También puede utilizar:

```java
@PostMapping("/users")
ResponseEntity<SuccessResponse<CreateUserResponse>> create(
        @RequestBody CreateUserRequest request)
```

## Justificación

Forge debe facilitar la estandarización sin modificar de forma automática contratos que el consumidor decidió implementar de otra manera.

La librería debe ser opt-in respecto al modelo de respuesta exitosa.

Esto mantiene la compatibilidad y evita efectos secundarios inesperados.

---

# ADR-005: Uso de records

## Estado

Aceptado.

## Decisión

Forge utilizará `record` preferentemente para modelos de datos públicos inmutables.

Ejemplos:

* `SuccessResponse`;
* `ErrorResponse`;
* `ErrorDetail`;
* `ApiMetadata`;
* `Pagination`.

## Justificación

Los records proporcionan:

* menor código repetitivo;
* semántica clara de datos;
* inmutabilidad estructural;
* métodos generados automáticamente;
* una API concisa.

El uso de `record` no es obligatorio cuando las características del tipo requieran una clase tradicional.

---

# ADR-006: Builders únicamente cuando aporten valor

## Estado

Aceptado.

## Decisión

Forge utilizará Builders cuando la construcción de un objeto tenga suficiente complejidad o número de opciones como para justificar una API fluida.

Para objetos simples se preferirán constructores o métodos de fábrica.

## Justificación

Los Builders tienen un coste adicional en código y API pública.

No deben introducirse cuando una construcción directa sea clara.

---

# ADR-007: Excepciones de Forge

## Estado

Aceptado.

## Decisión

Forge proporciona excepciones propias para errores que forman parte de su contrato.

La jerarquía actual incluye:

```text
ForgeException
ForgeInternalException
```

`ForgeException` representa errores controlados que contienen un `ErrorType`.

`ForgeInternalException` representa errores internos que deben traducirse a una respuesta `500 Internal Server Error`.

## Justificación

La separación permite distinguir errores controlados del dominio de Forge de errores internos que no deben exponerse directamente al consumidor.

No todas las excepciones utilizadas por una aplicación deben pertenecer a la jerarquía de Forge.

---

# ADR-008: ErrorType como catálogo de errores

## Estado

Aceptado.

## Decisión

Forge utiliza `ErrorType` para representar categorías generales de error.

La versión actual incluye:

```text
INVALID_ARGUMENT
RESOURCE_NOT_FOUND
CONFLICT
AUTHENTICATION_REQUIRED
ACCESS_DENIED
EXTERNAL_SERVER_ERROR
INTERNAL_SERVER_ERROR
TIMEOUT
```

El `ErrorType` se utiliza para determinar el código y, cuando corresponde, el HTTP status asociado.

## Justificación

Un catálogo común proporciona consistencia entre las excepciones de Forge y su representación HTTP.

La asignación entre `ErrorType` y HTTP status se mantiene separada mediante `ErrorTypeStatusMapper`.

Esto evita acoplar directamente el catálogo de errores del Core a Spring.

---

# ADR-009: Manejo global de excepciones mediante Spring MVC

## Estado

Aceptado.

## Decisión

La integración Spring de Forge proporciona un `@RestControllerAdvice` para convertir excepciones conocidas en `ErrorResponse`.

La implementación maneja, entre otras:

* errores de validación;
* parámetros faltantes;
* errores de conversión;
* headers requeridos;
* recursos no encontrados;
* métodos HTTP no soportados;
* excepciones de Forge;
* excepciones inesperadas.

## Justificación

Los errores de infraestructura web requieren conocimiento de Spring MVC y, por lo tanto, no pertenecen al Core.

Centralizar este comportamiento permite proporcionar respuestas consistentes sin obligar a cada aplicación a implementar los mismos handlers.

El consumidor puede definir su propio `@RestControllerAdvice` cuando necesite un comportamiento diferente.

---

# ADR-010: Uso de APIs públicas de Spring Boot

## Estado

Aceptado.

## Decisión

Forge utilizará APIs públicas y estables de Spring y Spring Boot siempre que exista una solución adecuada.

No se utilizarán APIs internas de Spring Boot cuando exista una alternativa pública equivalente.

## Justificación

El uso de APIs públicas reduce el acoplamiento con detalles internos del framework y facilita la evolución entre versiones.

Forge debe complementar Spring, no reemplazar sus mecanismos existentes.

---

# ADR-011: AutoConfiguration con comportamiento no invasivo

## Estado

Aceptado.

## Decisión

Forge se integra automáticamente mediante Spring Boot AutoConfiguration.

La configuración debe:

* funcionar sin configuración adicional en el caso común;
* registrar únicamente los componentes necesarios;
* permitir personalización cuando exista una necesidad legítima;
* respetar beans proporcionados por la aplicación cuando corresponda;
* evitar modificar propiedades globales de forma inesperada.

## Justificación

El objetivo es proporcionar una experiencia plug & play sin generar efectos secundarios difíciles de descubrir.

---

# ADR-012: API pública mínima

## Estado

Aceptado.

## Decisión

Solo formarán parte de la API pública los tipos que necesiten ser consumidos directamente por las aplicaciones.

Las implementaciones internas deben mantener la visibilidad más restrictiva posible.

## Justificación

Toda API pública se convierte en un contrato de compatibilidad.

Una API pequeña reduce el coste de mantenimiento y permite evolucionar Forge con mayor libertad.

No se expondrán clases, interfaces o métodos únicamente para facilitar una implementación interna.

---

# ADR-013: No introducir abstracciones especulativas

## Estado

Aceptado.

## Decisión

Forge no introducirá interfaces, factories, providers, strategies, módulos o patrones únicamente para anticipar posibles necesidades futuras.

Una abstracción debe justificarse mediante una necesidad concreta y observable.

## Justificación

Las abstracciones prematuras aumentan:

* complejidad;
* superficie de API;
* coste de mantenimiento;
* dificultad de aprendizaje.

La extensibilidad debe incorporarse cuando exista una necesidad real.

---

# ADR-014: Preferencia por estándares existentes

## Estado

Aceptado.

## Decisión

Antes de crear una abstracción propia, Forge evaluará si el problema puede resolverse correctamente mediante:

* Java;
* HTTP;
* Spring;
* Spring Boot;
* estándares de la industria.

Cuando un estándar existente sea suficiente, Forge deberá preferirlo.

## Justificación

Forge no busca reemplazar capacidades existentes.

La creación de una abstracción propia solo está justificada cuando proporciona un valor concreto que la solución existente no cubre adecuadamente.

---

# ADR-015: Evolución incremental

## Estado

Aceptado.

## Decisión

Las nuevas funcionalidades se incorporarán de forma independiente siempre que sea posible.

Una funcionalidad futura no debe modificar anticipadamente la arquitectura de funcionalidades existentes.

## Justificación

Permite mantener la versión base pequeña y estable.

La arquitectura debe evolucionar a partir de necesidades reales y no a partir de funcionalidades hipotéticas.

---

# ADR-016: Request ID fuera del alcance de Forge Web

## Estado

Aceptado.

## Decisión

Forge Web no proporciona mecanismos propios para generar, validar, propagar o almacenar un Request ID.

`Request ID`, correlation ID y trazabilidad distribuida quedan fuera del alcance de Forge Web.

Forge no incorporará:

* generación automática de Request ID;
* propagación de Request ID mediante headers;
* validación de identificadores de solicitud;
* integración con MDC;
* almacenamiento de identificadores de solicitud;
* contexto global de ejecución;
* persistencia de información de trazabilidad.

Cuando una aplicación necesite estas capacidades, deberá utilizar las herramientas de observabilidad, tracing o correlación que correspondan a su arquitectura.

## Justificación

Forge tiene como responsabilidad principal la estandarización de respuestas HTTP y el manejo global de errores.

La generación y propagación de identificadores de solicitud pertenece al ámbito de observabilidad y trazabilidad, no al contrato de las respuestas HTTP.

Existen estándares y soluciones especializadas para resolver estas necesidades. Implementar una solución propia dentro de Forge introduciría responsabilidades adicionales, configuración y superficie de API sin aportar un beneficio directo al propósito principal de la librería.

Mantener esta responsabilidad fuera de Forge permite:

* conservar el Core pequeño;
* evitar acoplamiento con mecanismos de observabilidad;
* evitar duplicar capacidades proporcionadas por otras herramientas;
* mantener una API pública mínima;
* permitir que cada aplicación adopte la estrategia de trazabilidad adecuada.

Por esta razón, `requestId` no forma parte de `ErrorResponse`.

---

# ADR-017: Serialización opcional de respuestas

## Estado

Aceptado.

## Decisión

Forge mantiene sus modelos del Core independientes de Jackson.

La integración con Jackson se realiza de forma opcional desde el módulo de integración con Spring Boot.

Cuando Jackson está disponible, Forge puede aplicar reglas de serialización específicas mediante mecanismos de integración como Jackson Mixins.

Las respuestas no deben incluir propiedades `null` o valores vacíos cuando no aporten información al consumidor.

Las respuestas exitosas mantienen `data` en el contrato incluso cuando su valor sea `null`.

## Justificación

La representación JSON pertenece a la capa de integración y no debe introducir una dependencia de Jackson en el Core.

La eliminación de valores innecesarios permite producir respuestas más limpias sin modificar los modelos fundamentales de Forge.

Mantener esta integración opcional evita imponer una tecnología de serialización al consumidor.

---

# ADR-018: Diseño de errores basado en Problem Details

## Estado

Aceptado.

## Decisión

El diseño de las respuestas de error de Forge toma como referencia **RFC 9457 — Problem Details for HTTP APIs**.

Forge utiliza su propio modelo `ErrorResponse` para mantener el contrato definido por la librería, pero sus decisiones de diseño deben mantenerse alineadas con los principios de Problem Details cuando sean aplicables.

## Justificación

RFC 9457 proporciona un estándar de la industria para representar información de errores HTTP.

Forge debe aprovechar estándares existentes en lugar de crear convenciones arbitrarias.

La adopción del estándar no implica introducir una dependencia adicional ni convertir el Core en una implementación específica de un framework.

---

# Política de nuevas decisiones

Toda decisión arquitectónica relevante deberá registrarse en este documento cuando:

* cambie la arquitectura;
* introduzca una nueva dependencia;
* modifique una API pública;
* introduzca un nuevo mecanismo de extensión;
* establezca una nueva convención importante;
* cambie una decisión previamente aceptada.

Las decisiones existentes no deben modificarse silenciosamente.

Cuando una decisión aceptada deje de ser válida, deberá registrarse un nuevo ADR que explique el cambio y su justificación.

Los ADR históricos no deben eliminarse únicamente porque posteriormente hayan sido reemplazados.
