package Unidad02.Practica04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

    public static void main(String[] args) {

        // Información de la base de datos
        String url = "jdbc:mysql://localhost:3306/testdb";  // Cambia "testdb" por el nombre de tu base de datos
        String user = "root";  // Cambia "root" por tu usuario de MySQL
        String password = "passwd";  // Cambia "your_password" por tu contraseña de MySQL

        // Cargar el DRIVER MYSQL manualmente
        // NO ES NECESARIO DESDE JAVA SE 6
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully!");

            try ( // Establecer la conexión
                    Connection conn = DriverManager.getConnection(url, user, password)) {
                System.out.println("Connection established successfully!");
                
                // Crear una sentencia SQL
                Statement stmt = conn.createStatement();
                
                // Ejecutar una consulta SQL
                String query = "SELECT * FROM users";  // Cambia "users" por el nombre de tu tabla
                ResultSet rs = stmt.executeQuery(query);
                
                // Procesar el resultado
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
                }
                
                // Cerrar los recursos
                rs.close();
                stmt.close();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
