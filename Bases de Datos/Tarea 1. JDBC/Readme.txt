# README - JDBC Database Manager

## 📝 Descripción
Aplicación Java para gestión de bases de datos MySQL mediante JDBC. Incluye dos clases principales:
1. **JDBC1**: Clase completa para operaciones CRUD con manejo automático de creación de base de datos/tabla
2. **App**: Ejemplo básico de conexión y consulta a MySQL

## 🔧 Características principales (JDBC1)
- **Auto-creación** de base de datos y tabla si no existen
- **Operaciones CRUD** completas:
  - Selección de campos, columnas y filas
  - Actualizaciones (parciales y completas)
  - Eliminación de registros
- **Múltiples formatos de retorno**:
  - Valores individuales
  - Listas
  - Mapas clave-valor
- **Manejo seguro** de conexiones

## 🛠️ Uso básico (JDBC1)
```java
// 1. Crear instancia (auto-crea DB si no existe)
JDBC1 jdbc = new JDBC1("testdb", "root", "password");

// 2. Operaciones de consulta
String nombre = jdbc.selectField(0, "NOMBRE");
List<String> direcciones = jdbc.selectColumn("DIRECCION");

// 3. Operaciones de modificación
Map<String, String> updates = new HashMap<>();
updates.put("NOMBRE", "Nuevo Nombre");
jdbc.update(1, updates);

// 4. Cerrar conexión
jdbc.closeConnection();
```

## ⚙️ Requisitos
- Java JDK 8+
- MySQL Server
- Driver JDBC para MySQL (com.mysql.cj.jdbc.Driver)
- Credenciales de acceso a MySQL

## 👨‍💻 Autor
Kevin Gómez Valderas

## 💡 Estructura de la tabla
La clase JDBC1 trabaja con una tabla llamada `agenda` con estructura:
```sql
CREATE TABLE agenda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    NOMBRE VARCHAR(100),
    DIRECCION VARCHAR(200)
)
```

## 📌 Dependencias
- mysql-connector-java (para conexión con MySQL)
- JDBC API estándar de Java

## 🛡️ Manejo de errores
- SQLException en todas las operaciones
- Validación de existencia de DB/tabla
- Cierre seguro de recursos

## Ejemplo App.java
Clase de demostración con conexión básica:
```java
// Conexión simple
Connection conn = DriverManager.getConnection(url, user, pass);
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM users");

// Procesar resultados
while(rs.next()) {
    System.out.println(rs.getInt("id") + ": " + rs.getString("name"));
}
```