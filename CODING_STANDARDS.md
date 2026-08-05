# Coding Standards

Este documento define los estándares de desarrollo utilizados en Forge.

Su objetivo es mantener una arquitectura consistente, una API pública pequeña y un código claro, predecible y mantenible.

Las reglas aplican a todo el proyecto y deben interpretarse junto con:

* `PHILOSOPHY.md`
* `ARCHITECTURE.md`
* `DESIGN_DECISIONS.md`

Cuando una regla de estilo entre en conflicto con una decisión arquitectónica concreta, prevalecerá la arquitectura y el contrato público de Forge.

---

# Principios generales

Forge prioriza:

* simplicidad;
* claridad;
* mantenibilidad;
* bajo acoplamiento;
* alta cohesión;
* APIs pequeñas;
* inmutabilidad cuando sea apropiada;
* composición cuando aporte valor;
* evitar abstracciones especulativas;
* utilizar estándares existentes antes de crear soluciones propias.

Los principios de diseño deben utilizarse como herramientas, no como objetivos de cobertura.

No se introducirá una abstracción únicamente para cumplir un patrón o principio.

---

# Principios de diseño

Forge utiliza los siguientes principios cuando sean apropiados:

* KISS — Keep It Simple.
* DRY — Don't Repeat Yourself.
* YAGNI — You Aren't Gonna Need It.
* responsabilidad única;
* composición sobre herencia;
* bajo acoplamiento;
* alta cohesión.

**SOLID no implica que toda clase deba tener una interfaz ni que toda dependencia deba abstraerse.**

Una abstracción debe existir únicamente cuando resuelva una necesidad concreta del diseño o proporcione un punto de extensión real.

---

# Organización de paquetes

Los paquetes representan responsabilidades concretas.

Ejemplos:

```text
context/
error/
exception/
response/
validation/
```

Evitar paquetes genéricos como:

```text
util/
common/
misc/
manager/
```

Los paquetes como `helper` deben utilizarse únicamente cuando exista una responsabilidad claramente definida que justifique su existencia.

Una clase no debe colocarse en un paquete genérico simplemente porque no se sabe dónde pertenece.

---

# Organización de clases

Cada clase debe tener una responsabilidad clara.

Una clase no debe mezclar responsabilidades independientes como:

* lógica de negocio;
* acceso a datos;
* configuración;
* serialización;
* integración con frameworks.

La separación debe realizarse cuando aporte claridad o reduzca acoplamiento.

No se deben crear clases únicamente para dividir código artificialmente.

---

# Uso de `record`

Utilizar `record` cuando el tipo represente principalmente datos y pueda beneficiarse de la inmutabilidad estructural.

Ejemplos:

* respuestas;
* metadata;
* detalles de error;
* objetos de configuración inmutables;
* DTOs cuando sea apropiado.

Los `record` no deben utilizarse cuando el tipo requiera una identidad mutable o un modelo de comportamiento incompatible con sus características.

---

# Uso de `class`

Utilizar `class` cuando el tipo necesite comportamiento, estado, construcción especializada o integración con frameworks.

Ejemplos:

* builders;
* handlers;
* componentes de Spring;
* clases utilitarias;
* implementaciones con comportamiento.

Una clase no debe introducirse únicamente para envolver otra clase sin aportar comportamiento o un contrato necesario.

---

# Uso de interfaces

Las interfaces representan contratos o puntos de extensión reales.

No crear una interfaz únicamente para cumplir una regla de diseño.

Una única implementación puede justificar una interfaz cuando exista una razón concreta, por ejemplo:

* un contrato público;
* sustitución legítima por parte del consumidor;
* desacoplamiento necesario entre módulos;
* integración con una implementación externa;
* extensión prevista y justificada.

Cuando no exista una necesidad de abstracción, una clase concreta es preferible.

---

# Herencia

La herencia debe utilizarse únicamente cuando exista una relación conceptual real entre los tipos.

