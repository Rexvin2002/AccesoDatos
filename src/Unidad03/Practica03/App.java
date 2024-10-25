package Unidad03.Practica03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

    public static void main(String[] args) {

        // Información de la base de datos
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "passwd";
        
        try {
            // Carga el DRIVER MYSQL manualmente
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully!");

            try ( // Establecer la conexión
                Connection conn = DriverManager.getConnection(url, user, password)) {
                System.out.println("Connection established successfully!");
                
                // Crear una sentencia SQL
                Statement stmt = conn.createStatement();
                
                // Ejecutar una consulta SQL
                String query = "SELECT * FROM users"; 
                ResultSet rs = stmt.executeQuery(query);
                
                // Procesa cada uno de los resultados
                while (rs.next()) {
                    
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
                }
                
                // Cierra los recursos
                rs.close();
                stmt.close();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            
            System.err.println("Error: " + ex.getMessage());
            
        }
        
    }
    
}
