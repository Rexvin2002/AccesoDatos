import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBC1 {

    private final Connection conexion;

    // Constructor
    public JDBC1(String dbName, String user, String password) throws SQLException {
        this.conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, user, password);
        this.conexion.setAutoCommit(false); // Habilitamos el uso de transacciones
    }

    // Método para seleccionar un campo específico de un registro
    public String selectCampo(int numRegistro, String nomColumna) throws SQLException {
        String query = "SELECT " + nomColumna + " FROM agenda LIMIT 1 OFFSET ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setInt(1, numRegistro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(nomColumna);
                }
            }
            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        }
        return null;
    }

    // Método para seleccionar una columna entera
    public List<String> selectColumna(String nomColumna) throws SQLException {
        List<String> values = new ArrayList<>();
        String query = "SELECT " + nomColumna + " FROM agenda";
        try (PreparedStatement stmt = conexion.prepareStatement(query,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                values.add(rs.getString(nomColumna));
            }
            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        }
        return values;
    }

    // Método para seleccionar una fila en forma de lista
    public List<String> selectRowList(int numRegistro) throws SQLException {
        List<String> row = new ArrayList<>();
        String query = "SELECT * FROM agenda LIMIT 1 OFFSET ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setInt(1, numRegistro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        row.add(rs.getString(i));
                    }
                }
            }
            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        }
        return row;
    }

    // Método para seleccionar una fila en forma de mapa
    public Map<String, String> selectRowMap(int numRegistro) throws SQLException {
        Map<String, String> rowMap = new HashMap<>();
        String query = "SELECT * FROM agenda LIMIT 1 OFFSET ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setInt(1, numRegistro);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        rowMap.put(metaData.getColumnName(i), rs.getString(i));
                    }
                }
            }
            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        }
        return rowMap;
    }

    // Método para actualizar múltiples campos de un registro
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
            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        }
    }

    // Método para actualizar un solo campo de un registro
    public void update(int row, String campo, String valor) throws SQLException {
        String query = "UPDATE agenda SET " + campo + " = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, valor);
            stmt.setInt(2, row);
            stmt.executeUpdate();
            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        }
    }

    // Método para eliminar un registro
    public void delete(int row) throws SQLException {
        String query = "DELETE FROM agenda WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, row);
            stmt.executeUpdate();
            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        }
    }

    // Método para cerrar la conexión
    public void closeConnection() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }

    // Función main de demostración
    public static void main(String[] args) {
        try {
            JDBC1 jdbc = new JDBC1("testdb", "root", "passwd");
            System.out.println("\nCONECTADO\n");

            // EJEMPLO DE SELECCIÓN
            System.out.println(jdbc.selectCampo(0, "NOMBRE"));
            System.out.println(jdbc.selectColumna("DIRECCION"));
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
            System.err.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}