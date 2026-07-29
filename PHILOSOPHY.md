# Forge Philosophy

> *"Build once. Reuse everywhere."*

---

# Nuestra visión

Forge nace con una idea sencilla:

**Las aplicaciones web no deberían reinventar los mismos componentes una y otra vez.**

En la mayoría de proyectos Spring Boot es común encontrar implementaciones similares para:

* respuestas HTTP;
* manejo de excepciones;
* auditoría;
* propagación del Request ID;
* logging;
* validaciones;
* configuración.

Aunque cambie el dominio del negocio, estos componentes suelen resolver exactamente los mismos problemas.

Forge existe para construir esa base una sola vez y ofrecer una solución consistente, extensible y fácil de adoptar.

---

# Nuestra misión

Permitir que los desarrolladores se concentren en resolver problemas de negocio, mientras Forge proporciona una infraestructura web sólida, uniforme y reutilizable.

El objetivo no es reemplazar Spring Boot, sino complementarlo con una capa de convenciones y componentes listos para usar.

---

# Nuestra filosofía

Forge se guía por un conjunto de principios que orientan cada decisión de diseño.

## Simplicidad antes que complejidad

La API pública debe ser intuitiva.

La mayoría de los desarrolladores debería poder utilizar Forge sin consultar la documentación para los casos de uso más comunes.

Las configuraciones avanzadas existirán, pero nunca serán el punto de partida.

---

## Convención antes que configuración

Forge proporcionará comportamientos predeterminados bien definidos.

La personalización será posible cuando sea necesaria, pero el camino más sencillo deberá funcionar desde el primer momento.

---

## El Core no pertenece a ningún framework

El núcleo de Forge debe permanecer independiente de cualquier tecnología específica.

El Core no conoce Spring Boot, Servlet, Reactor ni ningún otro framework.

Esta separación permite que la lógica permanezca estable y reutilizable.

---

## Responsabilidad única

Cada módulo debe resolver un problema específico.

Cada clase debe tener un propósito claro.

Cada componente debe ser fácil de comprender de forma aislada.

---

## APIs pequeñas y expresivas

Una API con menos métodos bien diseñados suele ser mejor que una API enorme difícil de aprender.

Forge prefiere pocas abstracciones consistentes antes que múltiples alternativas para resolver el mismo problema.

---

## Inmutabilidad por defecto

Siempre que sea posible, los objetos públicos serán inmutables.

La inmutabilidad facilita el razonamiento del código, reduce errores y mejora la seguridad en entornos concurrentes.

---

## Extensibilidad sin modificación

Los usuarios deben poder extender Forge sin alterar su implementación interna.

Las interfaces, los contratos y los puntos de extensión son preferibles a modificar el código del núcleo.

---

## Consistencia sobre creatividad

Dos proyectos que utilizan Forge deberían verse similares.

Las respuestas, excepciones, convenciones y mecanismos de integración deben comportarse de forma uniforme.

La consistencia facilita el mantenimiento y reduce la curva de aprendizaje.

---

## Transparencia

Forge no debe ocultar el funcionamiento de la aplicación.

Las decisiones automáticas deben ser predecibles y fáciles de entender.

Cuando Forge realice una configuración o aplique una convención, el desarrollador debe poder descubrir cómo y por qué ocurrió.

---

# Lo que Forge no pretende ser

Forge no busca convertirse en un framework.

Forge no pretende reemplazar Spring Boot.

Forge no implementará lógica de negocio.

Forge no intentará resolver todos los problemas del desarrollo web.

Forge se concentra únicamente en proporcionar una base técnica consistente para aplicaciones web.

---

# Principios de evolución

Cada nueva funcionalidad deberá responder afirmativamente a las siguientes preguntas:

* ¿Resuelve un problema recurrente?
* ¿Reduce código repetitivo?
* ¿Mantiene la simplicidad de la API?
* ¿Respeta la arquitectura modular?
* ¿Es coherente con la filosofía del proyecto?
* ¿Puede mantenerse a largo plazo?

Si la respuesta es negativa a alguna de estas preguntas, la funcionalidad deberá replantearse antes de incorporarse.

---

# Calidad antes que cantidad

Forge crecerá de forma gradual.

Se priorizarán componentes estables, bien documentados y ampliamente probados antes que incorporar un gran número de funcionalidades.

Una librería pequeña y confiable siempre será preferible a una librería extensa y difícil de mantener.

---

# Nuestro compromiso

Forge busca convertirse en una base sólida para aplicaciones Spring Boot modernas.

Cada línea de código deberá perseguir los siguientes objetivos:

* claridad;
* consistencia;
* mantenibilidad;
* extensibilidad;
* simplicidad.

Estos principios representan la identidad del proyecto y deberán mantenerse independientemente de su evolución.
