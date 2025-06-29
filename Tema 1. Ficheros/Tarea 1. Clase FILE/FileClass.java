/**
 * Kevin Gómez Valderas           2ºDAM
 *
 * Esta clase permite mostrar detalles de archivos y directorios, listar los contenidos de un directorio
 * y calcular el tamaño total de un directorio de manera recursiva.
 */

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class FileClass {

    /**
     * Método principal que ejecuta el programa.
     * Solicita al usuario un path de archivo o directorio, muestra sus detalles y lista los contenidos si es un directorio.
     *
     * @param args Argumentos de la línea de comandos, donde se puede pasar la ruta de archivo/directorio.
     */
    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
        
            // Obtener la ruta desde los argumentos de la línea de comandos o preguntar al usuario
            String path = getPath(args, scanner);

            // Cierra el programa al pulsar 1
            if (path.equals("1")) {
                System.out.println("\nEl programa ha finalizado correctamente.");
                // Cerrar el scanner para liberar recursos
                scanner.close();
                break;
            }
            
            // Crear un objeto File con la ruta proporcionada
            File file = new File(path);

            System.out.println("\n---------------------------------------");

            // Verificar si el archivo o directorio existe
            if (!file.exists()) {
                // Si no existe, informar al usuario
                System.out.println("El archivo o directorio " + file.getName() + " existe: " + file.exists());
                System.out.println("---------------------------------------\n");
            } else {
                // Si existe, mostrar que el archivo o directorio existe
                System.out.println("El archivo o directorio " + file.getName() + " existe: " + file.exists());
                System.out.println("---------------------------------------\n");

                if (file.isFile()) {
                    // Si es un archivo, mostrar los detalles
                    showDetails(file);
                } else if (file.isDirectory()) {
                    // Si es un directorio, mostrar los detalles y listar sus contenidos
                    showDetails(file);
                    listContents(file);
                }
            }
        }
    }

    /**
     * Método que obtiene la ruta del archivo o directorio.
     * Si se pasa un argumento en la línea de comandos, lo usa. De lo contrario, pide al usuario una ruta.
     *
     * @param args Los argumentos de la línea de comandos.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     * @return La ruta como un string.
     */
    public static String getPath(String[] args, Scanner scanner) {
        if (args.length > 0) {
            return args[0];  // Usar la ruta desde el argumento de la línea de comandos
        } else {
            System.out.println("\nIntroduce una ruta (1 salir): ");
            return scanner.nextLine();  // Leer la ruta desde la entrada del usuario
        }
    }

    /**
     * Muestra los detalles de un archivo o directorio, como el tipo, nombre, tamaño, permisos y fecha de última modificación.
     *
     * @param file El archivo o directorio del que se mostrarán los detalles.
     */
    public static void showDetails(File file) {
        // Determinar si es un archivo o un directorio
        String type = file.isDirectory() ? "Directorio" : "Archivo";
        System.out.println("\n---------------------------------------");
        System.out.println("Tipo: " + type);
        System.out.println("Nombre: " + file.getName());
        System.out.println("Ruta absoluta: " + file.getAbsolutePath());
        System.out.println("Ruta relativa: " + file.getPath());
        System.out.println("Ruta del padre: " + (file.getParent() != null ? file.getParent() : "Sin padre"));

        // Mostrar el tamaño del archivo o directorio
        if (file.isFile()) {
            long sizeBytes = file.length();
            System.out.printf("Tamaño: %d bytes (%.2f KB, %.2f MB)%n", sizeBytes, sizeBytes / 1024.0, sizeBytes / (1024.0 * 1024.0));
        } else {
            long sizeBytes = calculateDirectorySize(file);
            System.out.printf("Tamaño: %d bytes (%.2f KB, %.2f MB)%n", sizeBytes, sizeBytes / 1024.0, sizeBytes / (1024.0 * 1024.0));
        }

        // Mostrar si está oculto
        System.out.println("¿Está oculto?: " + (file.isHidden() ? "Sí" : "No"));

        // Mostrar permisos de lectura, escritura y ejecución
        String permissions = (file.canRead() ? "r" : "-") + 
                             (file.canWrite() ? "w" : "-") + 
                             (file.canExecute() ? "x" : "-");
        System.out.println("Permisos: " + permissions);

        // Mostrar la fecha de última modificación
        Date lastModified = new Date(file.lastModified());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println("Última modificación: " + sdf.format(lastModified));

        System.out.println("---------------------------------------");
    }

    /**
     * Lista los contenidos de un directorio, mostrando los detalles de cada archivo o subdirectorio.
     * Si hay subdirectorios, los lista recursivamente.
     *
     * @param directory El directorio del que se listarán los contenidos.
     */
    public static void listContents(File directory) {
        File[] files = directory.listFiles(); // Obtener los archivos y subdirectorios

        if (files == null || files.length == 0) {
            System.out.println("El directorio está vacío.");
        } else {
            // Mostrar los detalles de cada archivo o subdirectorio
            for (File file : files) {
                showDetails(file);
                if (file.isDirectory()) {
                    listContents(file); // Listar contenidos de subdirectorios recursivamente
                }
            }
        }
    }

    /**
     * Calcula el tamaño total de un directorio recursivamente, sumando los tamaños de todos los archivos y subdirectorios.
     *
     * @param directory El directorio del que se calculará el tamaño.
     * @return El tamaño total del directorio en bytes.
     */
    public static long calculateDirectorySize(File directory) {
        long totalSize = 0;
        File[] files = directory.listFiles(); // Obtener los archivos y subdirectorios

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    totalSize += file.length();  // Sumar el tamaño de los archivos
                } else if (file.isDirectory()) {
                    totalSize += calculateDirectorySize(file);  // Recursión para subdirectorios
                }
            }
        }

        return totalSize;
    }
}
