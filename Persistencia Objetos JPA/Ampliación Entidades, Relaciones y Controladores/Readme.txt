# Sistema de Gestión de Biblioteca con Spring Boot y JPA

## Descripción
Este proyecto es un sistema de gestión de biblioteca desarrollado con Spring Boot y Spring Data JPA. Implementa un modelo de datos relacional para gestionar libros, autores, préstamos y usuarios de una biblioteca.

## Modelo de Datos

### Entidades principales:

1. **Libro**
   - Información básica (título, ISBN, año de publicación, editorial)
   - Relación ManyToMany con Autores
   - Relación OneToMany con Ejemplares

2. **Autor**
   - Datos personales (nombre, apellido, nacionalidad)
   - Relación ManyToMany con Libros

3. **Ejemplar**
   - Información física (código de barras, estado, ubicación)
   - Relación ManyToOne con Libro
   - Relación OneToMany con Préstamos

4. **Usuario**
   - Datos personales (nombre, apellido, email, teléfono)
   - Relación OneToMany con Préstamos

5. **Préstamo**
   - Registro de préstamos (fecha préstamo, fecha devolución, estado)
   - Relaciones ManyToOne con Usuario y Ejemplar

## Características Técnicas

- **Spring Data JPA** para operaciones CRUD
- **Mapeo de relaciones** bidireccionales
- **Validación de datos** con anotaciones JPA
- **Generación automática de IDs** con estrategia IDENTITY
- **Consultas personalizadas** en repositorios

## Repositorios Implementados

1. **RepositorioLibros**
   - Métodos estándar de JpaRepository
   - Búsqueda por título y ISBN

2. **RepositorioAutores**
   - Búsqueda por nombre y apellido
   - Consulta de libros por autor

3. **RepositorioEjemplares**
   - Búsqueda por código de barras
   - Consulta de ejemplares disponibles

4. **RepositorioUsuarios**
   - Búsqueda por email y teléfono
   - Consulta de préstamos activos por usuario

5. **RepositorioPrestamos**
   - Búsqueda por fechas y estado
   - Consulta de préstamos vencidos

## Configuración y Uso

### Requisitos:
- Java 17+
- Maven 3.6+
- Base de datos MySQL o H2

### Instalación:
```bash
mvn clean install
mvn spring-boot:run
```

### Configuración de Base de Datos:
Editar `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca
spring.datasource.username=usuario
spring.datasource.password=contraseña
spring.jpa.hibernate.ddl-auto=update
```

## Ejemplo de Operaciones

El método `run` en la clase principal demuestra:
1. Creación de autores y libros
2. Registro de ejemplares
3. Registro de usuarios
4. Proceso de préstamo y devolución

## Estructura de Paquetes

```
com.proyectomaven.springexample
├── Entities        # Entidades JPA
├── Repositories    # Interfaces de repositorio
└── SpringexampleApplication.java  # Clase principal
```

## Autor
Kevin Gómez Valderas - 2º DAM

## Licencia
Este proyecto es de código abierto para fines educativos. Se permite su uso y modificación manteniendo los créditos al autor original.