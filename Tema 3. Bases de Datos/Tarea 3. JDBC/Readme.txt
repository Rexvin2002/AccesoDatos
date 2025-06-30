# README - Sistema de Gestión de Base de Datos con JDBC

## 📝 Descripción
Este proyecto incluye dos clases Java para interactuar con bases de datos MySQL mediante JDBC:
1. **JDBC1**: Clase completa para operaciones CRUD con manejo transaccional
2. **App**: Ejemplo básico de conexión y consulta a MySQL

## 🔧 Características principales (JDBC1)

### ✔️ Operaciones CRUD completas
- **Create**: Inserción de registros
- **Read**: Múltiples métodos de consulta
- **Update**: Actualización parcial o completa
- **Delete**: Eliminación segura de registros

### 🛡️ Seguridad y robustez
- **PreparedStatement** para prevenir SQL injection
- **Transacciones** con commit/rollback
- **TRY-WITH-RESOURCES** para manejo automático de recursos
- **Tipos de ResultSet** configurados para seguridad

### 📊 Métodos de consulta flexibles
- `selectCampo()`: Obtener un campo específico
- `selectColumna()`: Obtener todos los valores de una columna
- `selectRowList()`: Obtener fila como lista
- `selectRowMap()`: Obtener fila como mapa clave-valor

## 🛠️ Uso básico (JDBC1)

```java
// 1. Crear instancia
JDBC1 jdbc = new JDBC1("testdb", "root", "password");

// 2. Operaciones de consulta
String nombre = jdbc.selectCampo(0, "NOMBRE");
List<String> direcciones = jdbc.selectColumna("DIRECCION");

// 3. Operaciones de modificación
Map<String, String> updates = new HashMap<>();
updates.put("NOMBRE", "Nuevo Nombre");
jdbc.update(1, updates);

// 4. Cerrar conexión
jdbc.closeConnection();
```

## ⚙️ Estructura de la tabla
La clase JDBC1 trabaja con una tabla llamada `agenda` con estructura básica:
- ID (clave primaria)
- NOMBRE
- DIRECCION

## 👨‍💻 Autor
Kevin Gómez Valderas

## 📌 Requisitos
- Java JDK 11+
- MySQL Server 8+
- Driver JDBC para MySQL (com.mysql.cj.jdbc.Driver)
- Credenciales de acceso a MySQL

## 💡 Mejoras implementadas
- **ResultSet.TYPE_SCROLL_INSENSITIVE** para navegación segura
- **ResultSet.CONCUR_READ_ONLY** para operaciones de solo lectura
- **Manejo de errores** con rollback automático
- **Documentación clara** de cada método

## Ejemplo App.java
```java
// Conexión simple con try-with-resources
try (Connection conn = DriverManager.getConnection(url, user, pass);
     Statement stmt = conn.createStatement();
     ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
     
    while(rs.next()) {
        System.out.println(rs.getInt("id") + ": " + rs.getString("name"));
    }
}
```