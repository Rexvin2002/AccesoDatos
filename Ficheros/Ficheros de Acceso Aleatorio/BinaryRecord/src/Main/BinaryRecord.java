package Main;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.*;
import java.util.*;

/**
 * The BinaryRecord class handles operations for reading and writing records in
 * a binary file. Each record consists of multiple fields of specified lengths.
 */
public class BinaryRecord {

    private final File file;                      // The binary file to store records.
    private final List<String> fields;            // List of field names.
    private final List<Integer> fieldLengths;     // Corresponding lengths of each field.
    private long recordLength;                     // Total length of a single record.
    private long recordCount = 0;                 // Total number of records in the file.

    /**
     * Constructor to initialize the BinaryRecord with a file path, field names,
     * and field lengths.
     *
     * @param path The path of the binary file.
     * @param fields The list of field names.
     * @param fieldLengths The list of corresponding field lengths.
     * @throws IOException If there is an error creating the file or accessing
     * it.
     */
    public BinaryRecord(String path, List<String> fields, List<Integer> fieldLengths) throws IOException {
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

    /**
     * Inserts a new record at the specified position in the binary file.
     *
     * @param record A map containing the field names and their corresponding
     * values to be written.
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
     * @return A map containing the field names and their corresponding values
     * read from the file.
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
     * Modifies an existing record by inserting a new record at the specified
     * position.
     *
     * @param record A map containing the field names and their corresponding
     * values to be written.
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
                BinaryRecord binaryRecord = new BinaryRecord("src\\BinaryFiles\\binary_file_1.dat", fields, fieldLengths);

                Map<String, String> record = new HashMap<>();

                // First record
                record.put("ID", "11111111A");
                record.put("NAME", "Nombre y Apellidos 1");
                record.put("DIRECTION", "Calle Principal Nº 7, Planta4, Letra J");
                record.put("ZC", "54321");
                binaryRecord.insert(record, 0);
                record.clear();

                // Second record
                record.put("ID", "22222222B");
                record.put("NAME", "Nombre2");
                record.put("DIRECTION", "Calle Principal Nº 7, Planta4, Letra J");
                record.put("ZC", "12345");
                binaryRecord.insert(record, 1);
                record.clear();

                // Third record
                record.put("ID", "33333333C");
                record.put("NAME", "Nombre3");
                record.put("DIRECTION", "Direccion 3");
                record.put("ZC", "56789");
                binaryRecord.insert(record, 2);
                record.clear();

                while (true) {
                    System.out.println("\nMenu:");
                    System.out.println("1. Insert record");
                    System.out.println("2. Read record");
                    System.out.println("3. Modify record");
                    System.out.println("4. Exit");
                    System.out.print("\nChoose an option: ");

                    int option = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (option) {
                        case 1 -> {
                            System.out.print("\nEnter the position to insert: ");
                            long positionToInsert = scanner.nextLong();
                            scanner.nextLine(); // Consume newline

                            // Check if a record already exists at the specified position
                            Map<String, String> existingRecord = binaryRecord.read(positionToInsert);
                            boolean isEmpty = existingRecord.values().stream().allMatch(String::isEmpty);

                            if (!isEmpty) {
                                System.out.println("\nA record already exists at position " + positionToInsert + ":");
                                existingRecord.forEach((field, value) -> System.out.println(field + ": " + value));
                            } else {
                                System.out.println("\nNo record exists at position " + positionToInsert + ", a new one will be created.");
                            }

                            // Request data for the new record
                            Map<String, String> newRecord = new HashMap<>();
                            for (String field : fields) {
                                System.out.print("Enter " + field + ": ");
                                String value = scanner.nextLine();
                                newRecord.put(field, value);
                            }

                            // Insert the record at the specified position (overwriting if it exists)
                            binaryRecord.insert(newRecord, positionToInsert);
                            System.out.println("\nRecord inserted at position " + positionToInsert + ".");
                        }

                        case 2 -> {
                            System.out.print("\nEnter the position of the record to read: ");
                            long positionToRead = scanner.nextLong();
                            scanner.nextLine();
                            Map<String, String> readRecord = binaryRecord.read(positionToRead);
                            if (!readRecord.isEmpty()) {
                                System.out.println("\nRecord read:");
                                readRecord.forEach((field, value) -> System.out.println(field + ": " + value));
                            } else {
                                System.out.println("\nNo record found at the specified position.");
                            }
                        }

                        case 3 -> {
                            System.out.print("\nEnter the position of the record to modify: ");
                            long positionToModify = scanner.nextLong();
                            scanner.nextLine();
                            Map<String, String> modifiedRecord = new HashMap<>();
                            System.out.println();
                            for (String field : fields) {
                                System.out.print("Enter " + field + ": ");
                                String value = scanner.nextLine();
                                modifiedRecord.put(field, value);
                            }
                            binaryRecord.modify(modifiedRecord, positionToModify);
                        }

                        case 4 -> {
                            System.out.println("\nExiting...");
                            return;
                        }

                        default ->
                            System.out.println("\nInvalid option.");
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
