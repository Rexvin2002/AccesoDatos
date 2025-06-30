# JDBC Utilities - README

## Descripción
Este proyecto contiene dos clases Java para trabajar con bases de datos MySQL:
1. **JDBC1**: Clase utilitaria avanzada para operaciones CRUD en tablas
2. **App**: Ejemplo básico de conexión y consulta a MySQL

## JDBC1 - Clase Avanzada

### Características principales
- Conexión automatizada a MySQL
- Operaciones CRUD completas
- Métodos para consultas específicas
- Uso de PreparedStatement para seguridad
- Cierre automático de recursos

### Métodos disponibles

| Método | Descripción | Ejemplo |
|--------|-------------|---------|
| `selectField()` | Obtiene un campo específico | `jdbc.selectField(0, "nombre")` |
| `selectColumn()` | Obtiene todos los valores de una columna | `jdbc.selectColumn("email")` |
| `selectRowList()` | Obtiene una fila como List | `jdbc.selectRowList(2)` |
| `selectRowMap()` | Obtiene una fila como Map | `jdbc.selectRowMap(2)` |
| `update()` | Actualiza uno o varios campos | `jdbc.update(1, "nombre", "Juan")` |
| `delete()` | Elimina un registro | `jdbc.delete(3)` |

### Ejemplo de uso
```java
JDBC1 jdbc = new JDBC1("mi_bd", "usuario", "contraseña");

// Consultar datos
System.out.println(jdbc.selectField(0, "nombre"));
System.out.println(jdbc.selectColumn("email"));

// Actualizar datos
Map<String, String> datos = new HashMap<>();
datos.put("nombre", "Ana");
datos.put("email", "ana@example.com");
jdbc.update(1, datos);

jdbc.closeConnection();
```

## App - Ejemplo Básico

### Características
- Demostración simple de conexión JDBC
- Consulta básica a una tabla
- Manejo de excepciones
- Cierre adecuado de recursos

### Ejemplo de conexión
```java
String url = "jdbc:mysql://localhost:3306/testdb";
String user = "root";
String password = "passwd";

Connection conn = DriverManager.getConnection(url, user, password);
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");

while(rs.next()) {
    System.out.println(rs.getString("nombre"));
}
```

## Requisitos
- Java 8+
- MySQL Server
- Connector/J (mysql-connector-java)
- Tablas: `agenda` y `users`

## Configuración
1. Importar el proyecto en tu IDE
2. Añadir el conector MySQL al classpath
3. Modificar credenciales en los constructores
4. Ejecutar App.java o JDBC1.java

## Mejoras posibles
- Añadir pool de conexiones
- Implementar transacciones
- Soporte para otros motores de BD
- Logging más detallado

## Autor
Kevin Gómez Valderas