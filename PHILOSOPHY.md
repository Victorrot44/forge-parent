# Forge Philosophy

> *"Build once. Reuse everywhere."*

---

# Nuestra visión

Forge nace con una idea sencilla:

**Las aplicaciones web no deberían reinventar los mismos componentes una y otra vez.**

En la mayoría de proyectos Spring Boot es común encontrar implementaciones similares para:

* respuestas HTTP;
* manejo de excepciones;
* trazabilidad;
* logging;
* validaciones;
* configuración.

Aunque cambie el dominio del negocio, estos problemas técnicos suelen repetirse.

Forge existe para proporcionar soluciones reutilizables y consistentes para estos problemas, incorporándolas de forma gradual y únicamente cuando aporten un valor concreto.

---

# Nuestra misión

Permitir que los desarrolladores se concentren en resolver problemas de negocio mientras Forge proporciona una infraestructura web consistente, reutilizable y fácil de adoptar.

El objetivo no es reemplazar Spring Boot, sino complementarlo cuando exista una necesidad real de estandarización.

---

# Nuestra filosofía

Forge se guía por un conjunto de principios que orientan cada decisión de diseño.

## Simplicidad antes que complejidad

La API pública debe ser intuitiva.

Los casos de uso comunes deben funcionar con la menor cantidad de configuración posible.

Las capacidades avanzadas podrán existir cuando exista una necesidad concreta, pero nunca deben complicar el camino principal.

---

## Convención antes que configuración

Forge proporciona comportamientos predeterminados bien definidos.

La personalización será posible cuando exista una necesidad legítima, pero el caso común debe funcionar desde el primer momento.

---

## El Core no pertenece a ningún framework

El núcleo de Forge debe permanecer independiente de tecnologías específicas.

`forge-web-core` no depende de Spring Boot, Servlet, WebFlux ni de otros frameworks.

La integración específica con Spring pertenece a los módulos correspondientes.

Forge no debe crear abstracciones propias cuando una API pública y estable de Java, HTTP o Spring resuelva correctamente el problema.

---

## Responsabilidad clara

Cada módulo debe resolver un problema específico.

Cada clase debe tener un propósito claro.

Cada componente debe ser fácil de comprender y mantener de forma aislada.

La separación de responsabilidades debe existir cuando aporte un beneficio observable, no como una meta en sí misma.

---

## APIs pequeñas y expresivas

Una API con pocos elementos bien diseñados suele ser mejor que una API extensa difícil de aprender.

Forge prefiere soluciones simples y consistentes antes que múltiples alternativas para resolver el mismo problema.

Todo lo que forme parte de la API pública debe considerarse un contrato.

---

## Inmutabilidad por defecto

Siempre que sea posible, los objetos públicos serán inmutables.

La inmutabilidad facilita el razonamiento sobre el código, reduce efectos secundarios y mejora la seguridad en entornos concurrentes.

---

## Extensibilidad cuando sea necesaria

Forge debe permitir que las aplicaciones personalicen su comportamiento cuando exista una necesidad real.

Sin embargo, la extensibilidad no debe utilizarse como justificación para introducir interfaces, abstracciones, fábricas o puntos de extensión especulativos.

**No toda funcionalidad necesita una abstracción extensible.**

Cuando una aplicación necesite sustituir un comportamiento proporcionado por Forge, deberá existir un mecanismo claro y proporcional para hacerlo.

---

## Consistencia sobre creatividad

Dos aplicaciones que utilizan Forge deberían presentar convenciones similares.

Las respuestas, excepciones y mecanismos de integración deben comportarse de forma uniforme.

La consistencia facilita el mantenimiento y reduce la curva de aprendizaje.

---

## Transparencia

Forge no debe ocultar el funcionamiento de la aplicación.

Las decisiones automáticas deben ser predecibles y fáciles de entender.

Cuando Forge realice una configuración o aplique una convención, el desarrollador debe poder descubrir qué ocurrió y por qué.

Forge debe evitar efectos secundarios globales que no sean evidentes para el consumidor.

---

## Cero configuración para el caso común

Una aplicación debe poder incorporar Forge y obtener un comportamiento útil sin necesidad de configurar múltiples propiedades o beans.

La configuración existe para resolver necesidades reales de personalización, no para habilitar el funcionamiento básico.

---

# Lo que Forge no pretende ser

Forge no busca convertirse en un framework.

Forge no pretende reemplazar Spring Boot.

Forge no implementará lógica de negocio.

Forge no intentará resolver todos los problemas del desarrollo web.

Forge no pretende proporcionar una abstracción propia para cada API existente en Java, HTTP o Spring.

Forge se concentra en proporcionar una base técnica consistente para aplicaciones web cuando exista un problema recurrente que justifique estandarización.

---

# Principios de evolución

Cada nueva funcionalidad deberá responder afirmativamente a las siguientes preguntas:

* ¿Resuelve un problema recurrente?
* ¿Reduce código repetitivo o complejidad en la aplicación?
* ¿Mantiene la simplicidad de la API?
* ¿Respeta la arquitectura modular?
* ¿Utiliza estándares existentes cuando estos resuelven correctamente el problema?
* ¿Evita introducir abstracciones especulativas?
* ¿Puede incorporarse de forma independiente?
* ¿Puede mantenerse a largo plazo?
* ¿Permite al consumidor sustituir el comportamiento cuando exista una necesidad legítima?

Si una funcionalidad no aporta un beneficio observable al consumidor, deberá replantearse antes de incorporarse.

---

# Calidad antes que cantidad

Forge crecerá de forma gradual.

Se priorizarán componentes estables, bien documentados y probados antes que incorporar un gran número de funcionalidades.

Una librería pequeña y confiable siempre será preferible a una librería extensa y difícil de mantener.

---

# Nuestro compromiso

Forge busca convertirse en una base sólida para aplicaciones Spring Boot modernas.

Cada línea de código deberá perseguir los siguientes objetivos:

* claridad;
* consistencia;
* mantenibilidad;
* simplicidad;
* comportamiento predecible;
* evolución controlada.

Estos principios representan la identidad del proyecto y deberán mantenerse independientemente de su evolución.
