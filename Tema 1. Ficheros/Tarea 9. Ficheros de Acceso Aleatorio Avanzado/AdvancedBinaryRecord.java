package Unidad01.Practica09;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

import java.io.*;
import java.util.*;

/**
 * The AdvancedBinaryRecord class handles operations for reading and writing records in a binary file.
 * Each record consists of multiple fields of specified lengths.
 */
public class AdvancedBinaryRecord {

    private final File file;                      // The binary file to store records.
    private final List<String> fields;            // List of field names.
    private final List<Integer> fieldLengths;     // Corresponding lengths of each field.
    private long recordLength;                     // Total length of a single record.
    private long recordCount = 0;                 // Total number of records in the file.

    /**
     * Constructor to initialize the AdvancedBinaryRecord with a file path, field names, and field lengths.
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
            file.createNewFile(); // Create a new file if it doesn't exist.
        }
        
        this.recordLength = 0;
        for (int i = 0; i < fields.size(); i++) {
            this.recordLength += fieldLengths.get(i); // Calculate total record length.
        }

        if (file.exists()) {
            this.recordCount = file.length() / this.recordLength; // Calculate the number of records.
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

    /**
     * Inserts a new record at the specified position in the binary file.
     * 
     * @param record A map containing the field names and their corresponding values to be written.
     * @param position The position at which to insert the record.
     */
    public void insert(Map<String, String> record, long position) {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "rws")) {
            rndFile.seek(position * this.recordLength);  // Move to the specified position.
            int totalFields = fields.size();
            for (int i = 0; i < totalFields; i++) {
                String fieldName = fields.get(i);
                Integer fieldLength = fieldLengths.get(i);
                String fieldValue = record.getOrDefault(fieldName, ""); // Get the value for the field, default to empty string if not found.

                // Format the field value to fit the specified length.
                String formattedFieldValue = String.format("%1$-" + fieldLength + "s", fieldValue);
                rndFile.write(formattedFieldValue.getBytes("UTF-8"), 0, fieldLength); // Write the formatted field value to the file.
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
            rndFile.seek(position * this.recordLength); // Move to the specified position.
            for (int i = 0; i < fields.size(); i++) {
                Integer fieldLength = fieldLengths.get(i);
                byte[] buffer = new byte[fieldLength];
                rndFile.read(buffer, 0, fieldLength); 
                String fieldValue = new String(buffer, "UTF-8").trim(); // Convert bytes to string and trim whitespace.
                record.put(fields.get(i), fieldValue); // Store field name and value in the map.
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
    }
    
    /**
     * Retrieves a value for a specified column from a specific record.
     *
     * @param recordNum The record number to read.
     * @param columnName The name of the column to retrieve.
     * @return The value for the specified column, or null if not found.
     */
    public String selectField(int recordNum, String columnName) {
        Map<String, String> record = read(recordNum);
        return record.getOrDefault(columnName, null);
    }

    /**
     * Retrieves all values for a specified column across all records.
     *
     * @param columnName The name of the column to retrieve values from.
     * @return A list of values for the specified column.
     */
    public List<String> selectColumn(String columnName) {
        List<String> values = new ArrayList<>(); 
        int colIndex = fields.indexOf(columnName); // Get the index of the column.
        if (colIndex == -1) {
            return values; // Return empty list if column doesn't exist.
        }

        for (int i = 0; i < recordCount; i++) {
            Map<String, String> record = read(i); // Read each record.
            values.add(record.get(columnName)); // Add the value for the column to the list.
        }
        return values; // Return the list of values.
    }

    /**
     * Retrieves all values for a specified column across all records.
     *
     * @param columnName The name of the column to retrieve values from.
     * @return A list of all values found for the specified column.
     */
    public List<String> selectAllValuesForColumn(String columnName) {
        List<String> values = new ArrayList<>(); 
        int totalRecords = getTotalRecords(); 

        for (int i = 0; i < totalRecords; i++) {
            Map<String, String> record = read(i); // Read each record.

            if (record != null && record.containsKey(columnName)) {
                String value = record.get(columnName); // Get the value of the specified column.
                values.add(value); // Add the value to the list.
            }
        }
        return values; // Return the list of all found values.
    }

    /**
     * Returns the total number of records in the binary file.
     *
     * @return The total number of records.
     */
    public int getTotalRecords() {
        return (int) recordCount; 
    }

    /**
     * Retrieves all values from a specific record as a list.
     *
     * @param recordNum The record number to read.
     * @return A list of values from the specified record.
     */
    public List<String> selectRowList(int recordNum) {
        Map<String, String> record = read(recordNum); // Read the record at the specified position.
        List<String> values = new ArrayList<>(); // List to hold the values of the record.

        if (record != null) {
            // Add all values of the record to the list.
            for (String value : record.values()) {
                values.add(value);
            }
        }

        return values; // Return the list of values.
    }

    /**
     * Retrieves a record as a map for a specified record number.
     *
     * @param recordNum The record number to read.
     * @return A map containing the field names and their corresponding values for the record.
     */
    public Map<String, String> selectRowMap(int recordNum) {
        Map<String, String> record = read(recordNum); // Read the record.

        if (record != null) {
            return record; 
        }

        return new HashMap<>(); // Return an empty map if no record.
    }

    /**
     * Updates an existing record using a map of field updates.
     *
     * @param row The record number to update.
     * @param updates A map containing the field names and their new values.
     */
    public void update(int row, Map<String, String> updates) {
        Map<String, String> existingRecord = read(row); // Read the existing record.
        for (String field : updates.keySet()) {
            existingRecord.put(field, updates.get(field)); // Update the fields in the existing record.
        }
        insert(existingRecord, row); // Insert the updated record back.
    }

    /**
     * Updates a specific field in an existing record.
     *
     * @param row The record number to update.
     * @param field The name of the field to update.
     * @param value The new value for the specified field.
     */
    public void update(int row, String field, String value) {
        Map<String, String> existingRecord = read(row); // Read the existing record.
        existingRecord.put(field, value); // Update the specified field.
        insert(existingRecord, row); // Insert the updated record back.
    }

    /**
     * Deletes a record at the specified position in the binary file.
     *
     * @param row The record number to delete.
     */
    public void delete(int row) {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "rw")) {
            rndFile.seek(row * this.recordLength); // Move to the specified position.
            byte[] emptyBytes = new byte[(int) this.recordLength]; // Create empty bytes to clear the record.
            rndFile.write(emptyBytes); // Write empty bytes to "clear" the record.
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
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

                    // Handle the user's chosen option
                    switch (option) {
                        // Insert a new record
                        case 1 -> {
                            boolean validPosition = false;                  // Flag to indicate if the input position is valid.
                            long positionToInsert = -1;                     // Initial value for the position to insert.

                            while (!validPosition) {
                                System.out.print("\nEnter the position to insert (c to cancel): "); // Prompt user for the position to insert.

                                // Check if 'c' has been entered to cancel.
                                String input = scanner.nextLine();          // Read user input.

                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");     // Inform the user that the operation is being cancelled.
                                    break;                                   // Exit the loop and return to the menu.
                                }

                                // Attempt to convert the input to a number.
                                try {
                                    positionToInsert = Long.parseLong(input); // Parse the input as a long number.

                                    // Check if a record exists at the specified position.
                                    Map<String, String> existingRecord = binaryRecord.read(positionToInsert); // Read the existing record.
                                    boolean isEmpty = existingRecord.values().stream().allMatch(String::isEmpty); // Check if all values are empty.

                                    if (!isEmpty) {
                                        // Inform the user that a record already exists at the specified position.
                                        System.out.println("\nA record already exists at position " + positionToInsert + ":");
                                        existingRecord.forEach((field, value) -> System.out.println(field + ": " + value)); // Display existing record.

                                        // Ask if the user wants to overwrite the existing record.
                                        System.out.print("\nDo you want to overwrite it? (y/n): ");
                                        String confirm = scanner.nextLine(); // Read confirmation input.

                                        if (confirm.equalsIgnoreCase("n")) {
                                            System.out.println("Insertion canceled."); // Inform the user that insertion is cancelled.
                                            continue; // Prompt the user again for the position.
                                        } else if (confirm.equalsIgnoreCase("y")) {
                                            System.out.println("\nOverwriting the existing record at position " + positionToInsert + "."); // Inform about overwriting.
                                        } else {
                                            System.out.println("Invalid input. Please enter 'y' or 'n'."); // Handle invalid input.
                                            continue; // Prompt the user again for the position.
                                        }
                                    } else {
                                        System.out.println("\nNo record exists at position " + positionToInsert + ", a new one will be created."); // Inform about creating a new record.
                                    }

                                    validPosition = true; // The position is valid.

                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid position or 'c' to cancel."); // Handle invalid input.
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
                        // Read a record
                        case 2 -> {
                            boolean validPosition = false; // Variable to control the validity of the position.
                            long positionToRead = -1;      // Initial value for the position.

                            while (!validPosition) {
                                System.out.print("\nEnter the position of the record to read (c to cancel): "); // Prompt user for the position to read.

                                // Read the input as a string.
                                String input = scanner.nextLine(); // Capture user input.

                                // Check if 'c' has been entered to cancel.
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling..."); // Inform the user that the operation is being cancelled.
                                    break; // Exit the loop and return to the menu.
                                }

                                // Attempt to convert the input to a number.
                                try {
                                    positionToRead = Long.parseLong(input); // Parse the input as a long number.

                                    // Read the record at the specified position.
                                    Map<String, String> readRecord = binaryRecord.read(positionToRead); // Retrieve the record.

                                    // Check if the record has non-empty values.
                                    boolean isEmpty = readRecord.values().stream().allMatch(String::isEmpty); // Verify if all values are empty.

                                    if (!isEmpty) {
                                        // Display the read record.
                                        System.out.println("\nRecord read:");
                                        readRecord.forEach((field, value) -> System.out.println(field + ": " + value)); // Print each field and value.
                                    } else {
                                        System.out.println("\nThe record at the specified position is empty. Please try again."); // Inform user the record is empty.
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle invalid input.
                                    System.out.println("Invalid input. Please enter a valid position or 'c' to cancel."); // Prompt for valid input.
                                }
                            }

                        }
                        // Modify a record
                        case 3 -> {
                            boolean validPosition = false; // Variable to control the validity of the position.
                            long positionToModify = -1;    // Initial value for the position.

                            while (!validPosition) {
                                System.out.print("\nEnter the position of the record to modify (c to cancel): "); // Prompt user for the position to modify.
                                String input = scanner.nextLine().trim(); // Read the input and trim whitespace.

                                // Check if 'c' has been entered to cancel.
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling..."); // Inform the user that the operation is being cancelled.
                                    break; // Exit the loop and return to the menu.
                                }

                                // Verify if a number has been entered.
                                try {
                                    positionToModify = Long.parseLong(input); // Parse the input as a long number.

                                    // Read the record at the specified position.
                                    Map<String, String> existingRecord = binaryRecord.read(positionToModify);

                                    if (!existingRecord.isEmpty()) { // Check if the record is not empty.
                                        // Display the current record at the specified position.
                                        System.out.println("\nCurrent record at position " + positionToModify + ":");
                                        existingRecord.forEach((field, value) -> System.out.println(field + ": " + value));

                                        // Create a new record to modify.
                                        Map<String, String> modifiedRecord = new HashMap<>();
                                        System.out.println();

                                        // Loop through all fields to get new values from the user.
                                        for (String field : fields) {
                                            System.out.print("Enter new value for " + field + " (leave blank to keep current value): ");
                                            String value = scanner.nextLine().trim(); // Read and trim the input.

                                            // Only modify if a new value is provided.
                                            if (!value.isEmpty()) {
                                                modifiedRecord.put(field, value); // Add new value to modified record.
                                            } else {
                                                modifiedRecord.put(field, existingRecord.get(field)); // Keep the current value if no new value is provided.
                                            }
                                        }

                                        // Modify the existing record with the new values.
                                        binaryRecord.modify(modifiedRecord, positionToModify);
                                        System.out.println("\nRecord at position " + positionToModify + " modified successfully."); // Confirmation message.
                                    } else {
                                        System.out.println("\nNo record found at the specified position. Please try again."); // Inform user no record found.
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle invalid input.
                                    System.out.println("Invalid input. Please enter a valid position or 'c' to cancel."); // Prompt for valid input.
                                }
                            }
                        }
                        // Select a field
                        case 4 -> {
                            boolean validInput = false;
                            while (!validInput) {
                                System.out.print("\nEnter the record number (or 'c' to cancel): ");
                                String input = scanner.nextLine().trim();

                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break; // Exit the main loop
                                }

                                try {
                                    int recordNumber = Integer.parseInt(input);

                                    if (recordNumber < 0) {
                                        System.out.println("Error: The record number must be a non-negative integer.");
                                    } else {
                                        // Read the record to verify if it exists
                                        Map<String, String> record2 = binaryRecord.read(recordNumber);
                                        if (record2 == null || record2.isEmpty()) {
                                            System.out.println("Error: No record found for record number " + recordNumber + ".");
                                        } else {
                                            // Display the number of available columns
                                            System.out.println("\nAvailable columns (" + record2.size() + " total): " + record2.keySet());

                                            boolean validColumnInput = false; // Variable to validate column input
                                            while (!validColumnInput) {
                                                // Request the column name
                                                System.out.print("Enter the column name (or 'c' to cancel): ");
                                                String columnName = scanner.nextLine().trim();

                                                // Handle the input to exit
                                                if (columnName.equalsIgnoreCase("c")) {
                                                    System.out.println("Cancelling column selection...");
                                                    validInput = false; // Go back to ask for the record number
                                                    break; // Exit the column loop
                                                }

                                                // Convert the column name to lowercase for comparison
                                                String normalizedColumnName = columnName.toLowerCase();

                                                // Check if the field exists in the record (ignoring case)
                                                String actualFieldName = record2.keySet().stream()
                                                    .filter(key -> key.toLowerCase().equals(normalizedColumnName))
                                                    .findFirst()
                                                    .orElse(null);

                                                // Use the selectField method to get the value of the field if it exists
                                                if (actualFieldName != null) {
                                                    String value = binaryRecord.selectField(recordNumber, actualFieldName);
                                                    System.out.println("\nValue for column '" + actualFieldName + "' in record " + recordNumber + ": " + value);
                                                    validColumnInput = true; // End the loop if a valid value is obtained
                                                } else {
                                                    System.out.println("Error: No column found with the name '" + columnName + "'. Please try again.\n");
                                                }
                                            }
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Please enter a valid number for the record.");
                                }
                            }

                        }
                        // Select a column
                        case 5 -> {
                            boolean validInput = false; // Variable to control the validity of the input

                            while (!validInput) {
                                System.out.print("\nEnter the record number (or 'c' to cancel): ");
                                String input = scanner.nextLine().trim(); // Read the input as String and remove whitespace

                                // Handle the input to exit
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break; // Exit the loop
                                }

                                // Try to convert the input to an integer
                                int recordNumber;
                                try {
                                    recordNumber = Integer.parseInt(input); // Attempt to convert to integer
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Please enter a valid record number.");
                                    continue; // Show the menu again
                                }

                                // Verify that the record number is valid
                                if (recordNumber < 0) {
                                    System.out.println("Error: The record number must be a non-negative integer.");
                                    continue; // Show the menu again
                                }

                                // If we reach here, we have a valid record number
                                validInput = true; // Mark as valid input

                                // Read the record to get the available columns
                                Map<String, String> record2 = binaryRecord.read(recordNumber);
                                if (record2 == null || record2.isEmpty()) {
                                    System.out.println("Error: No record found for record number " + recordNumber + ".");
                                    validInput = false; // Mark as invalid input
                                    continue; // Show the menu again
                                }

                                // Show the available columns
                                System.out.println("\nAvailable columns: " + record2.keySet());

                                // Now ask for the column name
                                while (true) {
                                    System.out.print("Enter the column name (or 'c' to cancel): ");
                                    String columnName = scanner.nextLine().trim(); // Read the column input and remove whitespace

                                    // Handle the input to exit
                                    if (columnName.equalsIgnoreCase("c")) {
                                        System.out.println("Cancelling...");
                                        validInput = false; // Make the outer loop ask again
                                        break;
                                    }

                                    // Convert the column name to lowercase for comparison
                                    String normalizedColumnName = columnName.toLowerCase();

                                    // Check if the field exists in the record (ignoring case)
                                    String actualFieldName = record2.keySet().stream()
                                        .filter(key -> key.toLowerCase().equals(normalizedColumnName))
                                        .findFirst()
                                        .orElse(null);

                                    // Get the values for the specified column
                                    if (actualFieldName != null) {
                                        List<String> values = binaryRecord.selectAllValuesForColumn(actualFieldName); // Change to your corresponding method

                                        // Check if values were obtained
                                        if (values != null && !values.isEmpty()) {
                                            System.out.println("\nValues for column '" + actualFieldName + "': " + values+"\n");
                                        } else {
                                            System.out.println("Error: No values found for column '" + actualFieldName + "'. Please enter a valid column name.\n");
                                        }
                                    } else {
                                        System.out.println("Error: No column found with the name '" + columnName + "'. Please try again.\n");
                                    }
                                }
                            }


                        }
                        // Select a row as a list
                        case 6 -> {
                            while (true) {
                                System.out.print("\nEnter the record number to select as a list (or 'c' to cancel): ");
                                String input = scanner.nextLine().trim(); // Read the input as String and remove whitespace

                                // Handle the input to exit
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break;
                                }

                                try {
                                    int recordNumber = Integer.parseInt(input); // Attempt to convert to integer

                                    // Get the row values as a list
                                    List<String> rowValues = binaryRecord.selectRowList(recordNumber);

                                    // Verify if values were obtained
                                    if (rowValues != null && !rowValues.isEmpty()) {
                                        System.out.println("\nRow values: " + rowValues);
                                    } else {
                                        System.out.println("No values found for record number " + recordNumber + ".");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: Please enter a valid integer for the record number.\n");
                                }
                            }
                        }
                        // Select a row as a map
                        case 7 -> {
                            while (true) {
                                System.out.print("\nEnter the record number to select as a map (or 'c' to cancel): ");

                                String input = scanner.nextLine(); // Read the input as a string

                                // Check if 'c' was entered to cancel
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break; // Return to the menu
                                }

                                // Attempt to convert the input to a number
                                try {
                                    int recordNumber = Integer.parseInt(input); // Convert to an integer
                                    Map<String, String> rowMap = binaryRecord.selectRowMap(recordNumber); // Get the map of the record

                                    if (rowMap != null && !rowMap.isEmpty()) { // Check if the map is not null and not empty
                                        System.out.println("\nRow map: " + rowMap);
                                    } else {
                                        System.out.println("No record found for number " + recordNumber + "."); // Message if the record is not found
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid record number or 'c' to cancel.");
                                }
                            }
                        }
                        // Update a record
                        case 8 -> {
                            while (true) {
                                System.out.print("\nEnter the position of the record to update (c to cancel): ");

                                String input = scanner.nextLine(); // Read the input as a string

                                // Check if 'c' was entered to cancel
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break; // Return to the menu
                                }

                                // Attempt to convert the input to a number
                                try {
                                    int row = Integer.parseInt(input); // Convert to an integer

                                    // Read the record at the specified position
                                    Map<String, String> existingRecord = binaryRecord.read(row);

                                    // Check if the record exists
                                    if (existingRecord.isEmpty()) {
                                        System.out.println("No record found at the specified position.");
                                        continue; // Ask for the position again
                                    }

                                    // Display current fields and values
                                    System.out.println("\nCurrent record values: " + existingRecord);

                                    // Ask if the user wants to update all fields or a specific one
                                    System.out.print("Do you want to update all fields? (y/n): ");
                                    String updateChoice = scanner.nextLine().trim().toLowerCase();

                                    if (updateChoice.equals("y")) {
                                        // Update all fields
                                        for (String key : existingRecord.keySet()) {
                                            System.out.print("Enter new value for field '" + key + "' (current value: " + existingRecord.get(key) + "): ");
                                            String newValue = scanner.nextLine().trim();
                                            existingRecord.put(key, newValue); // Update the value in the map
                                        }
                                        // Save the updated record to the file
                                        binaryRecord.update(row, existingRecord); // Assuming this method handles the full map
                                        System.out.println("\nAll fields updated successfully.");
                                    } else {
                                        // Procedure to update a specific field
                                        System.out.println("\nAvailable fields: " + existingRecord.keySet());
                                        System.out.print("Enter the field name to update: ");
                                        String fieldName = scanner.nextLine().trim();

                                        // Check if the field exists in the record (case insensitive)
                                        boolean fieldExists = existingRecord.keySet().stream()
                                            .anyMatch(key -> key.equalsIgnoreCase(fieldName));

                                        if (!fieldExists) {
                                            System.out.println("Field '" + fieldName + "' does not exist in the record. Please try again.");
                                            continue; // Ask for the field name again
                                        }

                                        // Ask for the new value
                                        System.out.print("Enter the new value: ");
                                        String newValue = scanner.nextLine().trim();

                                        // Update the record using the correct key (the one that matches case insensitively)
                                        String actualFieldName = existingRecord.keySet().stream()
                                            .filter(key -> key.equalsIgnoreCase(fieldName))
                                            .findFirst()
                                            .orElse(null);

                                        if (actualFieldName != null) {
                                            // Update only the specific field
                                            existingRecord.put(actualFieldName, newValue); // Update in the map
                                            binaryRecord.update(row, existingRecord); // Save the updated record to the file
                                            System.out.println("\nField '" + actualFieldName + "' updated successfully.");
                                        } else {
                                            System.out.println("Failed to update the record due to field name mismatch.");
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid record position or 'c' to cancel.");
                                }
                            }


                        }
                        // Delete a record
                        case 9 -> {
                            while (true) {
                                System.out.print("\nEnter the position of the record to delete (c to cancel): ");

                                String input = scanner.nextLine(); // Read the input as a string

                                // Check if 'c' was entered to cancel
                                if (input.equalsIgnoreCase("c")) {
                                    System.out.println("Cancelling...");
                                    break; // Return to the menu
                                }

                                // Attempt to convert the input to a number
                                try {
                                    int row = Integer.parseInt(input); // Convert to an integer
                                    binaryRecord.delete(row); // Delete the record
                                    System.out.println("Record deleted.");
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid record position or 'c' to cancel.");
                                }
                            }
                        }
                        // Exit the program
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
