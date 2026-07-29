# Design Decisions

> Proyecto: **Forge Web**
> Estado: Activo

Este documento registra las principales decisiones de diseño tomadas durante el desarrollo de Forge. Su objetivo es preservar el contexto arquitectónico del proyecto y evitar que decisiones previamente acordadas sean replanteadas sin una justificación técnica.

---

# ADR-001: Arquitectura Modular

## Estado

Aceptado.

## Decisión

Forge se divide en módulos independientes.

```text
forge-parent
│
├── forge-web-core
├── forge-web-autoconfigure
├── forge-web-starter
├── forge-web-test
└── examples
```

## Justificación

Cada módulo posee una responsabilidad claramente definida.

Esto reduce el acoplamiento y facilita la evolución independiente de cada componente.

---

# ADR-002: El Core no depende de Spring

## Estado

Aceptado.

## Decisión

`forge-web-core` no tendrá dependencias hacia:

* Spring Framework
* Spring Boot
* Servlet API
* WebFlux
* Reactor

## Justificación

El Core representa la lógica reutilizable del proyecto y debe poder utilizarse fuera del ecosistema Spring Boot.

---

# ADR-003: Contratos separados para respuestas exitosas y de error

## Estado

Aceptado.

## Decisión

Las respuestas de la API estarán representadas por una interfaz sellada (`ApiResponse`) con dos implementaciones:

* `SuccessResponse`
* `ErrorResponse`

## Alternativas consideradas

Una única clase `ApiResponse`.

## Motivo del rechazo

Una sola clase contiene atributos mutuamente excluyentes (`data` y `errors`), complicando la validación y la evolución del contrato.

---

# ADR-004: Uso de Records

## Estado

Aceptado.

## Decisión

Los modelos de datos públicos utilizarán `record` siempre que representen información inmutable.

## Justificación

* Menor código repetitivo.
* Inmutabilidad.
* Mejor legibilidad.
* API más clara.

---

# ADR-005: Builders únicamente cuando sean necesarios

## Estado

Aceptado.

## Decisión

Los objetos con pocos parámetros utilizarán directamente sus constructores.

Los objetos con múltiples configuraciones expondrán un Builder.

## Justificación

Se busca mantener una API simple sin sacrificar flexibilidad.

---

# ADR-006: ForgeContext independiente de la implementación

## Estado

Aceptado.

## Decisión

El Core únicamente define:

* `ForgeContext`
* `ForgeContextProvider`

No conoce cómo se almacena el contexto.

## Justificación

Permite soportar distintas estrategias como:

* ThreadLocal
* Scoped Values
* Reactor Context
* Otras implementaciones futuras

sin modificar el Core.

---

# ADR-007: Excepciones tipadas

## Estado

Aceptado.

## Decisión

Forge define una jerarquía de excepciones basada en `ForgeException`.

Las categorías principales son:

* ValidationException
* BusinessException
* AuthenticationException
* AuthorizationException
* ExternalServiceException
* ConfigurationException
* InternalException

## Justificación

Cada excepción comunica explícitamente la naturaleza del error y facilita su tratamiento por las capas superiores.

---

# ADR-008: BusinessException es extensible

## Estado

Aceptado.

## Decisión

`BusinessException` es `non-sealed`.

## Justificación

Cada dominio podrá crear sus propias excepciones de negocio heredando de esta clase.

Las demás excepciones permanecen cerradas.

---

# ADR-009: ErrorDescriptor como contrato

## Estado

Aceptado.

## Decisión

Todo error implementa `ErrorDescriptor`.

## Justificación

Permite desacoplar las excepciones de la representación del error y facilita la creación de nuevos catálogos de errores.

---

# ADR-010: ResponseFactory como punto de entrada

## Estado

Aceptado.

## Decisión

Las respuestas deberán construirse preferentemente mediante `ResponseFactory`.

Los Builders permanecerán disponibles para escenarios avanzados.

## Justificación

La mayoría de los casos de uso requieren una API sencilla.

Los Builders ofrecen flexibilidad sin aumentar la complejidad de la interfaz pública.

---

# ADR-011: Respuestas con contrato uniforme

## Estado

Aceptado.

## Decisión

Forge devolverá siempre el mismo contrato estructural para respuestas exitosas y de error.

## Justificación

Los consumidores de la API no deberán implementar lógica especial para distintos tipos de respuesta.

---

# ADR-012: Arquitectura preparada para evolución

## Estado

Aceptado.

## Decisión

La arquitectura se diseña considerando futuras extensiones, entre ellas:

* WebFlux
* Observabilidad
* OpenTelemetry
* Internacionalización
* Nuevos módulos

## Justificación

Las decisiones actuales no deberán impedir la incorporación de nuevas funcionalidades sin romper la compatibilidad del proyecto.

---

# Política de nuevas decisiones

Toda decisión arquitectónica relevante deberá registrarse en este documento antes de implementarse.

Las decisiones existentes no deberán modificarse sin una justificación técnica documentada.
