# Proyecto Spring Example - Gestión de Clientes

Este proyecto es un ejemplo básico de una aplicación Spring Boot que demuestra operaciones CRUD (Create, Read, Update, Delete) para la gestión de clientes.

## Estructura del Proyecto

- `SpringexampleApplication`: Clase principal que implementa CommandLineRunner para ejecutar operaciones al iniciar
- `Entities/Clientes`: Entidad JPA que representa la tabla de clientes
- `Repositories/RepositorioClientes`: Interfaz que extiende JpaRepository para operaciones con la base de datos

## Características

- Configuración automática con Spring Boot
- Uso de Spring Data JPA para persistencia
- Anotaciones JPA para mapeo de entidades
- Operaciones CRUD básicas

## Configuración de la Entidad Clientes

La entidad Clientes está mapeada a la tabla "clientes" con los siguientes campos:
- id_empleado (clave primaria autoincremental)
- nombre (único, máximo 40 caracteres)
- apellido (máximo 40 caracteres)
- direccion (máximo 100 caracteres)
- email (máximo 100 caracteres)
- telefono (máximo 20 caracteres)
- fecha_registro

## Operaciones Implementadas

1. **Create**: Guardar un nuevo cliente
2. **Read**: Buscar cliente por ID y listar todos
3. **Update**: Actualizar información del cliente
4. **Delete**: Eliminar cliente por ID

## Requisitos

- Java 8+
- Spring Boot 2.7+
- Base de datos configurada (se requiere configuración adicional)

## Autor

Kevin Gómez Valderas - 2º DAM