package Unidad01.Practica06;

import java.io.*;
import java.util.Scanner;

public class FileProcessor {
    public static void main(String[] args) {
        System.out.println("Please enter the path of a file: ");
        String inputFilePath = new Scanner(System.in).nextLine();

        // Procesar el archivo
        try {
            processFile(inputFilePath);
            System.out.println("File processed successfully.");
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }

    /**
     * Procesa el archivo: convierte a mayúsculas después de puntos y elimina espacios en blanco consecutivos.
     *
     * @param inputFilePath ruta del archivo de entrada
     * @throws IOException si ocurre un error durante la lectura o escritura del archivo
     */
    public static void processFile(String inputFilePath) throws IOException {
        File inputFile = new File(inputFilePath);

        // Crear un archivo temporal en la ubicación predeterminada
        File tempFile = File.createTempFile("tempFile_", ".txt");
        
        // Mostrar la ruta del archivo original y del archivo temporal
        System.out.println("Original file path: " + inputFile.getAbsolutePath());
        System.out.println("Temporary file path: " + tempFile.getAbsolutePath());

        // Leer del archivo de entrada y escribir en el archivo temporal
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Procesar la línea: forzar mayúscula después de '.' y eliminar espacios
                String processedLine = processLine(line);
                writer.write(processedLine);
                writer.newLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading/writing file: " + e.getMessage());
        }

        // Crear un nuevo archivo procesado a partir del archivo temporal
        String processedFilePath = inputFile.getParent() + File.separator + "processed_" + inputFile.getName();
        createProcessedFileFromTemp(tempFile, processedFilePath);
    }

    /**
     * Crea un nuevo archivo procesado a partir del archivo temporal.
     *
     * @param tempFile archivo temporal
     * @param processedFilePath ruta del nuevo archivo a crear
     */
    private static void createProcessedFileFromTemp(File tempFile, String processedFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(processedFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Processed file created at: " + processedFilePath);
        } catch (IOException e) {
            System.err.println("Error creating processed file: " + e.getMessage());
        }
    }

    /**
     * Procesa una línea de texto.
     * Convierte a mayúsculas después de un punto y elimina espacios en blanco consecutivos.
     *
     * @param line la línea a procesar
     * @return la línea procesada
     */
    public static String processLine(String line) {
        // Eliminar múltiples espacios en blanco
        String cleanedLine = line.replaceAll("\\s+", " ").trim();

        // Forzar mayúscula después de '.'
        StringBuilder result = new StringBuilder();
        boolean toUpperCase = false;

        for (char ch : cleanedLine.toCharArray()) {
            if (toUpperCase && Character.isLetter(ch)) {
                result.append(Character.toUpperCase(ch));
                toUpperCase = false; // Resetear la bandera
            } else {
                result.append(ch);
            }
            if (ch == '.') {
                toUpperCase = true; // Configurar para convertir la siguiente letra a mayúscula
            }
        }

        return result.toString(); // Devolver la línea procesada
    }
}
