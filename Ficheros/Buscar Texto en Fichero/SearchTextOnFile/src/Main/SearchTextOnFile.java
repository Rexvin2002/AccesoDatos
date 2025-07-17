package Main;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Clase para leer archivos y directorios secuencialmente. Permite a los
 * usuarios ingresar una ruta de archivo o directorio, y muestra detalles sobre
 * el archivo o directorio especificado.
 */
public class SearchTextOnFile {

    /**
     * Método principal para ejecutar el programa. Solicita continuamente al
     * usuario una ruta de archivo o directorio.
     *
     * @param args argumentos de línea de comandos (ruta opcional)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nEnter the directory path (1 to exit): ");
            String path = scanner.nextLine();

            // Condición de salida
            if (path.equals("1")) {
                System.out.println("\nThe program has finished successfully.");
                scanner.close();
                break;
            }

            File directory = new File(path);

            // Verifica si el directorio existe
            if (!directory.exists() || !directory.isDirectory()) {
                System.out.println("The specified path does not exist or is not a directory: " + directory.getPath());
                continue;
            }

            // Solicitar texto para buscar
            System.out.print("Enter text to search in files: ");
            String searchText = scanner.nextLine();

            // Muestra detalles del directorio
            showDetails(directory);
            System.out.println("Searching for \"" + searchText + "\" in files...");

            // Buscar texto en todos los archivos del directorio
            searchTextInDirectory(directory, searchText);

            // Preguntar si desea buscar nuevamente
            System.out.println("\nWould you like to search again? (y/n)");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("y")) {
                System.out.println("\nThe program has finished successfully.");
                break;
            }
        }
    }

    /**
     * Muestra los detalles del archivo o directorio dado.
     *
     * @param file el archivo o directorio a mostrar
     */
    public static void showDetails(File file) {
        String type = file.isDirectory() ? "Directory" : "File";
        System.out.println("\n---------------------------------------");
        System.out.println("Type: " + type);
        System.out.println("Name: " + file.getName());
        System.out.println("Absolute path: " + file.getAbsolutePath());
        System.out.println("Relative path: " + file.getPath());
        System.out.println("Parent path: " + (file.getParent() != null ? file.getParent() : "No parent"));

        if (file.isFile()) {
            long sizeBytes = file.length();
            System.out.printf("Size: %d bytes (%.2f KB, %.2f MB)%n", sizeBytes, sizeBytes / 1024.0,
                    sizeBytes / (1024.0 * 1024.0));
        } else {
            long sizeBytes = calculateDirectorySize(file);
            System.out.printf("Size: %d bytes (%.2f KB, %.2f MB)%n", sizeBytes, sizeBytes / 1024.0,
                    sizeBytes / (1024.0 * 1024.0));

            // Muestra el número de archivos y carpetas
            File[] files = file.listFiles();
            if (files != null) {
                int fileCount = 0;
                int folderCount = 0;
                for (File f : files) {
                    if (f.isFile()) {
                        fileCount++;
                    } else if (f.isDirectory()) {
                        folderCount++;
                    }
                }
                System.out.println("Number of files: " + fileCount);
                System.out.println("Number of folders: " + folderCount);
            }
        }

        System.out.println("Is hidden: " + (file.isHidden() ? "Yes" : "No"));
        String permissions = (file.canRead() ? "r" : "-") + (file.canWrite() ? "w" : "-")
                + (file.canExecute() ? "x" : "-");
        System.out.println("Permissions: " + permissions);

        // Mostrar la fecha de creación
        showCreationDate(file);

        Date lastModified = new Date(file.lastModified());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println("Last modification: " + sdf.format(lastModified));

        System.out.println("---------------------------------------\n");
    }

    /**
     * Muestra la fecha de creación del archivo o directorio.
     *
     * @param file el archivo o directorio
     */
    public static void showCreationDate(File file) {
        try {
            Path path = file.toPath();
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            FileTime creationTime = attr.creationTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            System.out.println("Creation date: " + sdf.format(new Date(creationTime.toMillis())));
        } catch (IOException e) {
            System.out.println("Could not retrieve creation date: " + e.getMessage());
        }
    }

    /**
     * Calcula el tamaño total de un directorio y su contenido.
     *
     * @param directory el directorio cuyo tamaño se va a calcular
     * @return el tamaño total en bytes
     */
    public static long calculateDirectorySize(File directory) {
        long totalSize = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    totalSize += file.length();
                } else if (file.isDirectory()) {
                    totalSize += calculateDirectorySize(file);
                }
            }
        }
        return totalSize;
    }

    /**
     * Busca el texto proporcionado en todos los archivos del directorio y
     * muestra la línea y columna de cada aparición.
     *
     * @param directory el directorio en el que buscar
     * @param searchText el texto a buscar
     */
    public static void searchTextInDirectory(File directory, String searchText) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    searchTextInFile(file, searchText);
                } else if (file.isDirectory()) {
                    searchTextInDirectory(file, searchText);
                }
            }
        } else {
            System.out.println("No file found with the text.");
        }
    }

    /**
     * Busca el texto proporcionado en el archivo y muestra la línea y columna
     * de cada aparición.
     *
     * @param file el archivo en el que buscar
     * @param searchText el texto a buscar
     */
    public static void searchTextInFile(File file, String searchText) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                int columnIndex = line.indexOf(searchText);
                while (columnIndex != -1) {
                    System.out.printf("Found in File: %s, Line: %d, Column: %d%n", file.getName(), lineNumber,
                            columnIndex + 1);
                    columnIndex = line.indexOf(searchText, columnIndex + 1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
