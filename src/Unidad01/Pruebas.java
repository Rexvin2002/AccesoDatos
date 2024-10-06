package Unidad01;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 * @author kgv17
 */
public class Pruebas {
    public static void main(String[] args) {
        // Crear la estructura de directorios
        createDirectoryStructure();

        // Preguntar al usuario el nombre del archivo a borrar
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el nombre del archivo a borrar (ej. Hijo1.txt): ");
        String fileNameToDelete = scanner.nextLine();

        // Recorrer la estructura y borrar el archivo
        File root = new File("Abuelo");
        boolean deleted = deleteFileRecursively(root, fileNameToDelete);

        if (deleted) {
            System.out.println("El archivo " + fileNameToDelete + " ha sido borrado exitosamente.");
        } else {
            System.out.println("El archivo " + fileNameToDelete + " no fue encontrado.");
        }
        
        scanner.close();
    }

    // Método para crear la estructura de directorios y archivos
    public static void createDirectoryStructure() {
        try {
            // Crear los directorios Abuelo y Abuela
            File abuelo = new File("Abuelo");
            File abuela = new File("Abuela");

            abuelo.mkdir();
            abuela.mkdir();

            // Crear los directorios Padre y Madre dentro de Abuelo
            File padreAbuelo = new File(abuelo, "Padre");
            File madreAbuelo = new File(abuelo, "Madre");

            padreAbuelo.mkdir();
            madreAbuelo.mkdir();

            // Crear los archivos en Abuelo/Padre y Abuelo/Madre
            new File(padreAbuelo, "Hijo1.txt").createNewFile();
            new File(padreAbuelo, "Hija2.txt").createNewFile();
            new File(madreAbuelo, "Hijo3.txt").createNewFile();
            new File(madreAbuelo, "Hija4.txt").createNewFile();

            // Crear los directorios Padre y Madre dentro de Abuela
            File padreAbuela = new File(abuela, "Padre");
            File madreAbuela = new File(abuela, "Madre");

            padreAbuela.mkdir();
            madreAbuela.mkdir();

            // Crear los archivos en Abuela/Padre y Abuela/Madre
            new File(padreAbuela, "Hijo5.txt").createNewFile();
            new File(padreAbuela, "Hija6.txt").createNewFile();
            new File(madreAbuela, "Hijo7.txt").createNewFile();
            new File(madreAbuela, "Hija8.txt").createNewFile();

            System.out.println("Estructura de directorios y archivos creada correctamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al crear los archivos.");
            e.printStackTrace();
        }
    }

    // Método para recorrer recursivamente la estructura de archivos y borrar el archivo dado
    public static boolean deleteFileRecursively(File dir, String fileNameToDelete) {
        if (dir.isDirectory()) {
            // Listar todos los archivos y carpetas dentro del directorio
            File[] files = dir.listFiles();

            if (files != null) {
                for (File file : files) {
                    // Si es un directorio, buscar recursivamente
                    if (file.isDirectory()) {
                        if (deleteFileRecursively(file, fileNameToDelete)) {
                            return true;
                        }
                    } else if (file.getName().equals(fileNameToDelete)) {
                        // Si se encuentra el archivo, borrarlo
                        if (file.delete()) {
                            return true;
                        } else {
                            System.out.println("No se pudo borrar el archivo: " + file.getName());
                        }
                    }
                }
            }
        }
        return false; // No se encontró el archivo
    }
}

