# Forge Web Architecture

> **Version:** 1.0.x
> **Project:** forge-web
> **GroupId:** `io.github.victorrot44`
> **Java:** 21
> **Spring Boot:** 4.x

---

# 1. Introducción

## 1.1 Propósito

Forge Web es una librería orientada al ecosistema Spring Boot cuyo objetivo es proporcionar componentes reutilizables para estandarizar respuestas HTTP y el manejo global de errores.

Forge busca reducir código repetitivo sin imponer una arquitectura de aplicación ni modificar innecesariamente el comportamiento existente.

La versión `1.0.x` establece una base estable compuesta por:

* modelos de respuesta;
* modelos de error;
* excepciones de Forge;
* validación;
* manejo global de excepciones para Spring MVC;
* integración mediante Spring Boot AutoConfiguration;
* integración opcional con Jackson;
* starter para una integración sencilla.

Forge no pretende convertirse en una solución general de observabilidad, trazabilidad o logging.

---

# 2. Objetivos arquitectónicos

Forge persigue los siguientes objetivos:

* proporcionar una API pública pequeña;
* proporcionar contratos estandarizados de respuesta cuando el consumidor decide utilizarlos;
* proporcionar un manejo consistente de errores;
* mantener el Core independiente de Spring;
* permitir cero configuración para el caso común;
* evitar efectos secundarios globales inesperados;
* permitir que el consumidor sustituya comportamientos cuando exista una necesidad legítima;
* utilizar APIs públicas y estables de Java y Spring;
* utilizar estándares existentes cuando resuelvan correctamente un problema;
* evitar abstracciones especulativas;
* mantener cada funcionalidad dentro de una responsabilidad claramente definida.

---

# 3. Principios arquitectónicos

## 3.1 Simplicidad

La arquitectura debe ser tan pequeña como sea necesario para resolver los problemas actuales.

No se crearán módulos, interfaces, factories, providers o capas únicamente para anticipar funcionalidades futuras.

---

## 3.2 Separación de responsabilidades

Cada módulo debe tener una responsabilidad clara.

La separación existe para reducir acoplamiento y facilitar el mantenimiento, no como un objetivo independiente.

---

## 3.3 Independencia del Core

`forge-web-core` no depende de Spring ni de tecnologías específicas de infraestructura web.

La integración con Spring pertenece a los módulos correspondientes.

---

## 3.4 Uso de estándares

Cuando un estándar de Java, HTTP o Spring resuelva correctamente un problema, Forge debe utilizarlo antes de introducir una abstracción propia.

Esto incluye, entre otros:

* APIs estándar de Java;
* HTTP;
* Spring MVC;
* Spring Boot AutoConfiguration;
* Problem Details / RFC 9457 cuando corresponda.

---

## 3.5 Extensibilidad controlada

Forge debe permitir personalización cuando exista una necesidad real.

La extensibilidad no debe convertirse en una razón para introducir abstracciones especulativas.

Una funcionalidad puede permanecer concreta mientras no exista una necesidad legítima de sustitución.

---

## 3.6 Compatibilidad

La API pública de Forge debe considerarse un contrato.

Los cambios incompatibles deben evitarse dentro de una misma versión mayor.

La evolución de la API debe realizarse siguiendo Semantic Versioning.

---

# 4. Arquitectura modular

Forge está compuesto por los siguientes módulos:

```text
forge-parent
│
├── forge-web-core
├── forge-web-autoconfigure
└── forge-web-starter
```

La documentación del proyecto se encuentra en:

```text
docs/
```

La documentación no constituye un módulo Maven.

La estructura se mantiene deliberadamente pequeña y no incluye módulos adicionales.

---

# 5. Dependencias entre módulos

Las dependencias siguen una dirección unidireccional:

```text
forge-web-starter
        │
        ▼
forge-web-autoconfigure
        │
        ▼
forge-web-core
```

El Core no depende de los módulos superiores.

No deben introducirse dependencias circulares.

---

# 6. forge-web-core

`forge-web-core` constituye el núcleo independiente de Forge.

## Responsabilidades

Contiene:

* respuestas;
* metadata;
* detalles de error;
* tipos de error;
* excepciones de Forge;
* validaciones;
* builders;
* lógica independiente de Spring.

Ejemplos de componentes:

```text
io.github.victorrot44.forge.web.core
│
├── error
├── exception
└── response
```

El Core utiliza Java 21 y debe permanecer independiente de frameworks.

## Dependencias prohibidas

El Core no debe depender de:

* Spring Framework;
* Spring Boot;
* Jakarta Servlet;
* WebFlux;
* Reactor;
* Jackson;
* implementaciones específicas de servidores HTTP.

---

# 7. forge-web-autoconfigure

`forge-web-autoconfigure` contiene la integración automática de Forge con Spring Boot.

## Responsabilidades

Incluye:

* `@AutoConfiguration`;
* configuración de propiedades;
* registro condicional de beans;
* integración del manejo global de excepciones;
* integración opcional con Jackson;
* componentes específicos de Spring necesarios para el comportamiento de Forge.

La configuración debe utilizar APIs públicas y estables de Spring Boot.

Cuando sea apropiado, la configuración debe permitir que la aplicación sustituya beans proporcionados por Forge.

---

# 8. forge-web-starter

`forge-web-starter` proporciona una forma sencilla de incorporar Forge a una aplicación Spring Boot.

Su responsabilidad principal es reunir las dependencias necesarias para el consumidor.

El Starter no debe convertirse en un contenedor de lógica de negocio ni duplicar la lógica de `forge-web-autoconfigure`.

La implementación concreta de las funcionalidades dependientes de Spring debe permanecer en los módulos correspondientes.

