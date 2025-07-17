package Main;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class FileProcessor {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String inputFilePath;
            boolean fileValid = false;

            do {
                System.out.println("Please enter the path of a file (or 'exit' to quit): ");
                inputFilePath = scanner.nextLine().trim();

                if (inputFilePath.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;
                }

                if (inputFilePath.isEmpty()) {
                    System.err.println("Error: No file path provided. Please try again.\n");
                    continue;
                }

                try {
                    processFile(inputFilePath);
                    System.out.println("\nFile processed successfully.");
                    fileValid = true;
                } catch (FileNotFoundException e) {
                    System.err.println("Error: " + e.getMessage() + " Please try again.\n");
                } catch (IOException e) {
                    System.err.println("Error processing file: " + e.getMessage() + " Please try again.\n");
                }
            } while (!fileValid);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void processFile(String inputFilePath) throws IOException {
        // Verificar si el archivo existe y es legible
        Path inputPath = Paths.get(inputFilePath);
        if (!Files.exists(inputPath)) {
            throw new FileNotFoundException("The file '" + inputFilePath + "' does not exist.");
        }
        if (!Files.isRegularFile(inputPath)) {
            throw new IOException("The path '" + inputFilePath + "' is not a regular file.");
        }
        if (!Files.isReadable(inputPath)) {
            throw new IOException("The file '" + inputFilePath + "' is not readable.");
        }

        File inputFile = inputPath.toFile();

        // Crear archivo temporal en el directorio temporal del sistema
        File tempFile;
        try {
            tempFile = Files.createTempFile("tempFile_", ".txt").toFile();
            // Eliminar esta línea: tempFile.deleteOnExit();
        } catch (IOException e) {
            throw new IOException("Could not create temporary file: " + e.getMessage(), e);
        }

        System.out.println("\nOriginal file path: " + inputFile.getAbsolutePath());
        System.out.println("Temporary file path: " + tempFile.getAbsolutePath());

        // Procesar el archivo
        processFileContent(inputFile, tempFile);

        // Crear archivo procesado
        String processedFilePath = inputFile.getParent() + File.separator + "processed_" + inputFile.getName();
        createProcessedFileFromTemp(tempFile, processedFilePath);
    }

    private static void processFileContent(File inputFile, File tempFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile)); BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String processedLine = processLine(line);
                writer.write(processedLine);
                writer.newLine();
            }
        }
    }

    private static void createProcessedFileFromTemp(File tempFile, String processedFilePath) throws IOException {
        Path processedPath = Paths.get(processedFilePath);

        // Verificar si el archivo de salida ya existe
        if (Files.exists(processedPath)) {
            throw new IOException("Processed file already exists at: " + processedFilePath);
        }

        // Renombrar el archivo temporal al nombre final
        if (!tempFile.renameTo(new File(processedFilePath))) {
            throw new IOException("Failed to rename temporary file to processed file");
        }

        System.out.println("Processed file created at: " + processedPath.toAbsolutePath());
    }

    public static String processLine(String line) {
        if (line == null) {
            return "";
        }

        String cleanedLine = line.replaceAll("\\s+", " ").trim();

        if (cleanedLine.isEmpty()) {
            return cleanedLine;
        }

        StringBuilder result = new StringBuilder();
        boolean toUpperCase = true;

        for (char ch : cleanedLine.toCharArray()) {
            if (toUpperCase && Character.isLetter(ch)) {
                result.append(Character.toUpperCase(ch));
                toUpperCase = false;
            } else {
                result.append(ch);
            }
            if (ch == '.') {
                toUpperCase = true;
            }
        }

        return result.toString();
    }
}
