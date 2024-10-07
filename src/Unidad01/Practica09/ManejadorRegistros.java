package Unidad01.Practica09;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManejadorRegistros {
    
    private static List<ManejadorRegistros> registros = new ArrayList<>();
    private final List<String> campos;
    private final List<Integer> camposLength;
    private int registroLenght;
    private final File file;
    private long numRegistros = 0; 

    public ManejadorRegistros(String p, List<String> c, List<Integer> cl) throws IOException {
        this.campos = c;
        this.camposLength = cl;
        File fi = new File(p);
        if (!fi.exists()) {
            fi.createNewFile();
        }
        this.file = fi;
        
        // Suma el lenght de todos los campos para calcular los bytes por record
        this.registroLenght = 0;
        for (int i = 0; i < campos.size(); i++) {
            this.registroLenght += camposLength.get(i);
        }
        
        // Calculate the number of records.
        if (file.exists()) {
            this.numRegistros = file.length() / this.registroLenght; 
        }
    }
    
    public ManejadorRegistros(String p, List<String> c, List<Integer> cl, Map<String, String> d) throws IOException {
        this.campos = c;
        this.camposLength = cl;
        File fi = new File(p);
        if (!fi.exists()) {
            fi.createNewFile();
        }
        this.file = fi;
        
        // Suma el lenght de todos los campos para calcular los bytes por record
        this.registroLenght = 0;
        for (int i = 0; i < campos.size(); i++) {
            this.registroLenght += camposLength.get(i);
        }
        
        // Calculate the number of records.
        if (file.exists()) {
            this.numRegistros = file.length() / this.registroLenght; 
        }
        
        insertarDatos(d, numRegistros);
    }

    public static List<ManejadorRegistros> getRegistros() {
        return ManejadorRegistros.registros;
    }

    public static void setRegistro(List<ManejadorRegistros> registro) {
        ManejadorRegistros.registros = registro;
    }
    
    public void insertarDatos(Map<String, String> record, long position) {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "rws")) {
            rndFile.seek(position * this.registroLenght);
            
            for (int i = 0; i < campos.size(); i++) {
                String nomCampo = campos.get(i);
                Integer campoLenght = camposLength.get(i);
                String valorCampo = record.getOrDefault(nomCampo, ""); 

                String formatoValorCampo = String.format("%1$-" + campoLenght + "s", valorCampo);
                rndFile.write(formatoValorCampo.getBytes("UTF-8"), 0, campoLenght);
            }
            System.out.println("Se han insertado los datos exitosamente.");
        } catch (Exception ex) {
            System.err.println("\nError: " + ex.getMessage());
        }
    }

    public String leerDatosString(long posicion) {
        StringBuilder resultado = new StringBuilder(); // Utilizamos StringBuilder para concatenar los valores de los campos

        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "r")) {
            rndFile.seek(posicion * this.registroLenght); // Moverse a la posición del registro

            for (int i = 0; i < this.campos.size(); i++) {
                Integer campoLength = this.camposLength.get(i); // Longitud del campo
                byte[] buffer = new byte[campoLength]; // Buffer para almacenar los bytes del campo
                rndFile.read(buffer, 0, campoLength); // Leer bytes del campo
                String valorCampo = new String(buffer, "UTF-8").trim(); // Convertir a String y eliminar espacios

                resultado.append(valorCampo); // Añadir el valor del campo al StringBuilder

                if (i < this.campos.size() - 1) {
                    resultado.append(", "); // Añadir una coma y espacio después de cada campo, excepto el último
                }
            }
        } catch (Exception ex) {
            System.err.println("\nError: " + ex.getMessage());
        }

        return resultado.toString(); // Devolver la concatenación de todos los campos
    }
    public Map<String, String> leerDatosMap(long posicion) {
        Map<String, String> datos = new HashMap<>();

        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "r")) {
            rndFile.seek(posicion * this.registroLenght); // Moverse a la posición del registro

            for (int i = 0; i < this.campos.size(); i++) {
                Integer campoLength = this.camposLength.get(i); // Longitud del campo
                byte[] buffer = new byte[campoLength]; // Buffer para almacenar los bytes del campo
                rndFile.read(buffer, 0, campoLength); // Leer bytes del campo
                String valorCampo = new String(buffer, "UTF-8").trim(); // Convertir a String y eliminar espacios

                datos.put(this.campos.get(i), valorCampo); // Almacenar en el Map el campo y su valor
            }
        } catch (Exception ex) {
            System.err.println("\nError: " + ex.getMessage());
        }

        return datos; // Retornar el Map con los datos del registro
    }


}