No utilizar herencia únicamente para reutilizar código.

Preferir composición cuando proporcione un diseño más simple y claro.

---

# Uso de `final`

Las clases concretas deben declararse `final` cuando no exista una razón para permitir herencia.

No hacer una clase extensible simplemente por precaución.

La extensibilidad debe ser intencional.

---

# Inmutabilidad

Los objetos públicos deben ser inmutables siempre que sea razonablemente posible.

Cuando una colección forme parte de un objeto público y deba conservarse de forma inmutable, utilizar:

```java
List.copyOf(...)
Map.copyOf(...)
Set.copyOf(...)
```

No exponer referencias mutables internas.

---

# Null Handling

`null` puede utilizarse cuando represente un estado válido del modelo.

No se debe introducir `null` como sustituto de una decisión de diseño.

Cuando exista una alternativa claramente superior, pueden utilizarse:

* colecciones vacías;
* tipos específicos;
* valores por defecto;
* `Optional` en retornos apropiados.

No convertir la eliminación absoluta de `null` en una regla artificial.

---

# Optional

`Optional` debe utilizarse principalmente como valor de retorno cuando represente correctamente la ausencia de un resultado.

Evitar utilizar `Optional` como:

* campo;
* parámetro de método;
* elemento de una colección.

No utilizar `Optional` cuando un tipo concreto o una colección vacía exprese mejor el contrato.

---

# Validaciones

Las validaciones deben mantenerse cerca de la responsabilidad que protegen.

Las validaciones genéricas y reutilizables pueden centralizarse en `Preconditions`.

No mover una validación a `Preconditions` únicamente para evitar unas pocas líneas duplicadas.

La centralización debe aportar reutilización o consistencia real.

---

# Builders

Utilizar un Builder cuando un objeto tenga una construcción suficientemente compleja como para justificarlo.

Los Builders deben:

* mantener una API fluida;
* construir objetos inmutables;
* validar el estado final cuando corresponda;
* evitar lógica de negocio innecesaria.

No crear Builders para objetos cuya construcción directa sea suficientemente clara.

---

# Excepciones

Las excepciones específicas de Forge deben utilizar la jerarquía definida por Forge cuando corresponda.

No todas las excepciones utilizadas internamente deben extender `ForgeException`.

Las excepciones deben representar correctamente su naturaleza.

En particular, debe distinguirse entre:

* errores controlados que forman parte del contrato de Forge;
* errores internos;
* excepciones provenientes de frameworks o dependencias externas.

No envolver una excepción únicamente para cambiar su tipo si esto no aporta valor.

---

# Enumeraciones

Las enumeraciones representan catálogos o conjuntos de valores conocidos.

Deben contener únicamente la información y comportamiento directamente relacionado con sus valores.

Evitar introducir lógica compleja en un `enum`.

---

# Métodos

Los métodos deben:

* realizar una tarea clara;
* tener nombres descriptivos;
* minimizar efectos secundarios;
* evitar complejidad innecesaria;
* mantener una longitud razonable.

Si un método requiere comentarios extensos para explicar su funcionamiento, debe evaluarse si el diseño puede hacerse más claro.

Los comentarios deben explicar **por qué**, no repetir lo que el código ya expresa.

---

# JavaDoc

La API pública debe estar documentada.

Especialmente:

* clases públicas;
* interfaces públicas;
* métodos públicos;
* records públicos;
* configuraciones públicas;
* contratos destinados al consumidor.

El JavaDoc debe explicar el propósito, comportamiento y restricciones relevantes.

No debe limitarse a repetir la firma del método.

Las APIs internas no requieren JavaDoc exhaustivo cuando el código sea suficientemente claro.

---

# Dependencias

Antes de agregar una dependencia debe evaluarse:

