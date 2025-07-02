# README - Sistema de Gestión de Licencias con JDBC

## 📝 Descripción
Aplicación Java para gestión de usuarios y licencias mediante JDBC con MySQL. Incluye dos componentes principales:
1. **JDBC1**: Clase completa para operaciones CRUD con transacciones y relaciones entre tablas
2. **App**: Ejemplo básico de conexión y consulta a MySQL

## 🔧 Características principales (JDBC1)
- **Gestión transaccional** con commit/rollback
- **Relación 1:N** entre usuarios y licencias
- **Operaciones avanzadas**:
  - Inserción de usuario con múltiples licencias en una transacción
  - Eliminación en cascada de licencias
  - Consultas con PreparedStatement seguras
- **Modelo de datos mejorado**:
  - Tabla usuarios con DNI único
  - Tabla licencias con fechas y tipos
  - Integridad referencial con claves foráneas

## 🛠️ Uso básico (JDBC1)
```java
// 1. Crear instancia y tablas
JDBC1 jdbc = new JDBC1("testdb", "root", "password");
jdbc.createTables();

// 2. Insertar usuario con licencias
ArrayList<ArrayList<String>> licencias = new ArrayList<>();
ArrayList<String> licencia = new ArrayList<>();
licencia.add("B1"); licencia.add("2023-01-01"); licencia.add("2025-01-01");
licencias.add(licencia);

jdbc.insertLicencias("12345678A", "Calle Falsa 123", "28000", "John Doe", licencias);

// 3. Eliminar licencias por DNI
jdbc.eliminarLicencias("12345678A");

// 4. Cerrar conexión
jdbc.closeConnection();
```

## ⚙️ Estructura de la base de datos
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

## 👨‍💻 Autor
Kevin Gómez Valderas

## 📌 Requisitos
- Java JDK 11+
- MySQL Server 8+
- Driver JDBC para MySQL (com.mysql.cj.jdbc.Driver)
- Credenciales de acceso a MySQL

## 💡 Mejoras implementadas
- **PreparedStatement** para evitar SQL injection
- **Transacciones** para operaciones atómicas
- **TRY-WITH-RESOURCES** para manejo automático de recursos
- **Integridad referencial** con ON DELETE CASCADE
- **Manejo de errores** con rollback automático

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