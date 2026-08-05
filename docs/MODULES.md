# Modules Guide

> **Proyecto:** Forge Web
> **Versión:** 1.0.0

Este documento define la responsabilidad de cada módulo de Forge y las reglas para determinar dónde debe incorporarse una nueva funcionalidad.

La estructura debe mantenerse pequeña y evolucionar únicamente cuando exista una necesidad concreta.

---

# Filosofía

Forge utiliza una arquitectura modular para separar responsabilidades y mantener el Core independiente de Spring.

Cada módulo debe tener una responsabilidad clara.

La existencia de un módulo no justifica por sí misma la creación de nuevas abstracciones o componentes.

---

# Estructura actual

La versión `1.0.0` está compuesta por:

```text
forge-parent
│
├── docs
├── forge-web-core
├── forge-web-autoconfigure
└── forge-web-starter
```

`docs/` contiene documentación del proyecto y no constituye un módulo Maven.

No forman parte de la estructura actual de la versión `1.0.0` módulos como `forge-web-test` o `examples`.

Podrán incorporarse posteriormente cuando exista una necesidad concreta que justifique su creación.

---

# forge-web-core

## Responsabilidad

`forge-web-core` contiene los modelos, contratos y lógica que no requieren Spring ni infraestructura web.

Es el núcleo independiente de Forge.

---

## Contiene

Actualmente incluye principalmente:

* Responses;
* Error Responses;
* Error Details;
* Metadata;
* Error Types;
* Forge Exceptions;
* Builders;
* Validators;
* lógica independiente de frameworks.

Ejemplos de paquetes:

```text
io.github.victorrot44.forge.web.core
│
├── error
├── exception
└── response
```

---

## No contiene

El Core no debe depender de:

* Spring Framework;
* Spring Boot;
* Jakarta Servlet;
* WebFlux;
* Reactor;
* Jackson;
* implementaciones específicas de servidores HTTP.

Tampoco debe contener componentes específicos del ciclo de vida de Spring.

---

## Regla

Una clase pertenece al Core cuando su responsabilidad forma parte del núcleo funcional de Forge y puede implementarse sin depender de Spring.

La independencia de Spring es necesaria, pero por sí sola no determina la pertenencia de una clase al Core.

---

# forge-web-autoconfigure

## Responsabilidad

`forge-web-autoconfigure` contiene la integración automática de Forge con Spring Boot.

Su función es registrar y configurar los componentes necesarios para que Forge funcione automáticamente en una aplicación.

---

## Contiene

* `@AutoConfiguration`;
* `@ConfigurationProperties`;
* beans de Forge;
* condiciones de configuración;
* configuración opcional;
* registro de AutoConfiguration;
* componentes específicos de Spring necesarios para la integración.

---

## No contiene

No debe contener:

* lógica de negocio;
* modelos del Core;
* lógica duplicada del Core;
* funcionalidades que no estén relacionadas con la configuración e integración de Spring.

---

## Regla

La lógica de AutoConfiguration pertenece a este módulo.

Los componentes que requieren Spring pueden vivir aquí cuando su responsabilidad esté directamente relacionada con la configuración e integración automática de Forge.

---

# forge-web-starter

## Responsabilidad

`forge-web-starter` proporciona el punto de entrada conveniente para incorporar Forge a una aplicación Spring Boot.

Su objetivo principal es reunir las dependencias necesarias para utilizar Forge.

---

## Contiene

El Starter debe mantenerse deliberadamente pequeño.

Su responsabilidad principal es la composición de dependencias necesarias para el consumidor.

No debe duplicar la implementación de funcionalidades que pertenecen a `forge-web-autoconfigure`.

---

## No contiene

No debe contener:

* lógica de negocio;
* modelos del Core;
* implementaciones duplicadas de AutoConfiguration;
* handlers de excepciones;
* filtros;
* lógica HTTP;
* infraestructura específica que pertenezca a otros módulos.

---

## Regla

Si una funcionalidad necesita código específico de Spring Boot, debe implementarse en el módulo correspondiente de integración, no dentro del Starter únicamente porque el consumidor lo agrega mediante ese artefacto.

El Starter es principalmente el mecanismo de conveniencia para incorporar Forge.

---

# Módulos futuros

Forge puede incorporar nuevos módulos cuando una funcionalidad concreta lo justifique.

Por ejemplo:

```text
forge-web-test
forge-web-...
```

Sin embargo, un módulo futuro no debe crearse anticipadamente.

Antes de crear uno nuevo debe existir una responsabilidad suficientemente independiente que justifique su separación.

---

# Dependencias permitidas

La dirección de dependencias actual es:

```text
forge-web-starter
        │
        ▼
forge-web-autoconfigure
        │
        ▼
forge-web-core
```

Los módulos superiores pueden depender de módulos inferiores.

Los módulos inferiores no deben depender de módulos superiores.

No deben existir dependencias circulares.

---

# Regla de separación

La ubicación de una funcionalidad debe determinarse por:

1. su responsabilidad;
2. sus dependencias;
3. el nivel de integración requerido;
4. su relación con el contrato público de Forge.

No debe determinarse únicamente por el hecho de utilizar o no Spring.

---

# Reglas generales

## Regla 1 — Core independiente

Nunca agregar dependencias de Spring, Servlet, Reactor o Jackson al Core.

---

## Regla 2 — No duplicar responsabilidades

Una funcionalidad debe tener una única ubicación principal.

No duplicar lógica entre Core, AutoConfiguration y Starter.

---

## Regla 3 — AutoConfiguration separada

La configuración automática debe permanecer en `forge-web-autoconfigure`.

---

## Regla 4 — Starter pequeño

No utilizar el Starter como contenedor general de funcionalidades.

Su objetivo es proporcionar una integración conveniente y estable para el consumidor.

---

## Regla 5 — Dependencias unidireccionales

Las dependencias deben apuntar hacia el Core.

```text
starter → autoconfigure → core
```

Nunca en sentido contrario.

---

## Regla 6 — No crear módulos anticipadamente

Una posible funcionalidad futura no justifica crear un módulo antes de que exista una necesidad concreta.

---

## Regla 7 — No mover código sin necesidad

Una clase no debe moverse entre módulos únicamente para cumplir una estructura teórica.

El cambio debe estar justificado por responsabilidad, dependencia o evolución de la arquitectura.

---

# Cómo decidir dónde colocar una nueva funcionalidad

Antes de agregar una clase o componente, responder:

### 1. ¿Forma parte del núcleo funcional de Forge?

Si sí, evaluar `forge-web-core`.

### 2. ¿Requiere Spring Boot para funcionar?

Si sí, evaluar `forge-web-autoconfigure` o un futuro módulo específico de integración.

### 3. ¿Es únicamente composición de dependencias?

Si sí, corresponde al `forge-web-starter`.

### 4. ¿Introduce una responsabilidad suficientemente independiente?

Si sí, evaluar si justifica un nuevo módulo.

### 5. ¿Es una funcionalidad futura que todavía no existe?

Si sí, no crear el módulo anticipadamente.

---

# Objetivo

Mantener una arquitectura pequeña, predecible y fácil de evolucionar.

Cada módulo debe existir porque resuelve una responsabilidad concreta, no porque una arquitectura idealizada requiera más capas.

La estructura de Forge debe crecer junto con sus funcionalidades reales.
