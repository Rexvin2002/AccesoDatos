# README.txt - Aplicación JDBC para Gestión de Base de Datos MySQL

## Descripción
Esta aplicación Java demuestra el uso avanzado de JDBC para interactuar con una base de datos MySQL. Implementa operaciones CRUD completas con manejo de transacciones, relaciones entre tablas y operaciones batch. El proyecto incluye dos clases principales que muestran diferentes niveles de complejidad en el acceso a datos.

## Estructura del Proyecto

### Clases principales:
1. **App** - Versión básica
   - Conexión simple a MySQL
   - Consulta básica a tabla "users"
   - Uso de DriverManager y ResultSet

2. **JDBC1** - Versión avanzada
   - Gestión completa de conexiones con transacciones
   - Operaciones CRUD con PreparedStatement
   - Relaciones entre tablas (usuarios-licencias)
   - Métodos para:
     - Creación automática de tablas
     - Inserción transaccional de usuarios con licencias
     - Eliminación en cascada
     - Consultas avanzadas (por campo, columna o fila completa)

## Características Principales

### Gestión de Transacciones
- AutoCommit desactivado por defecto
- Commit/Rollback explícitos
- Transacciones atómicas para operaciones compuestas

### Modelo de Datos
- Tabla **usuarios**:
  - ID (PK), DNI (único), DIRECCION, CP, NOMBRE
- Tabla **licencias**:
  - ID (FK), TIPO, EXPEDICION, CADUCIDAD
  - Relación con eliminación en cascada

### Operaciones Implementadas
- **Creación** de tablas con relaciones
- **Inserción** transaccional de usuarios con múltiples licencias
- **Consulta** por:
  - Campo específico
  - Columna completa
  - Fila completa (como List o Map)
- **Actualización** de registros (individual o múltiples campos)
- **Eliminación**:
  - Licencias por DNI
  - Usuarios (con eliminación en cascada)

## Configuración y Uso

### Requisitos:
- Java 8+
- MySQL Server 5.7+
- Driver JDBC de MySQL (mysql-connector-java)

### Instalación:
1. Clonar el repositorio
2. Configurar credenciales en las clases (usuario/contraseña)
3. Ejecutar el método main() de JDBC1 para:
   - Crear la estructura de base de datos
   - Insertar datos de prueba
   - Ejecutar operaciones demostrativas

### Configuración de Conexión:
Modificar en el constructor de JDBC1:
```java
this.conexion = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/" + dbName, 
    user,  // usuario MySQL
    password  // contraseña MySQL
);
```

## Ejemplos de Uso

### Insertar usuario con licencias:
```java
ArrayList<ArrayList<String>> licencias = new ArrayList<>();
ArrayList<String> licencia1 = new ArrayList<>();
licencia1.add("B1"); // Tipo
licencia1.add("2023-01-01 00:00:00"); // Expedición
licencia1.add("2025-01-01 00:00:00"); // Caducidad
licencias.add(licencia1);

jdbc.insertLicencias(
    "12345678A",       // DNI
    "Calle Falsa 123", // Dirección
    "28000",           // CP
    "John Doe",        // Nombre
    licencias          // Lista de licencias
);
```

### Consultar datos:
```java
// Obtener fila como Map
Map<String, String> usuario = jdbc.selectRowMap(0);

// Obtener columna completa
List<String> nombres = jdbc.selectColumna("NOMBRE");
```

## Mejoras Implementadas
1. **PreparedStatement** para evitar SQL injection
2. **Try-with-resources** para manejo automático de recursos
3. **Transacciones** para operaciones atómicas
4. **Relaciones** entre tablas con integridad referencial
5. **Manejo de errores** con rollback automático

## Autor
Kevin Gómez Valderas - 2º DAM

## Licencia
Este código se proporciona con fines educativos. Uso libre bajo atribución.