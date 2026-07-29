# Modules Guide

> Proyecto: **Forge Web**

Este documento define la responsabilidad de cada módulo del proyecto y las reglas que deben respetarse al incorporar nuevas funcionalidades.

---

# Filosofía

Forge está construido bajo una arquitectura modular.

Cada módulo posee una única responsabilidad y únicamente conoce aquello que realmente necesita.

El objetivo es mantener un bajo acoplamiento, una alta cohesión y facilitar la evolución del proyecto.

---

# Estructura

```text
forge-parent
│
├── docs
├── forge-web-core
├── forge-web-autoconfigure
├── forge-web-starter
├── forge-web-test
└── examples
```

---

# forge-web-core

## Responsabilidad

Contiene toda la lógica independiente de cualquier framework.

Representa el núcleo del proyecto.

Debe poder utilizarse en cualquier aplicación Java sin necesidad de Spring Boot.

---

## Contiene

* Responses
* Exceptions
* ErrorDescriptor
* Context
* Factories
* Interfaces
* Builders
* Validaciones
* Objetos inmutables
* Utilidades independientes del framework

---

## No contiene

* Spring Framework
* Spring Boot
* Jakarta Servlet
* WebFlux
* Reactor
* SLF4J
* Jackson
* HTTP
* REST
* Anotaciones específicas de frameworks

---

## Regla

Si una clase puede ejecutarse en una aplicación Java sin Spring, pertenece al Core.

---

# forge-web-autoconfigure

## Responsabilidad

Registrar automáticamente los componentes necesarios cuando Forge se incorpora a un proyecto Spring Boot.

---

## Contiene

* AutoConfiguration
* Beans por defecto
* ConfigurationProperties
* Conditional Beans
* Imports automáticos

---

## No contiene

* Filters
* ControllerAdvice
* Lógica HTTP
* Implementaciones de negocio

---

## Regla

Todo componente cuyo propósito sea registrar Beans automáticamente pertenece aquí.

---

# forge-web-starter

## Responsabilidad

Integrar Forge con Spring Boot.

Es el punto de entrada para las aplicaciones.

---

## Contiene

* Filters
* Interceptors
* Controller Advice
* Context Providers
* Integración HTTP
* Integración con Jackson
* Integración con Logging
* Manejo de Request ID
* Integraciones con Servlet

---

## No contiene

* Modelos del dominio
* Lógica de negocio
* Algoritmos reutilizables

---

## Regla

Todo aquello que dependa directamente del ciclo de vida de Spring Boot o HTTP pertenece al Starter.

---

# forge-web-test

## Responsabilidad

Facilitar las pruebas unitarias e integrales de aplicaciones que utilizan Forge.

---

## Contiene

* Assertions
* Builders de prueba
* Helpers
* Fixtures
* Context simulados
* Utilidades de testing

---

## No contiene

Código utilizado en producción.

---

## Regla

Todo componente cuyo único propósito sea facilitar pruebas pertenece aquí.

---

# examples

## Responsabilidad

Mostrar el uso correcto de Forge.

---

## Contiene

* Aplicaciones de ejemplo
* Casos de uso
* Ejemplos de integración

---

## No contiene

Código reutilizable.

---

# Dependencias permitidas

```text
examples
      │
      ▼
starter
      │
      ▼
autoconfigure
      │
      ▼
core
```

Las dependencias siempre deberán seguir esta dirección.

Nunca deberán invertirse.

---

# Reglas generales

## Regla 1

Nunca mover lógica del Core al Starter únicamente porque utilice Spring Boot.

---

## Regla 2

Nunca agregar dependencias de Spring dentro del Core.

---

## Regla 3

El Starter puede depender del Core.

El Core nunca dependerá del Starter.

---

## Regla 4

Si una funcionalidad puede implementarse sin Spring Boot, deberá vivir en el Core.

---

## Regla 5

Si una funcionalidad requiere el ciclo de vida de Spring Boot, deberá vivir en el Starter.

---

## Regla 6

La configuración automática siempre deberá implementarse en Autoconfigure.

---

## Regla 7

Cada módulo deberá tener una única responsabilidad.

Si una clase parece pertenecer a dos módulos, probablemente su diseño deba replantearse.

---

# Lista de verificación

Antes de agregar una nueva clase, responder las siguientes preguntas:

* ¿Depende de Spring Framework?
* ¿Depende de HTTP?
* ¿Puede ejecutarse fuera de Spring Boot?
* ¿Es reutilizable?
* ¿Su responsabilidad pertenece al Core o a la integración?

Las respuestas determinarán el módulo correcto.

---

# Objetivo

Mantener una arquitectura limpia, predecible y fácil de evolucionar, donde cada módulo tenga límites claros y responsabilidades bien definidas.