* ¿es realmente necesaria?
* ¿el JDK ya proporciona la funcionalidad?
* ¿Spring ya proporciona una solución adecuada?
* ¿existe un estándar HTTP o Java que resuelva el problema?
* ¿qué dependencias transitivas introduce?
* ¿incrementa significativamente la complejidad o el tamaño del proyecto?
* ¿aporta suficiente valor para justificar su mantenimiento?

Forge prioriza mantener un conjunto pequeño de dependencias.

No introducir una dependencia para resolver un problema que puede solucionarse razonablemente con las capacidades existentes.

---

# Independencia del Core

El módulo `forge-web-core` no debe depender de Spring ni de APIs específicas de infraestructura web.

No debe depender de:

* Spring Framework;
* Spring Boot;
* Jakarta Servlet;
* WebFlux;
* Reactor;
* Jackson.

La integración con frameworks pertenece a los módulos correspondientes.

El Core puede definir modelos y contratos relacionados con el dominio funcional de Forge sin depender de la implementación del framework.

---

# API pública

Todo elemento público de Forge debe considerarse un contrato.

Antes de hacer pública una clase, método, record, enum, interfaz o configuración debe existir una razón concreta para exponerlo.

Preferir la visibilidad más restrictiva posible.

No exponer tipos internos únicamente porque una implementación los utiliza.

Una vez publicada una API, cualquier modificación debe considerar:

* compatibilidad;
* consumidores existentes;
* evolución futura;
* posibilidad de mantener el contrato.

---

# Compatibilidad

Las APIs públicas deben diseñarse pensando en su evolución.

Los cambios incompatibles deben evitarse dentro de una misma versión mayor.

Las versiones publicadas siguen Semantic Versioning:

```text
MAJOR.MINOR.PATCH
```

Los cambios incompatibles requieren una nueva versión mayor salvo que exista una estrategia explícita de migración compatible.

---

# Pruebas

Toda funcionalidad con comportamiento observable debe tener pruebas apropiadas.

Dependiendo del cambio podrán utilizarse:

* pruebas unitarias;
* pruebas de integración;
* pruebas de regresión.

Las pruebas deben validar comportamiento y contratos relevantes.

No se debe agregar cobertura únicamente para aumentar un porcentaje.

Una funcionalidad no debe considerarse terminada si carece de las pruebas necesarias para proteger su comportamiento.

---

# Estilo de nombres

Los nombres deben ser descriptivos y representar claramente su responsabilidad.

Ejemplos:

```text
ErrorResponse
ErrorDetail
ForgeException
ErrorTypeStatusMapper
ForgeWebExceptionHandler
```

Los sufijos deben utilizarse únicamente cuando describan una responsabilidad real.

Ejemplos válidos:

```text
Builder
Mapper
Handler
Validator
```

Evitar nombres genéricos como:

```text
Manager
Helper
Util
Processor
Common
```

salvo que la responsabilidad esté claramente definida y justifique el nombre.

---

# Reglas para el Core

`forge-web-core` constituye la base independiente de Forge.

Por ello:

* no depende de Spring;
* no depende de Spring Boot;
* no depende de Servlet;
* no depende de Reactor;
* no depende de WebFlux;
* no depende de Jackson;
* no contiene configuración específica de frameworks.

El Core debe concentrarse en:

* modelos;
* contratos;
* respuestas;
* errores;
* excepciones;
* validaciones;
* lógica reutilizable independiente del framework.

---

# Regla de oro

Antes de crear una nueva clase, interfaz, abstracción o módulo, pregúntate:

* ¿Existe una necesidad concreta?
* ¿Aporta valor observable al consumidor?
* ¿Pertenece realmente a este módulo?
* ¿Puede resolverse con una API estándar existente?
* ¿Puede simplificarse?
* ¿Es necesario exponerlo públicamente?
* ¿Respeta la filosofía de Forge?

Si la respuesta es negativa, replantea el diseño antes de continuar.

**Forge no debe crecer por cantidad de código, sino por valor aportado.**
