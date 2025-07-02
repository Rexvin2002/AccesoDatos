/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.io.*;
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
                    showDetails(file);
                } else if (file.isDirectory()) {
                    showDetails(file);
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
            System.out.printf("Size: %d bytes (%.2f KB, %.2f MB)%n", sizeBytes, sizeBytes / 1024.0, sizeBytes / (1024.0 * 1024.0));
            int[] vowelCounts = countVowelsUsingBufferedReader(file);
            System.out.printf("Vowel counts: A=%d, E=%d, I=%d, O=%d, U=%d%n", vowelCounts[0], vowelCounts[1], vowelCounts[2], vowelCounts[3], vowelCounts[4]);
        } else {
            long sizeBytes = calculateDirectorySize(file);
            System.out.printf("Size: %d bytes (%.2f KB, %.2f MB)%n", sizeBytes, sizeBytes / 1024.0, sizeBytes / (1024.0 * 1024.0));
        }

        System.out.println("Is hidden: " + (file.isHidden() ? "Yes" : "No"));
        String permissions = (file.canRead() ? "r" : "-") + (file.canWrite() ? "w" : "-") + (file.canExecute() ? "x" : "-");
        System.out.println("Permissions: " + permissions);
        Date lastModified = new Date(file.lastModified());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println("Last modification: " + sdf.format(lastModified));

        System.out.println("---------------------------------------\n");
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
                showDetails(file);
                if (file.isDirectory()) {
                    listContents(file);
                }
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

    // Contar vocales usando BufferedReader
    public static int[] countVowelsUsingBufferedReader(File file) {
        int[] counts = new int[5]; // A, E, I, O, U
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                for (char ch : line.toLowerCase().toCharArray()) {
                    switch (ch) {
                        case 'a' -> counts[0]++;
                        case 'e' -> counts[1]++;
                        case 'i' -> counts[2]++;
                        case 'o' -> counts[3]++;
                        case 'u' -> counts[4]++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return counts;
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
