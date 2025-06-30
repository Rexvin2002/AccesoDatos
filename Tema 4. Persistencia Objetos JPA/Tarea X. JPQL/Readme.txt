# README - Sistema de Gestión de Empresa con Spring Boot

## 📝 Descripción
Aplicación Spring Boot completa para gestión empresarial que incluye:
- **Departamentos**: Organización interna de la empresa
- **Empleados**: Recursos humanos con relación a departamentos
- **Proyectos**: Iniciativas empresariales con seguimiento
- **API RESTful** completa con operaciones CRUD
- **Consultas avanzadas** con Criteria API

## 🔧 Características principales

### ✔️ Modelo de datos completo
- **Departamento**: Nombre, ubicación, presupuesto
- **Empleado**: Datos personales, salario, fecha contratación
- **Proyecto**: Nombre, presupuesto, estado, fechas

### 🛡️ Relaciones JPA
- `@OneToMany` entre Departamento y Empleados
- `@ManyToOne` entre Empleado y Departamento
- Mapeo correcto de fechas con `@Temporal`

### 📊 API REST completa
- Controladores para cada entidad
- Operaciones CRUD estándar
- Manejo adecuado de respuestas HTTP

## 🛠️ Configuración y uso

### 1. Requisitos previos
- Java 17+
- Maven 3.6+
- Base de datos configurada (MySQL recomendado)

### 2. Estructura del proyecto
```
src/
├── main/
│   ├── java/
│   │   └── com/proyectomaven/springexample/
│   │       ├── Controllers/    # Controladores REST
│   │       ├── Entities/       # Entidades JPA
│   │       ├── Repositories/   # Repositorios Spring Data
│   │       └── SpringexampleApplication.java
│   └── resources/
│       └── application.properties
```

### 3. Endpoints disponibles
- **Departamentos**: `/api/departamentos`
- **Empleados**: `/api/empleados`
- **Proyectos**: `/api/proyectos`

## ⚙️ Ejemplos de uso

### Consultas con Criteria API
```java
// Ejemplo de consulta para empleados
List<Empleado> empleados = empleadoRepository.findByCriteria(
    entityManager,
    "Juan",     // nombreFilter
    "Pérez",    // apellidoFilter
    3000.0,     // salarioFilter
    null,       // fechaContratacionFilter
    "salario",  // orderByField
    true,       // orderAsc
    false,      // all
    10,         // maxResults
    0           // firstResult
);
```

### Operaciones CRUD básicas
```java
// Crear departamento
Departamento depto = new Departamento("IT", "Madrid", 50000.0);
departamentoRepository.save(depto);

// Actualizar empleado
empleadoRepository.findById(id).ifPresent(emp -> {
    emp.setSalario(3500.0);
    empleadoRepository.save(emp);
});

// Eliminar proyecto
proyectoRepository.deleteById(1L);
```

## 👨‍💻 Autor
Kevin Gómez Valderas

## 💡 Tecnologías clave
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Hibernate**
- **Criteria API** para consultas dinámicas
- **Jakarta Persistence**

## 📌 Personalización
Para adaptar el proyecto:
1. Configurar conexión a BD en `application.properties`
2. Modificar entidades según necesidades
3. Añadir nuevos repositorios y consultas
4. Implementar seguridad con Spring Security

## 🌟 Destacados
- **Consultas dinámicas** con CriteriaBuilder
- **Paginación** integrada
- **Validación** de parámetros
- **Manejo de fechas** con LocalDate
- **Relaciones bidireccionales** correctamente mapeadas