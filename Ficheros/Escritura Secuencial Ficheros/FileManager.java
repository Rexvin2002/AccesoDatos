package Unidad01.Practica05;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Programa que permite crear y modificar archivos de texto.
 */
public class FileManager {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Create a new file");
            System.out.println("2. Add text to the beginning of a file");
            System.out.println("3. Add text to the end of a file");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            // Validar la entrada del usuario
            int option = 0;
            try {
                option = scanner.nextInt();  // Leer la opción
                scanner.nextLine();  // Limpiar el buffer
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a number.");
                scanner.nextLine();  // Limpiar el buffer si la entrada no es un número
                continue;  // Volver a mostrar el menú
            }

            switch (option) {
                case 1 -> createFile(scanner);
                case 2 -> prependToFile(scanner);
                case 3 -> appendToFile(scanner);
                case 4 -> {
                    System.out.println("\nExiting program.");
                    scanner.close();
                    return;
                }
                default -> System.err.println("Invalid option. Try again.");
            }
        }
    }


    /**
     * Crea un archivo y escribe contenido en él.
     *
     * @param scanner Objeto Scanner para la entrada del usuario
     */
    public static void createFile(Scanner scanner) {
        System.out.print("Enter the file name (including .txt): ");
        String fileName = scanner.nextLine();
        File file = new File(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            System.out.println("Enter the content to write to the file:");
            String content = scanner.nextLine();
            writer.write(content);
            System.out.println("File created successfully.");
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    /**
     * Añade texto al final del archivo.
     *
     * @param scanner Objeto Scanner para la entrada del usuario
     */
    public static void appendToFile(Scanner scanner) {
        System.out.print("Enter the file name: ");
        String fileName = scanner.nextLine();
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("The file does not exist.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            System.out.println("Enter the content to append to the file:");
            String content = scanner.nextLine();
            writer.write("\n" + content);
            System.out.println("Content appended to file.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Añade texto al comienzo del archivo.
     *
     * @param scanner Objeto Scanner para la entrada del usuario
     */
    public static void prependToFile(Scanner scanner) {
        System.out.print("Enter the file name: ");
        String fileName = scanner.nextLine();
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("The file does not exist.");
            return;
        }

        try {
            StringBuilder fileContent;
            try ( // Leer el contenido existente
                    BufferedReader reader = new BufferedReader(new FileReader(file))) {
                fileContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");
                }
            }

            // Escribir el nuevo contenido al principio
            System.out.println("Enter the content to prepend to the file:");
            String newContent = scanner.nextLine();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(newContent + "\n" + fileContent.toString());
            }

            System.out.println("Content prepended to file.");
        } catch (IOException e) {
            System.err.println("Error modifying file: " + e.getMessage());
        }
    }
}
