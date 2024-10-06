package Unidad01.Practica03;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * Clase para leer archivos y directorios secuencialmente.
 * Permite a los usuarios ingresar una ruta de archivo o directorio,
 * y muestra detalles sobre el archivo o directorio especificado.
 */
public class SequentialFileReading {

    /**
     * Método principal para ejecutar el programa.
     * Solicita continuamente al usuario una ruta de archivo o directorio.
     *
     * @param args argumentos de línea de comandos (ruta opcional)
     */
    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String path = getPath(args, scanner);

            // Condición de salida
            if (path.equals("1")) {
                System.out.println("\nThe program has finished successfully.");
                scanner.close();
                break;
            }

            File file = new File(path);
            System.out.println("\n---------------------------------------");

            // Verifica si el archivo o directorio existe
            if (!file.exists()) {
                System.out.println("The file or directory " + file.getName() + " exists: " + file.exists());
                System.out.println("---------------------------------------\n");
            } else {
                System.out.println("The file or directory " + file.getName() + " exists: " + file.exists());
                System.out.println("---------------------------------------");

                // Muestra detalles según si es un archivo o un directorio
                if (file.isFile()) {
                    showDetails(file, false);
                } else if (file.isDirectory()) {
                    showDetails(file, true);
                    System.out.println("               CONTENT                 ");
                    listContents(file);
                }
            }
        }
    }

    /**
     * Recupera la ruta de los argumentos de línea de comandos o solicita al usuario que ingrese una.
     *
     * @param args argumentos de línea de comandos
     * @param scanner objeto Scanner para la entrada del usuario
     * @return la ruta como un String
     */
    public static String getPath(String[] args, Scanner scanner) {
        if (args.length > 0) {
            return args[0];
        } else {
            System.out.print("Enter a path (1 to exit): ");
            return scanner.nextLine();
        }
    }

    /**
     * Muestra los detalles del archivo o directorio dado.
     *
     * @param file el archivo o directorio a mostrar
     * @param isDirectory si es un directorio
     */
    public static void showDetails(File file, boolean isDirectory) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            String type = file.isDirectory() ? "Directory" : "File";
            System.out.println("\n---------------------------------------");
            System.out.println("Type: " + type);
            System.out.println("Name: " + file.getName());
            System.out.println("Absolute path: " + file.getAbsolutePath());
            System.out.println("Relative path: " + file.getPath());
            System.out.println("Parent path: " + (file.getParent() != null ? file.getParent() : "No parent"));

            // Mostrar tamaño
            long sizeBytes = file.isFile() ? file.length() : calculateDirectorySize(file);
            System.out.printf("Size: %d bytes (%.2f KB, %.2f MB)%n", sizeBytes, sizeBytes / 1024.0, sizeBytes / (1024.0 * 1024.0));

            // Si es un directorio, cuenta el número de archivos y carpetas
            if (isDirectory) {
                int[] counts = countFilesAndFolders(file);
                System.out.println("Number of files: " + counts[0]);
                System.out.println("Number of folders: " + counts[1]);
            }

            // Mostrar otros detalles
            System.out.println("Is hidden: " + (file.isHidden() ? "Yes" : "No"));
            String permissions = (file.canRead() ? "r" : "-") + (file.canWrite() ? "w" : "-") + (file.canExecute() ? "x" : "-");
            System.out.println("Permissions: " + permissions);

            // Mostrar fecha de creación
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            System.out.println("Creation date: " + sdf.format(new Date(attrs.creationTime().toMillis())));
            System.out.println("Last modification: " + sdf.format(new Date(file.lastModified())));

            System.out.println("---------------------------------------\n");

        } catch (IOException e) {
            System.err.println("Error retrieving file attributes: " + e.getMessage());
        }
    }

    /**
     * Lista el contenido de un directorio, mostrando los detalles de cada archivo y subdirectorio.
     *
     * @param directory el directorio cuyo contenido se va a listar
     */
    public static void listContents(File directory) {
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("The directory is empty.");
        } else {
            for (File file : files) {
                showDetails(file, file.isDirectory());
            }
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
     * Cuenta el número de archivos y carpetas en un directorio.
     *
     * @param directory el directorio cuyo contenido se va a contar
     * @return un array donde [0] es el número de archivos y [1] es el número de carpetas
     */
    public static int[] countFilesAndFolders(File directory) {
        int fileCount = 0;
        int folderCount = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileCount++;
                } else if (file.isDirectory()) {
                    folderCount++;
                }
            }
        }
        return new int[]{fileCount, folderCount};
    }
 
    // Contar vocales usando FileReader (NO SE INCLUYE)
    /*
    public static int[] countVowelsUsingFileReader(File file) {
        int[] counts = new int[5]; // A, E, I, O, U
        try (FileReader fr = new FileReader(file)) {
            int ch;
            while ((ch = fr.read()) != -1) {
                char character = Character.toLowerCase((char) ch);
                switch (character) {
                    case 'a' -> counts[0]++;
                    case 'e' -> counts[1]++;
                    case 'i' -> counts[2]++;
                    case 'o' -> counts[3]++;
                    case 'u' -> counts[4]++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return counts;
    }
    */
}
