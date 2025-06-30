
/**
 * Kevin Gómez Valderas           2ºDAM
 */

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
            System.out.println("\nDriver cargado exitosamente!");

            // Establecer la conexión
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida exitosamente!");

            // Crear una sentencia SQL
            Statement statement = connection.createStatement();

            // Ejecutar una consulta SQL
            String query = "SELECT * FROM users";  // Cambia "users" por el nombre de tu tabla
            ResultSet resultSet = statement.executeQuery(query);

            // Procesar el resultado
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") + ", Nombre: " + resultSet.getString("name"));
            }

            System.out.print("\n");

            // Cerrar los recursos
            resultSet.close();
            statement.close(); 
            connection.close();

        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        
    }

}