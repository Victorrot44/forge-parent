# Forge Web Architecture

> **Version:** 1.0.0 (Draft)
> **Project:** forge-web
> **GroupId:** io.github.victorrot44
> **Artifact:** forge-parent

---

# 1. Introducción

## 1.1 Propósito

Forge Web es una librería orientada al ecosistema Spring Boot cuyo objetivo es proporcionar un conjunto de componentes reutilizables para construir aplicaciones web con un comportamiento uniforme, desacoplado y fácil de mantener.

Forge busca eliminar la necesidad de que cada proyecto implemente nuevamente aspectos transversales como:

* Contratos estándar de respuesta.
* Manejo centralizado de excepciones.
* Auditoría basada en Request ID.
* Contexto de ejecución.
* Logging consistente.
* Auto configuración mediante Spring Boot.
* Componentes reutilizables para aplicaciones REST.

La filosofía de Forge consiste en ofrecer una experiencia **plug & play**, donde un desarrollador pueda integrar la librería en pocos minutos sin perder la capacidad de personalización.

---

# 2. Objetivos

Forge tiene como objetivos principales:

* Estandarizar la comunicación entre aplicaciones.
* Reducir código repetitivo.
* Mejorar la trazabilidad de las peticiones.
* Facilitar la observabilidad.
* Promover buenas prácticas de diseño.
* Mantener independencia del framework siempre que sea posible.
* Facilitar la evolución futura del proyecto.

---

# 3. Principios Arquitectónicos

Toda decisión dentro del proyecto deberá respetar los siguientes principios.

## 3.1 SOLID

Todos los componentes deberán cumplir los principios SOLID.

No se aceptarán implementaciones que sacrifiquen mantenibilidad por conveniencia.

---

## 3.2 Clean Architecture

La lógica del dominio nunca dependerá de frameworks.

Las dependencias deberán apuntar siempre hacia componentes más abstractos.

---

## 3.3 Framework Agnostic

El módulo Core no dependerá de:

* Spring Framework
* Spring Boot
* Jakarta Servlet
* WebFlux
* Reactor
* Librerías HTTP

Toda integración con tecnologías específicas deberá realizarse en módulos dedicados.

---

## 3.4 Open for Extension

Forge deberá permitir extender su comportamiento sin modificar su implementación base.

Siempre que sea posible se preferirá composición sobre herencia.

---

## 3.5 Backward Compatibility

Las APIs públicas deberán evolucionar procurando mantener compatibilidad entre versiones menores.

Los cambios incompatibles deberán reservarse para versiones mayores.

---

# 4. Arquitectura Modular

Forge está compuesto por módulos con responsabilidades claramente definidas.

```
forge-parent
│
├── docs
├── forge-web-core
├── forge-web-autoconfigure
├── forge-web-starter
├── forge-web-test
└── examples
```

Cada módulo deberá tener una única responsabilidad.

No se permitirá mezclar responsabilidades entre módulos.

---

# 5. Responsabilidad de cada módulo

## forge-web-core

Contiene toda la lógica independiente de frameworks.

Responsabilidades:

* Responses
* Exceptions
* Error Model
* Context
* Factories
* Validaciones
* Contratos públicos

No depende de Spring Boot.

No conoce HTTP.

No conoce Servlet.

No conoce Reactor.

Debe poder utilizarse en cualquier aplicación Java.

---

## forge-web-autoconfigure

Contiene la configuración automática.

Responsabilidades:

* AutoConfiguration
* Beans por defecto
* Properties
* Conditional Beans

No contiene lógica de negocio.

---

## forge-web-starter

Integra Forge con Spring Boot.

Responsabilidades:

* Filters
* Controller Advice
* Context Provider
* Logging
* RequestId
* Integración HTTP

Todo componente dependiente de Spring deberá vivir en este módulo.

---

## forge-web-test

Componentes reutilizables para pruebas.

Incluye:

* Test Utilities
* Assertions
* Mocks
* Helpers

---

## examples

Aplicaciones de ejemplo.

Su único propósito es demostrar el uso de Forge.

---

# 6. Reglas de Dependencia

Las dependencias deberán seguir el siguiente flujo.

```
starter
      │
      ▼
autoconfigure
      │
      ▼
core
```

Nunca se permitirá una dependencia en sentido contrario.

---

# 7. Filosofía del Core

El módulo Core representa el corazón del proyecto.

Debe permanecer pequeño, estable y completamente independiente.

Todo componente del Core deberá poder reutilizarse fuera del ecosistema Spring Boot.

---

# 8. Convenciones de Diseño

Forge adopta las siguientes convenciones.

* Uso preferente de records para modelos inmutables.
* Uso de sealed interfaces cuando exista un conjunto cerrado de implementaciones.
* Builders únicamente cuando el número de parámetros lo justifique.
* Objetos inmutables siempre que sea posible.
* Validaciones en constructores compactos.
* APIs simples para los casos comunes.
* APIs avanzadas mediante Builders.

---

# 9. Principios de Extensibilidad

Forge deberá permitir:

* reemplazar implementaciones;
* registrar nuevos componentes;
* extender respuestas;
* crear nuevos ErrorDescriptor;
* implementar nuevos ContextProvider;
* integrar nuevos mecanismos de logging.

Sin modificar el código del Core.

---

# 10. Visión a Futuro

La arquitectura ha sido diseñada para permitir futuras extensiones, entre ellas:

* soporte para WebFlux;
* observabilidad con Micrometer;
* integración con OpenTelemetry;
* propagación distribuida de RequestId;
* internacionalización de mensajes;
* soporte para Problem Details (RFC 9457);
* integración con clientes HTTP;
* módulos adicionales para distintos ecosistemas.

Estas funcionalidades deberán implementarse como módulos independientes siempre que sea posible.

---

# 11. Objetivo Final

Forge aspira a convertirse en una librería de referencia para aplicaciones Spring Boot que permita construir servicios consistentes, observables y mantenibles mediante una arquitectura modular, extensible y desacoplada.
 