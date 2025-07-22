package main;

/**
 * Kevin Gómez Valderas 2ºDAM
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

    private final Connection conexion;

    // Constructor
    public JDBC1(String dbName, String user, String password) throws SQLException {
        this.conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, user, password);
        this.conexion.setAutoCommit(false); // Transacción inicia desactivando el auto-commit
    }

    // Método para seleccionar un campo (actualizado con PreparedStatement)
    public String selectCampo(int numRegistro, String nomColumna) throws SQLException {
        String query = "SELECT " + nomColumna + " FROM usuarios LIMIT 1 OFFSET ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, numRegistro);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(nomColumna);
            }
        }
        return null;
    }

    // Método List<String> selectColumna(String nomColumna)
    public List<String> selectColumna(String nomColumna) throws SQLException {

        List<String> values = new ArrayList<>();
        String query = "SELECT " + nomColumna + " FROM agenda";

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                values.add(rs.getString(nomColumna));
            }

        }

        return values;

    }

    // Método List<String> selectRowList(int numRegistro)
    public List<String> selectRowList(int numRegistro) throws SQLException {

        List<String> row = new ArrayList<>();
        String query = "SELECT * FROM agenda LIMIT 1 OFFSET ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, numRegistro);
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
    public Map<String, String> selectRowMap(int numRegistro) throws SQLException {

        Map<String, String> rowMap = new HashMap<>();
        String query = "SELECT * FROM agenda LIMIT 1 OFFSET ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, numRegistro);
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
        query.append(" WHERE id = ?");

        try (PreparedStatement stmt = conexion.prepareStatement(query.toString())) {

            int index = 1;

            for (String column : values.keySet()) {
                stmt.setString(index++, values.get(column));
            }

            stmt.setInt(index, row);
            stmt.executeUpdate();

        }

    }

    // Método update(int row, String campo, String valor)
    public void update(int row, String campo, String valor) throws SQLException {

        String query = "UPDATE agenda SET " + campo + " = ? WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setString(1, valor);
            stmt.setInt(2, row); // Suponiendo que 'row' es el id del registro
            stmt.executeUpdate();

        }

    }

    // Método delete(int row)
    public void delete(int row) throws SQLException {

        String query = "DELETE FROM agenda WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setInt(1, row); // Suponiendo que 'row' es el id del registro
            stmt.executeUpdate();

        }

    }

    // Método que cierra la conexión
    public void closeConnection() throws SQLException {

        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }

    }

    // Método para crear las tablas con las mejoras requeridas
    public void createTables() throws SQLException {
        try (Statement stmt = conexion.createStatement()) {
            String usuariosTable = """
                    CREATE TABLE IF NOT EXISTS usuarios (
                        ID INT AUTO_INCREMENT PRIMARY KEY,
                        DNI VARCHAR(9) UNIQUE,
                        DIRECCION VARCHAR(100),
                        CP VARCHAR(5),
                        NOMBRE VARCHAR(100)
                    );
                    """;

            String licenciasTable = """
                    CREATE TABLE IF NOT EXISTS licencias (
                        ID INT,
                        TIPO VARCHAR(2),
                        EXPEDICION DATETIME,
                        CADUCIDAD DATETIME,
                        FOREIGN KEY (ID) REFERENCES usuarios(ID) ON DELETE CASCADE
                    );
                    """;

            stmt.execute(usuariosTable);
            stmt.execute(licenciasTable);
            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        }
    }

    // Método para insertar usuario y múltiples licencias en una transacción
    public boolean insertLicencias(String DNI, String DIRECCION, String CP, String NOMBRE,
            ArrayList<ArrayList<String>> licencias) {
        String insertUser = "INSERT INTO usuarios (DNI, DIRECCION, CP, NOMBRE) VALUES (?, ?, ?, ?)";
        String insertLicencia = "INSERT INTO licencias (ID, TIPO, EXPEDICION, CADUCIDAD) VALUES (?, ?, ?, ?)";

        try (PreparedStatement userStmt = conexion.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS); PreparedStatement licenciaStmt = conexion.prepareStatement(insertLicencia)) {

            // Insertar usuario
            userStmt.setString(1, DNI);
            userStmt.setString(2, DIRECCION);
            userStmt.setString(3, CP);
            userStmt.setString(4, NOMBRE);
            userStmt.executeUpdate();

            ResultSet generatedKeys = userStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userID = generatedKeys.getInt(1);

                // Insertar licencias
                for (ArrayList<String> licencia : licencias) {
                    licenciaStmt.setInt(1, userID);
                    licenciaStmt.setString(2, licencia.get(0)); // Tipo de licencia
                    licenciaStmt.setString(3, licencia.get(1)); // Fecha de expedición
                    licenciaStmt.setString(4, licencia.get(2)); // Fecha de caducidad
                    licenciaStmt.executeUpdate();
                }
            }
            conexion.commit();
            return true;

        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException rollBackEx) {
                System.err.println("Error during rollback: " + rollBackEx.getMessage());
            }
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar licencias por DNI
    public boolean eliminarLicencias(String DNI) {
        String deleteLicencias = "DELETE FROM licencias WHERE ID = (SELECT ID FROM usuarios WHERE DNI = ?)";
        try (PreparedStatement deleteStmt = conexion.prepareStatement(deleteLicencias)) {

            deleteStmt.setString(1, DNI);
            int rowsAffected = deleteStmt.executeUpdate();
            conexion.commit();
            return rowsAffected > 0;

        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException rollBackEx) {
                System.err.println("Error during rollback: " + rollBackEx.getMessage());
            }
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    // Función main de demostración
    // Add this method to your JDBC1 class
    public boolean cleanUserByDNI(String DNI) throws SQLException {
        String deleteUser = "DELETE FROM usuarios WHERE DNI = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(deleteUser)) {
            stmt.setString(1, DNI);
            int rowsAffected = stmt.executeUpdate();
            conexion.commit();
            return rowsAffected > 0;
        }
    }

    // Then modify your main method like this:
    public static void main(String[] args) {
        try {
            JDBC1 jdbc = new JDBC1("testdb", "root", "passwd");
            jdbc.createTables();
            System.out.println("\nConexión establecida y tablas creadas.\n");

            // Clean existing data first
            String testDNI = "12345678A";
            jdbc.cleanUserByDNI(testDNI);

            // Ejemplo de inserción de usuario con licencias
            ArrayList<ArrayList<String>> licencias = new ArrayList<>();
            ArrayList<String> licencia1 = new ArrayList<>();
            licencia1.add("B1");
            licencia1.add("2023-01-01 00:00:00");
            licencia1.add("2025-01-01 00:00:00");
            licencias.add(licencia1);

            jdbc.insertLicencias(testDNI, "Calle Falsa 123", "28000", "John Doe", licencias);

            // Ejemplo de eliminación de licencias
            jdbc.eliminarLicencias(testDNI);

            // Cerrar la conexión
            jdbc.closeConnection();

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
