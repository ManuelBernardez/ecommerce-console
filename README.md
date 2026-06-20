# Ecommerce Console

Esta es la primera iteración de un sistema de comercio electrónico que estoy desarrollando. Incluye las entidades, procesos y relaciones elementales para la gestión del sistema. 

El objetivo fue modelar e implementar una versión académica para comprender en profundidad las necesidades del sistema, aplicando buenas prácticas de diseño y desarrollo de software. La aplicación permite gestionar productos, categorías y pedidos mediante una interfaz de consola, con persistencia temporal en memoria. 


## Cómo ejecutar

1. Clonar el repositorio
```bash
git clone https://github.com/ManuelBernardez/ecommerce-console.git
cd ecommerce-console
```
2. Abrir el proyecto en cualquier IDE compatible con Java.
3. Ejecutar la clase `Main.java`.


## Funcionalidades

- [x] Gestión de inventario
- [x] CRUD de categorías, pedidos y productos
- [x] Módulos reutilizables (secuencias y validaciones)
- [x] Manejo de excepciones personalizadas
- [x] Interfaz de consola 


## Conceptos aplicados

- Enums
- Generics
- Colecciones
- Reglas de negocio y excepciones personalizadas
- Programación Orientada a Objetos: principios SOLID, herencia, polimorfismo, encapsulamiento, clases abstractas e interfaces
- Arquitectura en capas (dominio, servicio y presentación)


## Reglas de negocio importantes

**Control de stock y consistencia con el pedido**
- Al agregar un producto al pedido: se valida su existencia, se verifica stock disponible y se descuenta del inventario si es válido.
- Al quitar un producto del pedido: se elimina del carrito y se repone el stock al inventario
- No se pueden eliminar categorías o productos que tengan pedidos asociados

**Gestión de estados del pedido**
- Un pedido solo puede modificarse si está en estado `CREADO`
- Transiciones de estado válidas: `CREADO → ENVIADO / CANCELADO`
- Evita modificaciones inconsistentes sobre pedidos ya procesados

**Validaciones de integridad**
- No se permiten productos ni pedidos nulos o inexistentes, ni valores u operaciones inválidas (como cantidades negativas)
- Validación de duplicados en productos y categorías


## Estructura del proyecto

```
src/ 
├── domain/ 
│     ├── model/ 
│     ├── repository/ 
│     ├── interfaces/ 
│     └── exception/ 
├── service/              # Lógica de negocio 
├── presentation/ 
│     └── console/        # Interfaz por consola 
├── utils/ 
└── app/ 
      └── Main.java       # Punto de entrada
```


## Autor

Desarrollado por Manuel Bernardez

Proyecto desarrollado con fines educativos en el marco del curso Backend Java (Techlab/Talento Tech).
