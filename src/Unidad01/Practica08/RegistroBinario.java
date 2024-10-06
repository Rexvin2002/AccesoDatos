package Unidad01.Practica08;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para manejar registros en un archivo binario.
 * 
 * @author kgv17
 */
public class RegistroBinario {

    private final File file;
    private final List<String> fields;
    private final List<Integer> fieldLengths;
    private final long bytesPerLine; // Bytes por línea
    
    // Constructor
    public RegistroBinario(String path, List<String> fields, List<Integer> fieldLengths) throws IOException {
        
        this.file = new File(path);
        if (!this.file.exists()) {
            this.file.createNewFile();
        }

        this.fields = fields;
        this.fieldLengths = fieldLengths;

        // Calcular los bytes por línea
        this.bytesPerLine = fieldLengths.stream().mapToInt(Integer::intValue).sum();

        // Mostrar bytes por campo en la terminal
        System.out.println("\nBYTES POR CAMPO / COLUMNA");
        for (int i = 0; i < fields.size(); i++) {
            System.out.println(fields.get(i) + ": " + fieldLengths.get(i));
        }
        System.out.println();
        
    }

    // Método para obtener el número de filas
    public long getNumRows() {
        return bytesPerLine > 0 ? this.file.length() / this.bytesPerLine : 0;
    }

    // Método para insertar o modificar registros en el archivo
    public void insertOrUpdateRecord(Map<String, String> data, long position) {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "rws")) {
            System.out.println("\nINSERTANDO/MODIFICANDO REGISTRO EN POSICIÓN: " + position);

            // Posicionarse para escribir
            rndFile.seek(position * this.bytesPerLine);

            // Escribir cada campo en el archivo
            for (int i = 0; i < fields.size(); i++) {
                String field = fields.get(i);
                int length = fieldLengths.get(i);
                String fieldValue = data.getOrDefault(field, "");

                // Formatear valor al tamaño requerido
                String formattedValue = String.format("%1$-" + length + "s", fieldValue);

                System.out.println(field + ": " + formattedValue);

                // Escribir en el archivo
                rndFile.write(formattedValue.getBytes(StandardCharsets.UTF_8), 0, length);
            }
            System.out.println();
        } catch (IOException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
    }

    // Método para leer registros del archivo
    public void readRecord(long position) {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "r")) {
            System.out.println("\nLEYENDO REGISTRO EN POSICIÓN: " + position);

            // Posicionarse para leer
            rndFile.seek(position * this.bytesPerLine);

            // Leer cada campo del archivo
            for (int i = 0; i < fields.size(); i++) {
                int length = fieldLengths.get(i);
                byte[] buffer = new byte[length];
                rndFile.read(buffer, 0, length);
                String fieldValue = new String(buffer, StandardCharsets.UTF_8).trim();
                System.out.println(fields.get(i) + ": " + fieldValue);
            }
            System.out.println();
        } catch (IOException ex) {
            System.err.println("\nERROR: " + ex.getMessage());
        }
    }

    // Método para obtener entrada del usuario
    private static Map<String, String> getUserInput(List<String> fields) {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> data = new HashMap<>();
        for (String field : fields) {
            System.out.print("Ingrese " + field + ": ");
            data.put(field, scanner.nextLine());
        }
        return data;
    }

    // Método principal con menú
    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            List<String> fields = List.of("DNI", "NOMBRE", "DIRECCION", "CP", "FECHA");
            List<Integer> fieldLengths = List.of(9, 32, 32, 5, 10);
            
            try {
                RegistroBinario registroBinario = new RegistroBinario("src/Unidad01/Practica08/archivo_binario.dat", fields, fieldLengths);
                Scanner scanner = new Scanner(System.in);
                int option;
                
                do {
                    System.out.println("\nMENU:");
                    System.out.println("1. Insertar/Modificar Registro");
                    System.out.println("2. Leer Registro");
                    System.out.println("3. Salir");
                    System.out.print("\nSeleccione una opción: ");
                    option = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea
                    
                    switch (option) {
                        case 1 -> {
                            System.out.print("\nIngrese la posición del registro a insertar/modificar: ");
                            long position = scanner.nextLong();
                            scanner.nextLine(); // Consumir nueva línea
                            Map<String, String> data = getUserInput(fields);
                            registroBinario.insertOrUpdateRecord(data, position);
                        }
                        case 2 -> {
                            System.out.print("\nIngrese la posición del registro a leer: ");
                            long readPosition = scanner.nextLong();
                            registroBinario.readRecord(readPosition);
                        }
                        case 3 -> System.out.println("\nSaliendo...");
                        default -> System.out.println("\nOpción no válida.");
                    }
                } while (option != 3);
                
            } catch (IOException e) {
                System.err.println("\nERROR: " + e.getMessage());
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RegistroBinario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
