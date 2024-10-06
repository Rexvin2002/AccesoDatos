package Unidad01.Practica08;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
    private final long numRows;      // Número de filas
    
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

        // Establece el número de filas
        this.numRows = this.bytesPerLine > 0 ? this.file.length() / this.bytesPerLine : 0;
    }

    // Método para obtener el número de filas
    public long getNumRows() {
        return numRows;
    }

    // Método para insertar registros en el archivo
    public void insertRecord(Map<String, String> data, long position) {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "rws")) {
            System.out.println("INSERTANDO REGISTRO EN POSICIÓN: " + position);

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

    // Método main para probar la clase
    public static void main(String[] args) {
        List<String> fields = List.of("DNI", "NOMBRE", "DIRECCION", "CP");
        List<Integer> fieldLengths = List.of(9, 32, 32, 5);

        try {
            RegistroBinario faa = new RegistroBinario("src/Unidad01/Practica08/archivo_binario.dat", fields, fieldLengths);
            Map<String, String> data = new HashMap<>();

            // Primer registro
            data.put("DNI", "11111111A");
            data.put("NOMBRE", "Nombre y Apellidos 1");
            data.put("DIRECCION", "Calle Principal Nº 7, Planta4, Letra J");
            data.put("CP", "543210");
            faa.insertRecord(data, 0);
            data.clear();

            // Segundo registro
            data.put("DNI", "22222222B");
            data.put("NOMBRE", "Nombre2");
            data.put("DIRECCION", "Calle Principal Nº 7, Planta4, Letra J");
            data.put("CP", "123456");
            faa.insertRecord(data, 1);
            data.clear();

            // Tercer registro
            data.put("DNI", "33333333B");
            data.put("NOMBRE", "Nombre3");
            data.put("DIRECCION", "Dirección");
            data.put("CP", "56789");
            faa.insertRecord(data, 2);
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}
