# JDBC Utilities - README

## Descripción
Este proyecto contiene dos clases Java para trabajar con bases de datos MySQL con enfoque en gestión de usuarios y licencias:
1. **JDBC1**: Clase utilitaria avanzada para operaciones CRUD con transacciones
2. **App**: Ejemplo básico de conexión y consulta a MySQL

## JDBC1 - Clase Avanzada

### Características principales
- Conexión automatizada a MySQL con control de transacciones
- Operaciones CRUD completas con PreparedStatement
- Gestión de tablas relacionadas (usuarios-licencias)
- Transacciones atómicas para operaciones complejas
- Cierre automático de recursos
- Métodos específicos para gestión de licencias

### Métodos disponibles

| Método | Descripción | Ejemplo |
|--------|-------------|---------|
| `createTables()` | Crea las tablas usuarios y licencias | `jdbc.createTables()` |
| `insertLicencias()` | Inserta usuario con múltiples licencias | `jdbc.insertLicencias(dni, dir, cp, nombre, licencias)` |
| `eliminarLicencias()` | Elimina licencias por DNI | `jdbc.eliminarLicencias("12345678A")` |
| `cleanUserByDNI()` | Elimina usuario por DNI | `jdbc.cleanUserByDNI("12345678A")` |
| `selectColumna()` | Obtiene todos los valores de una columna | `jdbc.selectColumna("nombre")` |
| `selectRowList()` | Obtiene una fila como List | `jdbc.selectRowList(2)` |
| `selectRowMap()` | Obtiene una fila como Map | `jdbc.selectRowMap(2)` |
| `update()` | Actualiza campos | `jdbc.update(1, "nombre", "Juan")` |
| `delete()` | Elimina un registro | `jdbc.delete(3)` |

### Ejemplo de uso
```java
JDBC1 jdbc = new JDBC1("mi_bd", "usuario", "contraseña");
jdbc.createTables();

// Insertar usuario con licencias
ArrayList<ArrayList<String>> licencias = new ArrayList<>();
ArrayList<String> licencia1 = new ArrayList<>();
licencia1.add("B1"); licencia1.add("2023-01-01"); licencia1.add("2025-01-01");
licencias.add(licencia1);

jdbc.insertLicencias("12345678A", "Calle Falsa 123", "28000", "Juan Pérez", licencias);

// Consultar datos
System.out.println(jdbc.selectColumna("nombre"));

jdbc.closeConnection();
```

## App - Ejemplo Básico

### Características
- Demostración simple de conexión JDBC
- Consulta básica a una tabla de usuarios
- Manejo de excepciones
- Carga explícita del driver JDBC

### Ejemplo de conexión
```java
String url = "jdbc:mysql://localhost:3306/testdb";
String user = "root";
String password = "passwd";

Class.forName("com.mysql.cj.jdbc.Driver");
Connection conn = DriverManager.getConnection(url, user, password);
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM users");

while(rs.next()) {
    System.out.println(rs.getString("name"));
}
```

## Requisitos
- Java 8+
- MySQL Server 5.7+
- Connector/J (mysql-connector-java 8.0+)
- Tablas: `usuarios` y `licencias` (se crean automáticamente)

## Configuración
1. Importar el proyecto en tu IDE
2. Añadir el conector MySQL al classpath
3. Modificar credenciales en los constructores
4. Ejecutar App.java o JDBC1.java

## Estructura de tablas
```sql
CREATE TABLE usuarios (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    DNI VARCHAR(9) UNIQUE,
    DIRECCION VARCHAR(100),
    CP VARCHAR(5),
    NOMBRE VARCHAR(100)
);

CREATE TABLE licencias (
    ID INT,
    TIPO VARCHAR(2),
    EXPEDICION DATETIME,
    CADUCIDAD DATETIME,
    FOREIGN KEY (ID) REFERENCES usuarios(ID) ON DELETE CASCADE
);
```

## Mejoras implementadas
- Transacciones atómicas
- Relaciones entre tablas con claves foráneas
- Inserción de múltiples registros relacionados
- Eliminación en cascada
- PreparedStatement para todas las operaciones

## Autor
Kevin Gómez Valderas