---

# 9. Manejo de excepciones

Forge proporciona un `@RestControllerAdvice` para manejar errores comunes de aplicaciones Spring MVC.

La implementación actual contempla, entre otras:

| Excepción                                 | HTTP |
| ----------------------------------------- | ---: |
| `MethodArgumentNotValidException`         |  400 |
| `HttpMessageNotReadableException`         |  400 |
| `MissingServletRequestParameterException` |  400 |
| `MethodArgumentTypeMismatchException`     |  400 |
| `MissingRequestHeaderException`           |  400 |
| `NoResourceFoundException`                |  404 |
| `HttpRequestMethodNotSupportedException`  |  405 |
| Excepciones no esperadas                  |  500 |

Las excepciones específicas de Forge se resuelven mediante su `ErrorType` correspondiente.

El consumidor puede proporcionar su propio `@RestControllerAdvice` cuando necesite un comportamiento diferente.

Forge no requiere que el `@RestControllerAdvice` del consumidor extienda una clase de Forge.

---

# 10. Respuestas exitosas

Forge proporciona modelos para respuestas exitosas, pero no obliga a la aplicación a utilizarlos.

El consumidor puede utilizar sus propios modelos:

```java
CreateUserResponse create(
        @Valid @RequestBody CreateUserRequest request)
```

o utilizar explícitamente:

```java
SuccessResponse<CreateUserResponse> create(
        @Valid @RequestBody CreateUserRequest request)
```

o:

```java
ResponseEntity<SuccessResponse<CreateUserResponse>> create(
        @Valid @RequestBody CreateUserRequest request)
```

La librería no debe envolver automáticamente una respuesta existente únicamente para imponer el formato de Forge.

El contrato de la aplicación tiene prioridad cuando el consumidor decide no utilizar `SuccessResponse`.

---

# 11. Respuestas de error

Las respuestas de error utilizan `ErrorResponse` y `ErrorDetail`.

Un error puede contener:

* `code`;
* `field`;
* `message`;
* `details`.

Los valores opcionales pueden permanecer ausentes cuando no sean necesarios.

Forge no obliga al consumidor a definir códigos internos específicos para cada error de validación.

La aplicación puede utilizar códigos propios cuando necesite una identificación más específica.

El diseño de los errores toma como referencia los principios de **Problem Details for HTTP APIs (RFC 9457)** cuando son aplicables.

---

# 12. Serialización

Forge mantiene los modelos del Core independientes de Jackson.

La integración con Jackson pertenece al módulo `forge-web-autoconfigure` y es opcional.

Cuando Jackson está disponible, Forge puede aplicar reglas específicas de serialización mediante mecanismos de integración como Jackson Mixins.

La política de serialización evita incluir propiedades `null` o valores vacíos cuando no aportan información al consumidor.

Las respuestas exitosas mantienen `data` en el contrato incluso cuando su valor sea `null`.

Esta integración no introduce una dependencia de Jackson en `forge-web-core`.

---

# 13. AutoConfiguration

La integración automática debe seguir las convenciones de Spring Boot.

Forge utiliza:

```text
META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

para registrar su AutoConfiguration.

La configuración debe:

* activarse automáticamente en el caso común;
* permitir desactivación cuando exista una propiedad correspondiente;
* evitar registrar beans innecesarios;
* respetar beans definidos por el consumidor cuando corresponda.

Forge no debe modificar propiedades globales de la aplicación sin una configuración explícita.

---

# 14. API pública

Todo elemento `public` expuesto por Forge debe considerarse parte potencial del contrato público.

Antes de hacer público un tipo debe existir una razón concreta.

Preferir visibilidad restringida para componentes internos.

No deben exponerse clases internas, helpers o implementaciones únicamente porque sean utilizadas por otros componentes del mismo módulo.

---

# 15. Límites de la arquitectura

Forge no pretende:

* reemplazar Spring Boot;
* implementar lógica de negocio;
* imponer una arquitectura de aplicación;
* abstraer todas las APIs de Spring;
* proporcionar una solución para todos los problemas web;
* proporcionar mecanismos propios de Request ID;
* implementar trazabilidad distribuida;
* proporcionar logging HTTP;
* implementar una plataforma general de observabilidad;
* anticipar funcionalidades futuras mediante capas o abstracciones innecesarias.

Forge proporciona una base técnica pequeña y reutilizable para la estandarización de respuestas HTTP y el manejo de errores.

---

# 16. Evolución de la arquitectura

Forge no mantiene una lista de funcionalidades futuras que deban implementarse obligatoriamente.

Una nueva funcionalidad debe incorporarse únicamente cuando exista una necesidad concreta y observable.

Antes de crear un nuevo módulo, interfaz, abstracción o patrón debe determinarse:

1. qué problema concreto resuelve;
2. quién consume la funcionalidad;
3. si existe una API estándar que ya lo resuelva;
4. si puede implementarse de forma más sencilla;
5. si necesita realmente ser extensible;
6. si debe formar parte de la API pública;
7. si pertenece al ámbito de responsabilidad de Forge;
8. si puede incorporarse sin afectar funcionalidades existentes.

Una funcionalidad que pertenezca claramente a otro ámbito debe permanecer fuera de Forge.

---

# 17. Regla arquitectónica principal

La arquitectura de Forge debe evolucionar desde las necesidades reales del producto.

No se introducirán componentes únicamente porque sean técnicamente posibles.

Cada módulo, clase, dependencia y abstracción debe justificar su existencia mediante un beneficio concreto para el consumidor.

Si una abstracción no aporta un beneficio observable al consumidor, no debe introducirse.
