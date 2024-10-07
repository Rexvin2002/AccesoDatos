package Unidad01.Practica09;

import java.io.*;
import java.util.*;

/**
 * The BinaryRecord class handles operations for reading and writing records in a binary file.
 * Each record consists of multiple fields of specified lengths.
 */
public class AdvancedBinaryRecord {

    private final File file;                      // The binary file to store records.
    private final List<String> fields;            // List of field names.
    private final List<Integer> fieldLengths;     // Corresponding lengths of each field.
    private long recordLength;                     // Total length of a single record.
    private long recordCount = 0;                 // Total number of records in the file.

    /**
     * Constructor to initialize the BinaryRecord with a file path, field names, and field lengths.
     * 
     * @param path         The path of the binary file.
     * @param fields       The list of field names.
     * @param fieldLengths The list of corresponding field lengths.
     * @throws IOException If there is an error creating the file or accessing it.
     */
    public AdvancedBinaryRecord(String path, List<String> fields, List<Integer> fieldLengths) throws IOException {
        this.fields = fields;
        this.fieldLengths = fieldLengths;

        this.file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        
        this.recordLength = 0;
        for (int i = 0; i < fields.size(); i++) {
            this.recordLength += fieldLengths.get(i);
        }

        if (file.exists()) {
            this.recordCount = file.length() / this.recordLength;
        }
    }

    /**
     * Gets the total number of records in the binary file.
     * 
     * @return The number of records.
     */
    public long getRecordCount() {
        return recordCount;
    }

    // 1. Método selectCampo
    public String selectCampo(int numRegistro, String nomColumna) {
        Map<String, String> record = read(numRegistro);
        return record.getOrDefault(nomColumna, null);
    }

    // 2. Método selectColumna
    public List<String> selectColumna(String nomColumna) {
        List<String> values = new ArrayList<>();
        int colIndex = fields.indexOf(nomColumna);
        if (colIndex == -1) {
            return values; // Return empty list if column doesn't exist
        }

        for (int i = 0; i < recordCount; i++) {
            Map<String, String> record = read(i);
            values.add(record.get(nomColumna));
        }
        return values;
    }

    // 3. Método selectRowList
    public List<String> selectRowList(int numRegistro) {
        Map<String, String> record = read(numRegistro);
        return new ArrayList<>(record.values());
    }

    // 4. Método selectRowMap
    public Map<String, String> selectRowMap(int numRegistro) {
        return read(numRegistro);
    }

    // 5.1 Método update con Map
    public void update(int row, Map<String, String> updates) {
        Map<String, String> existingRecord = read(row);
        for (String field : updates.keySet()) {
            existingRecord.put(field, updates.get(field));
        }
        insert(existingRecord, row);
    }

    // 5.2 Método update con campo y valor
    public void update(int row, String campo, String valor) {
        Map<String, String> existingRecord = read(row);
        existingRecord.put(campo, valor);
        insert(existingRecord, row);
    }

