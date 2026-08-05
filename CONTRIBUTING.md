# Contributing to Forge

¡Gracias por tu interés en contribuir a Forge!

Toda contribución, ya sea una corrección, una nueva funcionalidad, una mejora en la documentación o un reporte de errores, ayuda a construir una librería más útil, estable y mantenible.

---

# Código de conducta

Esperamos que todas las interacciones dentro del proyecto sean respetuosas, profesionales y constructivas.

Las discusiones técnicas son bienvenidas y deben centrarse en mejorar el proyecto y sus decisiones de diseño.

---

# Antes de comenzar

Antes de implementar un cambio, verifica si:

* ya existe una propuesta o Issue relacionado;
* el cambio está alineado con `PHILOSOPHY.md`;
* respeta la arquitectura descrita en `ARCHITECTURE.md`;
* mantiene la API pública pequeña;
* evita introducir abstracciones especulativas;
* no duplica una capacidad que ya proporciona Java, HTTP o Spring;
* no rompe la compatibilidad pública existente.

Para cambios importantes se recomienda abrir primero una discusión o Issue.

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
* comportamiento actual;
* stack trace o logs relevantes.

Mientras más información proporciones, más sencillo será analizar y reproducir el problema.

---

## Proponer funcionalidades

Antes de solicitar una nueva característica, considera:

* ¿Resuelve un problema recurrente?
* ¿Aporta un beneficio observable al consumidor?
* ¿Reduce código repetitivo o complejidad?
* ¿Mantiene la simplicidad de Forge?
* ¿Puede implementarse sin introducir abstracciones innecesarias?
* ¿Existe una API estándar que ya resuelva el problema?
* ¿Encaja con la filosofía del proyecto?
* ¿Puede incorporarse de forma independiente?

No todas las propuestas serán aceptadas.

Forge prioriza una API pequeña, consistente y mantenible sobre incorporar una gran cantidad de funcionalidades.

---

# Enviar un Pull Request

1. Haz un Fork del proyecto.
2. Crea una rama descriptiva.
3. Implementa un cambio enfocado.
4. Agrega o actualiza las pruebas necesarias.
5. Actualiza la documentación cuando corresponda.
6. Verifica que el proyecto compile correctamente.
7. Verifica que las pruebas existentes continúen funcionando.
8. Envía el Pull Request.

Ejemplos:

```text id="h7u9zq"
feature/request-id
feature/http-logging
fix/error-builder
fix/method-not-allowed
docs/readme
refactor/response-builder
```

El nombre de la rama debe describir el cambio realizado y no la implementación interna utilizada.

---

# Estándares de calidad

Toda contribución debe cumplir los siguientes requisitos:

* El proyecto compila sin errores.
* Las pruebas existentes continúan funcionando.
* Las nuevas funcionalidades incluyen pruebas apropiadas.
* No se introducen advertencias innecesarias.
* El código mantiene las convenciones del proyecto.
* La API pública permanece clara y mínima.
* La documentación permanece actualizada cuando el cambio afecta al comportamiento público.

---

# Principios de desarrollo

Forge sigue los principios definidos en `PHILOSOPHY.md`.

En particular:

* simplicidad antes que complejidad;
* responsabilidad clara;
* APIs pequeñas;
* bajo acoplamiento;
* alta cohesión;
* inmutabilidad cuando sea apropiada;
* composición cuando aporte valor;
* evitar abstracciones especulativas;
* utilizar estándares existentes antes de crear soluciones propias.

Los principios de diseño deben aplicarse con criterio.

No se introducirá una interfaz, patrón, clase o abstracción únicamente para cumplir una regla de diseño.

---

# API pública

Todo elemento expuesto como parte de la API pública de Forge debe considerarse un contrato.

Antes de modificar o eliminar una API pública debe evaluarse:

* compatibilidad;
* impacto para los consumidores;
* posibilidad de mantener compatibilidad;
* necesidad real del cambio.

Una API pública no debe exponerse simplemente porque una implementación interna podría necesitarla.

La visibilidad debe mantenerse lo más restrictiva posible.

---

# Compatibilidad

Las modificaciones no deben romper la compatibilidad pública sin una justificación sólida.

Cuando un cambio implique romper compatibilidad, deberá discutirse antes de ser aceptado y documentarse adecuadamente.

Forge utiliza Versionado Semántico (Semantic Versioning) para sus versiones publicadas.

---

# Documentación

La documentación forma parte del proyecto y debe mantenerse sincronizada con el comportamiento real de Forge.

Toda API pública deberá contar con documentación apropiada.

Dependiendo del cambio, esto puede incluir:

* JavaDoc;
* ejemplos de uso;
* documentación en `docs/`;
* actualización del `README.md`.

La documentación no debe describir funcionalidades que todavía no existan.

---

# Pruebas

Toda nueva funcionalidad deberá incluir las pruebas apropiadas.

Dependiendo del cambio podrán requerirse:

* pruebas unitarias;
* pruebas de integración;
* pruebas de regresión.

Las pruebas deben validar comportamiento observable y contratos relevantes.

No se debe agregar cobertura únicamente para aumentar un porcentaje de cobertura.

---

# Estilo de código

Forge utiliza un estilo de código consistente en todos sus módulos.

Algunas reglas generales:

* nombres descriptivos;
* clases pequeñas cuando sea apropiado;
* métodos enfocados;
* evitar duplicación significativa;
* evitar complejidad innecesaria;
* minimizar el estado mutable;
* preferir soluciones simples;
* evitar abstracciones sin una necesidad concreta.

Las decisiones de diseño deben priorizar la claridad y el mantenimiento a largo plazo.

---

# Dependencias

Antes de agregar una nueva dependencia considera:

* ¿es realmente necesaria?
* ¿puede resolverse correctamente con el JDK?
* ¿puede resolverse con una API estándar de Spring?
* ¿introduce una dependencia transitiva innecesaria?
* ¿incrementa significativamente la complejidad o tamaño del proyecto?
* ¿aporta un beneficio suficiente para justificar su mantenimiento?

Forge intenta mantener un conjunto mínimo de dependencias.

Una dependencia no debe incorporarse únicamente para resolver un problema que puede solucionarse razonablemente con las capacidades existentes de Java o Spring.

---

# Filosofía para aceptar cambios

Generalmente un Pull Request será aceptado cuando:

* aporte un beneficio claro al consumidor;
* simplifique el uso de Forge;
* mejore la mantenibilidad;
* reduzca código repetitivo;
* mantenga la consistencia del proyecto;
* utilice estándares existentes cuando sean adecuados;
* incluya las pruebas necesarias;
* mantenga una API pública pequeña.

Un Pull Request probablemente será rechazado cuando:

* aumente innecesariamente la complejidad;
* introduzca abstracciones especulativas;
* agregue configuraciones innecesarias;
* duplique capacidades existentes de Java, HTTP o Spring;
* rompa la consistencia del proyecto;
* introduzca una dependencia innecesaria;
* no aporte un beneficio claro para el consumidor.

---

# Gracias

Forge existe gracias a las personas que dedican tiempo a mejorar el proyecto.

Toda contribución, por pequeña que sea, es apreciada.
