# Contributing to Forge

¡Gracias por tu interés en contribuir a Forge!

Toda contribución, ya sea una corrección, una nueva funcionalidad, una mejora en la documentación o un reporte de errores, ayuda a construir una mejor librería para la comunidad.

---

# Código de conducta

Esperamos que todas las interacciones dentro del proyecto sean respetuosas, profesionales y constructivas.

Las discusiones técnicas son bienvenidas, pero siempre deben centrarse en mejorar el proyecto y no en las personas.

---

# Antes de comenzar

Antes de implementar una nueva funcionalidad, verifica si:

* ya existe una propuesta similar;
* el cambio está alineado con `PHILOSOPHY.md`;
* respeta la arquitectura descrita en `ARCHITECTURE.md`;
* no rompe la compatibilidad de la API pública.

Si tienes dudas, abre primero una discusión o un Issue.

---

# Cómo contribuir

## Reportar errores

Al crear un Issue incluye, siempre que sea posible:

* versión de Forge;
* versión de Java;
* versión de Spring Boot;
* sistema operativo;
* descripción del problema;
* pasos para reproducirlo;
* comportamiento esperado;
* comportamiento actual.

Mientras más información proporciones, más sencillo será analizar el problema.

---

## Proponer funcionalidades

Antes de solicitar una nueva característica, pregúntate:

* ¿Resuelve un problema recurrente?
* ¿Puede beneficiar a la mayoría de los usuarios?
* ¿Mantiene la simplicidad de Forge?
* ¿Encaja con la filosofía del proyecto?

No todas las propuestas serán aceptadas.

Forge prioriza una API pequeña y consistente sobre incorporar una gran cantidad de funcionalidades.

---

## Enviar un Pull Request

1. Haz un Fork del proyecto.
2. Crea una rama descriptiva.

```text
feature/request-id
feature/context
fix/error-builder
docs/readme
refactor/response-factory
```

3. Implementa únicamente un cambio por Pull Request.
4. Agrega o actualiza las pruebas necesarias.
5. Actualiza la documentación cuando corresponda.
6. Verifica que el proyecto compile correctamente.
7. Envía el Pull Request.

---

# Estándares de calidad

Toda contribución debe cumplir los siguientes requisitos:

* El proyecto compila sin errores.
* No existen advertencias importantes.
* Las pruebas existentes continúan funcionando.
* Las nuevas funcionalidades incluyen pruebas.
* El código sigue los estándares definidos por Forge.
* La documentación permanece actualizada.

---

# Principios de desarrollo

Forge sigue los principios descritos en `PHILOSOPHY.md`.

En particular:

* SOLID.
* Responsabilidad única.
* Composición sobre herencia.
* Objetos inmutables siempre que sea posible.
* APIs pequeñas.
* Bajo acoplamiento.
* Alta cohesión.

---

# Compatibilidad

Las modificaciones no deben romper la compatibilidad pública sin una justificación sólida.

Cuando un cambio implique romper compatibilidad deberá discutirse antes de ser aceptado.

Forge utiliza Versionado Semántico (Semantic Versioning).

---

# Documentación

Toda funcionalidad pública deberá estar documentada.

Esto incluye:

* JavaDoc;
* ejemplos de uso;
* documentación en `docs/` cuando corresponda.

La documentación forma parte del código y debe mantenerse sincronizada con él.

---

# Pruebas

Toda nueva funcionalidad deberá incluir pruebas apropiadas.

Dependiendo del cambio podrán requerirse:

* pruebas unitarias;
* pruebas de integración;
* pruebas de regresión.

No se aceptarán funcionalidades que reduzcan la estabilidad del proyecto.

---

# Estilo de código

Forge utiliza un estilo de código consistente en todos sus módulos.

Algunas reglas generales:

* nombres descriptivos;
* clases pequeñas;
* métodos cortos;
* evitar duplicación;
* evitar complejidad innecesaria;
* preferir composición;
* minimizar el estado mutable.

Consulta `CODING_STANDARDS.md` para conocer todas las convenciones.

---

# Dependencias

Antes de agregar una nueva dependencia considera:

* ¿es realmente necesaria?
* ¿puede resolverse con el JDK?
* ¿incrementa significativamente el tamaño del proyecto?
* ¿afecta la mantenibilidad?

Forge intenta mantener un conjunto mínimo de dependencias.

---

# Filosofía para aceptar cambios

Generalmente un Pull Request será aceptado cuando:

* simplifique la API;
* mejore la mantenibilidad;
* reduzca código repetitivo;
* mejore el rendimiento sin sacrificar legibilidad;
* mantenga la filosofía del proyecto.

Un Pull Request probablemente será rechazado cuando:

* aumente innecesariamente la complejidad;
* introduzca múltiples responsabilidades;
* agregue configuraciones innecesarias;
* rompa la consistencia del proyecto;
* no aporte un beneficio claro para la mayoría de los usuarios.

---

# Gracias

Forge existe gracias a las personas que dedican tiempo a mejorar el proyecto.

Toda contribución, por pequeña que sea, es apreciada.