    // 6. Método delete
    public void delete(int row) {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "rw")) {
            rndFile.seek(row * this.recordLength);
            byte[] emptyBytes = new byte[(int) this.recordLength];
            rndFile.write(emptyBytes); // Write empty bytes to "clear" the record
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Inserts a new record at the specified position in the binary file.
     * 
     * @param record A map containing the field names and their corresponding values to be written.
     * @param position The position at which to insert the record.
     */
    public void insert(Map<String, String> record, long position) {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "rws")) {
            rndFile.seek(position * this.recordLength);
            int totalFields = fields.size();
            for (int i = 0; i < totalFields; i++) {
                String fieldName = fields.get(i);
                Integer fieldLength = fieldLengths.get(i);
                String fieldValue = record.getOrDefault(fieldName, "");

                // Format the field value to fit the specified length.
                String formattedFieldValue = String.format("%1$-" + fieldLength + "s", fieldValue);
                rndFile.write(formattedFieldValue.getBytes("UTF-8"), 0, fieldLength);
            }
        } catch (Exception ex) {
            System.err.println("\nError: " + ex.getMessage());
        }
    }

    /**
     * Reads a record from the binary file at the specified position.
     * 
     * @param position The position of the record to read.
     * @return A map containing the field names and their corresponding values read from the file.
     */
    public Map<String, String> read(long position) {
        Map<String, String> record = new HashMap<>();
        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "r")) {
            rndFile.seek(position * this.recordLength);
            for (int i = 0; i < fields.size(); i++) {
                Integer fieldLength = fieldLengths.get(i);
                byte[] buffer = new byte[fieldLength];
                rndFile.read(buffer, 0, fieldLength);
                String fieldValue = new String(buffer, "UTF-8").trim();
                record.put(fields.get(i), fieldValue);
            }
        } catch (Exception ex) {
            System.err.println("\nError: " + ex.getMessage());
        }
        return record;
    }

    /**
     * Modifies an existing record by inserting a new record at the specified position.
     * 
     * @param record A map containing the field names and their corresponding values to be written.
     * @param position The position at which to modify the record.
     */
    public void modify(Map<String, String> record, long position) {
        insert(record, position);
        System.out.println("\nPosition modified successfully.");
    }

    /**
     * Main method to execute the BinaryRecord functionalities.
     * 
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            Scanner scanner = new Scanner(System.in);
            List<String> fields = Arrays.asList("ID", "NAME", "DIRECTION", "ZC");
            List<Integer> fieldLengths = Arrays.asList(9, 32, 32, 5);

            try {
                AdvancedBinaryRecord binaryRecord = new AdvancedBinaryRecord("src/Unidad01/Practica08/binary_file_1.dat", fields, fieldLengths);

                Map<String, String> record = new HashMap<>();

                // Inicialización de registros
                record.put("ID", "11111111A");
                record.put("NAME", "Nombre y Apellidos 1");
                record.put("DIRECTION", "Calle Principal Nº 7, Planta4, Letra J");
                record.put("ZC", "54321");
                binaryRecord.insert(record, 0);
                record.clear();

                record.put("ID", "22222222B");
                record.put("NAME", "Nombre2");
                record.put("DIRECTION", "Calle Principal Nº 7, Planta4, Letra J");
                record.put("ZC", "12345");
                binaryRecord.insert(record, 1);
                record.clear();

                record.put("ID", "33333333C");
                record.put("NAME", "Nombre3");
                record.put("DIRECTION", "Direccion 3");
                record.put("ZC", "56789");
                binaryRecord.insert(record, 2);
                record.clear();

                while (true) {
                    int option = 0;
                    while (true) {
                        System.out.println("\nMenu:");
                        System.out.println("1. Insert record");
                        System.out.println("2. Read record");
                        System.out.println("3. Modify record");
                        System.out.println("4. Select field");
                        System.out.println("5. Select column");
                        System.out.println("6. Select row as list");
                        System.out.println("7. Select row as map");
                        System.out.println("8. Update record");
                        System.out.println("9. Delete record");
                        System.out.println("10. Exit");
                        System.out.print("\nChoose an option: ");

                        // Comprobar si se ha introducido un número
                        if (scanner.hasNextInt()) {
                            option = scanner.nextInt();
                            scanner.nextLine(); // Consumir nueva línea

                            // Verificar si la opción está en el rango válido
                            if (option >= 1 && option <= 10) {
                                break; // Opción válida, salir del bucle
                            } else {
                                System.out.println("Invalid option. Please choose a number between 1 and 10.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a number.");
                            scanner.nextLine(); // Limpiar la entrada no válida
                        }
                    }

                    switch (option) {
                        case 1 -> {
                            boolean validPosition = false;
                            long positionToInsert = -1; // Valor inicial para la posición

                            while (!validPosition) {
                                System.out.print("\nEnter the position to insert (c to cancel): ");

                                // Verificar si se ha introducido 'c' para cancelar
                                String input = scanner.nextLine();

                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break; // Volver al menú en lugar de salir del bucle
                                }

                                // Intentar convertir la entrada a un número
                                try {
                                    positionToInsert = Long.parseLong(input);

                                    // Verificar si existe un registro en la posición especificada
                                    Map<String, String> existingRecord = binaryRecord.read(positionToInsert);
                                    boolean isEmpty = existingRecord.values().stream().allMatch(String::isEmpty);

                                    if (!isEmpty) {
                                        System.out.println("\nA record already exists at position " + positionToInsert + ":");
                                        existingRecord.forEach((field, value) -> System.out.println(field + ": " + value));

                                        // Preguntar si desea sobrescribir el registro existente
                                        System.out.print("\nDo you want to overwrite it? (y/n): ");
                                        String confirm = scanner.nextLine();

                                        if (confirm.equalsIgnoreCase("n")) {
                                            System.out.println("\nInsertion canceled.");
                                            continue; // Volver a pedir la posición
                                        } else if (confirm.equalsIgnoreCase("y")) {
                                            System.out.println("\nOverwriting the existing record at position " + positionToInsert + ".");
                                        } else {
                                            System.out.println("Invalid input. Please enter 'y' or 'n'.");
                                            continue; // Volver a pedir la posición
                                        }
                                    } else {
                                        System.out.println("\nNo record exists at position " + positionToInsert + ", a new one will be created.");
                                    }

                                    validPosition = true; // La posición es válida

                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid position or 'c' to cancel.");
                                }
                            }

                            // Si se encontró una posición válida, solicitar datos para el nuevo registro
                            if (validPosition) {
                                // Solicitar datos para el nuevo registro
                                Map<String, String> newRecord = new HashMap<>();
                                for (String field : fields) {
                                    System.out.print("Enter " + field + ": ");
                                    String value = scanner.nextLine();
                                    newRecord.put(field, value);
                                }

                                // Insertar el registro en la posición especificada
                                binaryRecord.insert(newRecord, positionToInsert);
                                System.out.println("\nRecord inserted at position " + positionToInsert + ".");
                            }
                        }
                        case 2 -> {
                            boolean validPosition = false; // Variable para controlar la validez de la posición
                            long positionToRead = -1; // Valor inicial para la posición

                            while (!validPosition) {
                                System.out.print("\nEnter the position of the record to read (c to cancel): ");

                                // Leer la entrada como cadena
                                String input = scanner.nextLine();

                                // Verificar si se ha introducido 'c' para cancelar
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break; // Volver al menú en lugar de salir del bucle
                                }

                                // Intentar convertir la entrada a un número
                                try {
                                    positionToRead = Long.parseLong(input);

                                    // Leer el registro en la posición especificada
                                    Map<String, String> readRecord = binaryRecord.read(positionToRead);

                                    // Verificar si el registro tiene valores no vacíos
                                    boolean isEmpty = readRecord.values().stream().allMatch(String::isEmpty);

                                    if (!isEmpty) {
                                        System.out.println("\nRecord read:");
                                        readRecord.forEach((field, value) -> System.out.println(field + ": " + value));
                                        validPosition = true; // La posición es válida
                                    } else {
                                        System.out.println("\nThe record at the specified position is empty. Please try again.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid position or 'c' to cancel.");
                                }
                            }
                        }
                        case 3 -> {
                            boolean validPosition = false; // Variable para controlar la validez de la posición
                            long positionToModify = -1; // Valor inicial para la posición

                            while (!validPosition) {
                                System.out.print("\nEnter the position of the record to modify: ");

                                // Verificar si se ha introducido un número
                                if (scanner.hasNextLong()) {
                                    positionToModify = scanner.nextLong();
                                    scanner.nextLine(); // Consumir nueva línea

                                    // Leer el registro en la posición especificada
                                    Map<String, String> existingRecord = binaryRecord.read(positionToModify);
                                    if (!existingRecord.isEmpty()) {
                                        System.out.println("\nCurrent record at position " + positionToModify + ":");
                                        existingRecord.forEach((field, value) -> System.out.println(field + ": " + value));

                                        // Crear un nuevo registro para modificar
                                        Map<String, String> modifiedRecord = new HashMap<>();
                                        System.out.println();
                                        for (String field : fields) {
                                            System.out.print("Enter new value for " + field + " (leave blank to keep current value): ");
                                            String value = scanner.nextLine();
                                            // Solo se modifica si se proporciona un nuevo valor
                                            if (!value.isEmpty()) {
                                                modifiedRecord.put(field, value);
                                            } else {
                                                modifiedRecord.put(field, existingRecord.get(field)); // Mantener el valor actual si no se proporciona uno nuevo
                                            }
                                        }
                                        binaryRecord.modify(modifiedRecord, positionToModify);
                                        System.out.println("\nRecord at position " + positionToModify + " modified successfully.");
                                        validPosition = true; // La posición es válida
                                    } else {
                                        System.out.println("\nNo record found at the specified position. Please try again.");
                                    }
                                } else {
                                    System.out.println("Invalid input. Please enter a valid position.");
                                    scanner.nextLine(); // Limpiar la entrada no válida
                                }
                            }
                        }
                        case 4 -> {
                            boolean validInput = false;
                            while (!validInput) {
                                System.out.print("\nEnter the record number (or 'c' to cancel): ");
                                String input = scanner.nextLine().trim();
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break;
                                }

                                try {
                                    int numRegistro = Integer.parseInt(input);
                                    if (numRegistro < 0) {
                                        System.out.println("Error: The record number must be a non-negative integer.");
                                    } else {
                                        Map<String, String> registro = binaryRecord.read(numRegistro);
                                        if (registro == null || registro.isEmpty()) {
                                            System.out.println("Error: No record found for record number " + numRegistro + ".");
                                        } else {
                                            System.out.println("Record " + numRegistro + " data:");
                                            registro.forEach((field, value) -> System.out.println(field + ": " + value));
                                            validInput = true;
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Please enter a valid number for the record.");
                                }
                            }
                        }
                        case 5 -> {
                            boolean validInput = false; // Variable para controlar la validez de la entrada

                            while (!validInput) {
                                System.out.print("\nEnter the record number (or 'c' to cancel): ");

                                String input = scanner.nextLine(); // Leer la entrada del usuario

                                // Manejo de la entrada para salir
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break;
                                }

                                // Intentar convertir la entrada a un número entero
                                int recordNumber;
                                try {
                                    recordNumber = Integer.parseInt(input);
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Please enter a valid record number.");
                                    continue; // Volver a mostrar el menú
                                }

                                // Verificar que el número de registro sea válido
                                if (recordNumber < 0) {
                                    System.out.println("Error: The record number must be a non-negative integer.");
                                    continue; // Volver a mostrar el menú
                                }

                                // Si llegamos aquí, tenemos un número de registro válido
                                validInput = true; // Marcar como entrada válida
                                while (true) {
                                    System.out.print("Enter the column name: ");
                                    String nomColumna = scanner.nextLine().toUpperCase(); // Convertir a mayúsculas para comparación

                                    // Obtener el valor del campo en la columna especificada
                                    String value = binaryRecord.selectCampo(recordNumber, nomColumna);

                                    // Verificar si se obtuvo un valor válido
                                    if (value != null) {
                                        System.out.println("\nValue in record " + recordNumber + ", column '" + nomColumna + "': " + value);
                                        break;
                                    } else {
                                        System.out.println("\nNo value found for record number " + recordNumber + " in column '" + nomColumna + "'.\n");
                                    }
                                }
                            }
                        }
                        case 6 -> {
                            while (true) {
                                System.out.print("\nEnter the record number to select as list (or 'c' to cancel): ");
                                String input = scanner.nextLine().trim(); // Leer la entrada como String y eliminar espacios en blanco

                                // Manejo de la entrada para salir
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break;
                                }

                                try {
                                    int numRegistro = Integer.parseInt(input); // Intentar convertir a entero

                                    // Obtener los valores de la fila como lista
                                    List<String> rowValues = binaryRecord.selectRowList(numRegistro);

                                    // Verificar si se obtuvieron valores
                                    if (rowValues != null && !rowValues.isEmpty()) {
                                        System.out.println("Row values: " + rowValues);
                                        break; // Salir del bucle si se obtienen valores
                                    } else {
                                        System.out.println("No values found for record number " + numRegistro + ".");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Please enter a valid integer for the record number.");
                                }
                            }
                        }
                        case 7 -> {
                            while (true) {
                                System.out.print("\nEnter the record number to select as map (c to cancel): ");

                                String input = scanner.nextLine(); // Leer la entrada como cadena

                                // Verificar si se ha introducido 'c' para cancelar
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break; // Volver al menú
                                }

                                // Intentar convertir la entrada a un número
                                try {
                                    int numRegistro = Integer.parseInt(input); // Convertir a número entero
                                    Map<String, String> rowMap = binaryRecord.selectRowMap(numRegistro);
                                    System.out.println("Row map: " + rowMap);
                                    break; // Salir del bucle después de mostrar el mapa
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid record number or 'c' to cancel.");
                                }
                            }
                        }
                        case 8 -> {
                            while (true) {
                                System.out.print("\nEnter the position of the record to update (c to cancel): ");

                                String input = scanner.nextLine(); // Leer la entrada como cadena

                                // Verificar si se ha introducido 'c' para cancelar
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break; // Volver al menú
                                }

                                // Intentar convertir la entrada a un número
                                try {
                                    int row = Integer.parseInt(input); // Convertir a número entero

                                    // Leer el registro en la posición especificada
                                    Map<String, String> existingRecord = binaryRecord.read(row);

                                    // Verificar si el registro existe
                                    if (existingRecord.isEmpty()) {
                                        System.out.println("No record found at the specified position.");
                                        continue; // Volver a pedir la posición
                                    }

                                    // Mostrar los campos disponibles
                                    System.out.println("\nAvailable fields: " + existingRecord.keySet());

                                    // Preguntar por el nombre del campo a actualizar
                                    System.out.print("Enter the field name to update: ");
                                    String fieldName = scanner.nextLine();

                                    // Verificar si el campo existe en el registro (ignorando mayúsculas y minúsculas)
                                    boolean fieldExists = existingRecord.keySet().stream()
                                        .anyMatch(key -> key.equalsIgnoreCase(fieldName));

                                    if (!fieldExists) {
                                        System.out.println("Field '" + fieldName + "' does not exist in the record. Please try again.");
                                        continue; // Volver a pedir el nombre del campo
                                    }

                                    // Pedir el nuevo valor
                                    System.out.print("Enter the new value: ");
                                    String newValue = scanner.nextLine();

                                    // Actualizar el registro utilizando la clave correcta (la que coincida insensiblemente)
                                    String actualFieldName = existingRecord.keySet().stream()
                                        .filter(key -> key.equalsIgnoreCase(fieldName))
                                        .findFirst()
                                        .orElse(null);

                                    if (actualFieldName != null) {
                                        // Actualizar el registro
                                        binaryRecord.update(row, actualFieldName, newValue);
                                        System.out.println("\nRecord updated.");
                                    } else {
                                        System.out.println("Failed to update the record due to field name mismatch.");
                                    }
                                    break; // Salir del bucle después de actualizar el registro
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid record position or 'c' to cancel.");
                                }
                            }
                        }
                        case 9 -> {
                            while (true) {
                                System.out.print("\nEnter the position of the record to delete (c to cancel): ");

                                String input = scanner.nextLine(); // Leer la entrada como cadena

                                // Verificar si se ha introducido 'c' para cancelar
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break; // Volver al menú
                                }

                                // Intentar convertir la entrada a un número
                                try {
                                    int row = Integer.parseInt(input); // Convertir a número entero
                                    binaryRecord.delete(row);
                                    System.out.println("Record deleted.");
                                    break; // Salir del bucle después de eliminar el registro
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid record position or 'c' to cancel.");
                                }
                            }
                        }

                        case 10 -> {
                            System.out.println("\nExiting...");
                            return;
                        }

                        default -> System.out.println("\nInvalid option.");
                    }
                }

            } catch (IOException e) {
                System.err.println("\nError: " + e.getMessage());
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("\nError: " + e.getMessage());
        }
    }
}
