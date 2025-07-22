# Sistema de Gestión de Clientes con Spring Boot

## Descripción
Este proyecto es un sistema CRUD (Create, Read, Update, Delete) para la gestión de clientes desarrollado con Spring Boot y MySQL. El sistema demuestra las capacidades básicas de Spring Data JPA para operaciones de persistencia.

## Características principales
- Operaciones CRUD completas para entidades Cliente
- Configuración automática de la base de datos
- Generación automática de IDs
- Validación de campos con anotaciones JPA
- Fecha de registro automática
- Nombres de cliente únicos
- Ejecución demostrativa con datos de prueba

## Tecnologías utilizadas
- **Spring Boot 3.x**
- **Spring Data JPA**
- **MySQL 8.0**
- **Maven**

## Configuración

### Requisitos previos
- Java 17+
- MySQL 8.0+
- Maven 3.9+

### Configuración de la base de datos
Editar el archivo `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring_example_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

## Estructura del proyecto

### Entidades principales
- **Clientes**: Modelo principal con información de clientes
  - Campos: id, nombre (único), apellido, dirección, email, teléfono, fecha de registro

### Repositorios
- **RepositorioClientes**: Extiende JpaRepository para operaciones CRUD

### Ejecución demostrativa
La clase principal `SpringexampleApplication` implementa `CommandLineRunner` y ejecuta automáticamente:
1. Limpieza inicial de datos
2. Creación de clientes con nombres aleatorios
3. Operaciones de lectura, actualización y eliminación
4. Visualización de resultados en consola

## Ejecución del proyecto

1. Clonar el repositorio
2. Configurar la base de datos en `application.properties`
3. Ejecutar con Maven:
```bash
mvn spring-boot:run
```

## Salida esperada
Al ejecutar la aplicación, se verá en consola:
```
=== INICIO DE EJECUCIÓN ===
Preparando datos...

Creando clientes...
Clientes creados: Ana_123 y Luis_456

Clientes registrados:
ID: 1 - Ana_123 García - Dirección: Calle Primavera 23
ID: 2 - Luis_456 Martínez - Dirección: Avenida Libertad 45

Actualizando cliente...
Dirección actualizada a: Nueva Dirección 78

Eliminando cliente...
Cliente con ID 2 eliminado

Estado final de clientes:
ID: 1 - Ana_123 García - Dirección: Nueva Dirección 78

=== FIN DE EJECUCIÓN ===
```

## Autor
Kevin Gómez Valderas - 2º DAM