package Unidad02.Practica04;

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
    }

    // Método string selectCampo(int numRegistro, String nomColumna)
    public String selectCampo(int numRegistro, String nomColumna) throws SQLException {
        
        String query = "SELECT " + nomColumna + " FROM agenda LIMIT 1 OFFSET ?";
        
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
        
        try (Statement stmt = conexion.createStatement(); 
            ResultSet rs = stmt.executeQuery(query)) {
            
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

    // Función main de demostración
    public static void main(String[] args) {
        
        try {

            JDBC1 jdbc = new JDBC1("testdb", "root", "passwd");
            System.out.println("CONECTANDO");

            // EJEMPLO DE SELECCIÓN
            System.out.println(jdbc.selectCampo(0, "NOMBRE")); 
            System.out.println(jdbc.selectColumna("DIRECCION"));
            System.out.println(jdbc.selectRowList(0)); 
            System.out.println(jdbc.selectRowMap(0)); 

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
