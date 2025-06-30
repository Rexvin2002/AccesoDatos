import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Esta clase permite crear una estructura de directorios y archivos,
 * así como eliminar archivos específicos solicitados por el usuario.
 */
public class CreationAndDeletion {

    /**
     * Método principal que ejecuta el programa.
     * Crea la estructura de directorios y permite al usuario borrar archivos.
     *
     * @param args argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Llamada al método para crear la estructura de directorios y archivos
        createDirectoryStructure();
        
        // Usamos try-with-resources para asegurar que el Scanner se cierra automáticamente al finalizar
        try (Scanner scanner = new Scanner(System.in)) {
            // Bucle infinito para permitir al usuario borrar varios archivos hasta que elija salir
            while (true) {
                // Pedir al usuario que introduzca el nombre del archivo a borrar o '1' para salir
                System.out.println("Introduce el nombre del archivo a borrar (ej. Hijo1.txt) (1 para salir): ");
                String fileNameToDelete = scanner.nextLine();
                
                // Sale del programa si el usuario introduce '1'
                if (fileNameToDelete.equals("1")) {
                    System.out.println("\nHa salido del programa correctamente.");
                    break;
                }
                
                // Buscar y eliminar el archivo especificado por el usuario
                File root = new File("Estructura"); // Directorio base
                boolean deleted = deleteFileRecursively(root, fileNameToDelete);
                
                // Informar al usuario si el archivo fue eliminado o no
                if (deleted) {
                    System.out.println("El archivo " + fileNameToDelete + " ha sido borrado exitosamente.\n");
                } else {
                    System.err.println("El archivo " + fileNameToDelete + " no fue encontrado.\n");
                }
            }
        }
    }

    /**
     * Crea una estructura de directorios y archivos representando una familia ficticia.
     * La estructura generada contiene directorios para "Abuelo", "Abuela", y dentro de ellos,
     * directorios para "Padre" y "Madre", además de archivos de texto como hijos.
     */
    public static void createDirectoryStructure() {
        try {
            // Crear el directorio base "Estructura"
            File root = new File("Estructura");
            if (!root.exists()) {
                root.mkdir();
            }

            // Crear los directorios Abuelo y Abuela dentro de la estructura
            File abuelo = new File(root, "Abuelo");
            File abuela = new File(root, "Abuela");

            abuelo.mkdir();
            abuela.mkdir();

            // Crear directorios Padre y Madre dentro de Abuelo
            File padreAbuelo = new File(abuelo, "Padre");
            File madreAbuelo = new File(abuelo, "Madre");

            padreAbuelo.mkdir();
            madreAbuelo.mkdir();

            // Crear archivos de texto en Abuelo/Padre y Abuelo/Madre
            new File(padreAbuelo, "Hijo1.txt").createNewFile();
            new File(padreAbuelo, "Hija2.txt").createNewFile();
            new File(madreAbuelo, "Hijo3.txt").createNewFile();
            new File(madreAbuelo, "Hija4.txt").createNewFile();

            // Crear directorios Padre y Madre dentro de Abuela
            File padreAbuela = new File(abuela, "Padre");
            File madreAbuela = new File(abuela, "Madre");

            padreAbuela.mkdir();
            madreAbuela.mkdir();

            // Crear archivos de texto en Abuela/Padre y Abuela/Madre
            new File(padreAbuela, "Hijo5.txt").createNewFile();
            new File(padreAbuela, "Hija6.txt").createNewFile();
            new File(madreAbuela, "Hijo7.txt").createNewFile();
            new File(madreAbuela, "Hija8.txt").createNewFile();

            // Confirmación de que la estructura se creó correctamente
            System.out.println("Estructura de directorios y archivos creada correctamente.\n");
        } catch (IOException e) {
            // Manejo de errores en caso de fallar al crear archivos
            System.err.println("Ocurrió un error al crear los archivos.");
        }
    }

    /**
     * Método que busca y elimina recursivamente un archivo específico dentro de un directorio.
     *
     * @param dir El directorio donde comenzar la búsqueda.
     * @param fileNameToDelete El nombre del archivo que se desea eliminar.
     * @return true si el archivo fue eliminado exitosamente, false si no se encontró o no se pudo eliminar.
     */
    public static boolean deleteFileRecursively(File dir, String fileNameToDelete) {
        // Comprobar si el directorio es válido
        if (dir.isDirectory()) {
            // Obtener la lista de archivos y carpetas dentro del directorio
            File[] files = dir.listFiles();

            // Si hay archivos o carpetas dentro del directorio
            if (files != null) {
                for (File file : files) {
                    // Si es un subdirectorio, se realiza una búsqueda recursiva
                    if (file.isDirectory()) {
                        if (deleteFileRecursively(file, fileNameToDelete)) {
                            return true;
                        }
                    } 
                    // Si es un archivo y el nombre coincide, se elimina
                    else if (file.getName().equals(fileNameToDelete)) {
                        if (file.delete()) {
                            return true; // Archivo eliminado con éxito
                        } else {
                            System.err.println("No se pudo borrar el archivo: " + file.getName());
                        }
                    }
                }
            }
        }
        // Retorna false si el archivo no se encontró
        return false;
    }
}
