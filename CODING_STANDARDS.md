# Coding Standards

Este documento define los estándares de desarrollo utilizados en Forge.

Su objetivo es garantizar que todos los módulos mantengan una arquitectura consistente, una API uniforme y un estilo de código predecible.

Las siguientes reglas aplican a todo el proyecto.

---

# Principios generales

Todo el código debe seguir los principios definidos en:

* `PHILOSOPHY.md`
* `ARCHITECTURE.md`
* `DESIGN_DECISIONS.md`

Cuando exista un conflicto entre una implementación y estos documentos, prevalecerá la arquitectura del proyecto.

---

# Principios de diseño

Todo el código debe respetar:

* SOLID.
* DRY (Don't Repeat Yourself).
* KISS (Keep It Simple).
* YAGNI (You Aren't Gonna Need It).
* Composition over Inheritance.
* Favor interfaces over implementations.

---

# Organización de paquetes

Los paquetes representan responsabilidades, no capas técnicas.

Ejemplo:

```text
context/
error/
exception/
factory/
response/
validation/
```

No deben existir paquetes genéricos como:

```text
util/
common/
helper/
misc/
manager/
```

Cuando una responsabilidad crezca deberá convertirse en un nuevo paquete.

---

# Organización de clases

Cada clase debe tener una única responsabilidad.

Una clase no debe mezclar:

* lógica de negocio;
* validaciones;
* acceso a datos;
* serialización;
* configuración.

---

# Uso de record

Utilizar `record` cuando el objeto:

* sea inmutable;
* represente datos;
* no mantenga estado mutable;
* no requiera herencia.

Ejemplos:

* DTO
* Response
* Context
* Metadata
* ErrorDetail

---

# Uso de class

Utilizar `class` cuando exista:

* comportamiento complejo;
* estado mutable;
* herencia;
* builders;
* estrategias;
* fábricas.

---

# Uso de interfaces

Las interfaces representan contratos.

No deben existir interfaces con una única implementación salvo que representen un punto claro de extensión.

Ejemplos válidos:

* ResponseFactory
* ForgeContextProvider
* ErrorDescriptor

---

# Uso de sealed

Utilizar `sealed` cuando el conjunto de implementaciones deba permanecer controlado por Forge.

Ejemplo:

```java
sealed interface ApiResponse
```

---

# Uso de non-sealed

Utilizar `non-sealed` únicamente cuando Forge permita explícitamente la extensión por parte del usuario.

Ejemplo:

```java
BusinessException
```

---

# Uso de final

Toda clase concreta debe declararse `final` salvo que exista una razón arquitectónica para permitir herencia.

La herencia nunca debe utilizarse únicamente para reutilizar código.

---

# Constructores

Utilizar `Objects.requireNonNull()` para validar dependencias obligatorias.

Ejemplo:

```java
Objects.requireNonNull(factory, "ResponseFactory must not be null.");
```

---

# Inmutabilidad

Todos los objetos públicos deben ser inmutables.

Cuando una colección sea expuesta públicamente deberá utilizar:

```java
List.copyOf(...)
Map.copyOf(...)
Set.copyOf(...)
```

Nunca deben exponerse referencias mutables.

---

# Null Handling

Evitar valores `null` siempre que sea posible.

Preferir:

* colecciones vacías;
* Optional;
* objetos inmutables.

Nunca retornar `null` cuando exista una alternativa más segura.

---

# Optional

`Optional` solo debe utilizarse como valor de retorno.

No utilizar:

* campos Optional;
* parámetros Optional;
* colecciones de Optional.

---

# Validaciones

Todas las validaciones comunes deberán centralizarse en `Preconditions`.

Ejemplo:

```java
Preconditions.requireNotNullOrEmpty(name);
```

Evitar duplicar validaciones entre módulos.

---

# Builders

Cuando un objeto posea una construcción compleja deberá utilizar un Builder.

Los Builders deben:

* mantener una API fluida;
* validar el objeto antes de construirlo;
* producir objetos inmutables.

---

# Excepciones

Todas las excepciones del proyecto deberán extender `ForgeException`.

No lanzar:

* RuntimeException
* Exception
* IllegalStateException

salvo cuando el error represente una falla interna del propio JDK o una precondición de programación.

---

# Enumeraciones

Las enumeraciones representan catálogos estables.

No deben contener lógica compleja.

Solo información y pequeñas utilidades derivadas de sus propios valores.

---

# Métodos

Los métodos deben:

* realizar una sola tarea;
* tener nombres descriptivos;
* minimizar efectos secundarios;
* evitar complejidad innecesaria.

Siempre que un método requiera comentarios para entenderse, debe evaluarse si puede simplificarse.

---

# JavaDoc

Todo elemento público deberá incluir JavaDoc.

Especialmente:

* interfaces;
* clases públicas;
* métodos públicos;
* API expuesta.

La documentación debe explicar el propósito del elemento, no repetir el código.

---

# Dependencias

Antes de agregar una nueva dependencia responder:

* ¿es realmente necesaria?
* ¿el JDK ya ofrece esta funcionalidad?
* ¿incrementa el tamaño del proyecto?
* ¿afecta el mantenimiento?

Forge prioriza mantener un conjunto mínimo de dependencias.

---

# Framework Independence

El módulo `forge-web-core` no debe depender de:

* Spring Framework;
* Spring Boot;
* Jakarta Servlet;
* Reactor;
* WebFlux;
* Jackson.

Toda integración con frameworks pertenece a módulos específicos.

---

# Compatibilidad

Toda API pública debe diseñarse pensando en compatibilidad futura.

Romper una API pública requerirá una nueva versión mayor siguiendo Semantic Versioning.

---

# Pruebas

Toda funcionalidad pública deberá incluir pruebas.

Se priorizan:

* pruebas unitarias;
* pruebas de integración;
* pruebas de regresión cuando aplique.

Una funcionalidad sin pruebas se considera incompleta.

---

# Estilo de nombres

Interfaces:

```text
ResponseFactory
ForgeContextProvider
ErrorDescriptor
```

Builders:

```text
Builder
```

Factories:

```text
DefaultResponseFactory
```

Providers:

```text
ForgeContextProvider
```

Resolvers:

```text
ExceptionResolver
```

---

# Reglas para el Core

El módulo `forge-web-core` es la base de Forge.

Por ello:

* no conoce HTTP como protocolo;
* no conoce Spring Boot;
* no conoce Servlet;
* no conoce Reactor;
* no conoce Jackson;
* no conoce anotaciones específicas de frameworks.

Su única responsabilidad es definir contratos, modelos y lógica reutilizable.

---

# Regla de oro

Antes de escribir una nueva clase, pregúntate:

* ¿Tiene una única responsabilidad?
* ¿Pertenece realmente a este módulo?
* ¿Es extensible sin modificarse?
* ¿Puede simplificarse?
* ¿Respeta la filosofía de Forge?

Si alguna respuesta es negativa, la implementación debe replantearse antes de continuar.
