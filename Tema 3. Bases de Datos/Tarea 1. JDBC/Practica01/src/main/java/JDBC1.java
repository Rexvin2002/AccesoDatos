
/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBC1 {

    private Connection connection;

    // Constructor modificado para crear la base de datos si no existe
    public JDBC1(String dbName, String user, String password) throws SQLException {
        // Primero conectamos sin especificar la base de datos
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/", user, password);
        
        // Verificamos si la base de datos existe
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE '" + dbName + "'");
        
        if (!rs.next()) {
            // Si no existe, la creamos
            stmt.executeUpdate("CREATE DATABASE " + dbName);
            System.out.println("Base de datos '" + dbName + "' creada exitosamente.");
        }
        
        // Cerramos la conexión temporal
        stmt.close();
        conn.close();
        
        // Ahora nos conectamos a la base de datos específica
        this.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + dbName, user, password);
        
        // Verificamos si la tabla agenda existe, si no, la creamos
        verifyAndCreateTable();
    }

    // Método para verificar y crear la tabla si no existe
    private void verifyAndCreateTable() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'agenda'");
        
        if (!rs.next()) {
            // Si la tabla no existe, la creamos
            String createTableSQL = "CREATE TABLE agenda (" +
                                   "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                   "NOMBRE VARCHAR(100), " +
                                   "DIRECCION VARCHAR(200))";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Tabla 'agenda' creada exitosamente.");
            
            // Opcional: insertar algunos datos de ejemplo
            String insertSQL = "INSERT INTO agenda (NOMBRE, DIRECCION) VALUES " +
                              "('Ejemplo Nombre', 'Ejemplo Dirección')";
            stmt.executeUpdate(insertSQL);
        }
        
        stmt.close();
    }

    // Resto de los métodos permanecen igual...
    public String selectField(int recordNum, String columnName) throws SQLException {
        String query = "SELECT " + columnName + " FROM agenda LIMIT 1 OFFSET ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, recordNum);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(columnName);
            }
        }
        return null;
    }

    // Método List<String> selectColumna(String nomColumna)
    public List<String> selectColumn(String columnName) throws SQLException {
        List<String> values = new ArrayList<>();
        String query = "SELECT " + columnName + " FROM agenda";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                values.add(rs.getString(columnName));
            }
        }
        return values;
    }

    // Método List<String> selectRowList(int numRegistro)
    public List<String> selectRowList(int recordNum) throws SQLException {
        List<String> row = new ArrayList<>();
        String query = "SELECT * FROM agenda LIMIT 1 OFFSET ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, recordNum);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
            }
        }
        return row;
    }

    // Método Map<String, String> selectRowMap(int numRegistro)
    public Map<String, String> selectRowMap(int recordNum) throws SQLException {
        Map<String, String> rowMap = new HashMap<>();
        String query = "SELECT * FROM agenda LIMIT 1 OFFSET ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, recordNum);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    rowMap.put(metaData.getColumnName(i), rs.getString(i));
                }
            }
        }
        return rowMap;
    }

    // Método update(int row, Map<String, String> values)
    public void update(int row, Map<String, String> values) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE agenda SET ");
        for (String column : values.keySet()) {
            query.append(column).append(" = ?, ");
        }
        query.setLength(query.length() - 2); // Eliminar la última coma
        query.append(" WHERE id = ?"); // Asumiendo que hay una columna 'id' para identificar el registro

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            int index = 1;
            for (String column : values.keySet()) {
                stmt.setString(index++, values.get(column));
            }
            stmt.setInt(index, row); // Suponiendo que 'row' es el id del registro
            stmt.executeUpdate();
        }
    }

    // Método update(int row, String campo, String valor)
    public void update(int row, String field, String value) throws SQLException {
        String query = "UPDATE agenda SET " + field + " = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, value);
            stmt.setInt(2, row); // Suponiendo que 'row' es el id del registro
            stmt.executeUpdate();
        }
    }

    // Método delete(int row)
    public void delete(int row) throws SQLException {
        String query = "DELETE FROM agenda WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, row); // Suponiendo que 'row' es el id del registro
            stmt.executeUpdate();
        }
    }

    // Método que cierra la conexión
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Función main de demostración
    public static void main(String[] args) {
        
        try {

            JDBC1 jdbc = new JDBC1("testdb", "root", "passwd");
            System.out.println("\nCONECTADO\n");

            // EJEMPLO DE SELECCIÓN
            System.out.println(jdbc.selectField(0, "NOMBRE")); 
            System.out.println(jdbc.selectColumn("DIRECCION"));
            System.out.println(jdbc.selectRowList(0)); 
            System.out.println(jdbc.selectRowMap(0)+"\n"); 

            // EJEMPLO DE ACTUALIZACIÓN
            Map<String, String> updateValues = new HashMap<>();
            updateValues.put("NOMBRE", "Nuevo Nombre");
            updateValues.put("DIRECCION", "Nueva Dirección");
            jdbc.update(0, updateValues); 

            // EJEMPLO DE ELIMINACIÓN
            jdbc.delete(0); 

            // CIERRA LA CONEXIÓN
            jdbc.closeConnection();

        } catch (SQLException e) {

            System.err.println("Error: " + e.getMessage());

        }

    }

}