package Unidad01.Practica09;

import java.io.*;
import java.util.*;

public class AdvancedBinaryRecord2 {
    
    public Record records;

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));

            // Definir los campos y las longitudes de los campos
            List<String> campos = Arrays.asList("ID", "NAME", "DIRECTION", "ZC");
            List<Integer> camposLength = Arrays.asList(9, 32, 32, 5);
            
            // Crear una instancia de AdvancedBinaryRecord2
            ManejadorRegistros recordManager = new ManejadorRegistros("src/Unidad01/Practica09/archivo_binario_2.dat", campos, camposLength);

            // Crear un nuevo registro como un Map
            Map<String, String> data = new HashMap<>();
            data.put("ID", "22222222B");
            data.put("NAME", "Nombre2");
            data.put("DIRECTION", "Calle Principal Nº 7, Planta4");
            data.put("ZC", "12345");
            
            recordManager.insertarDatos(data, 0);
            
            System.out.println(recordManager.leerDatos(0));
            
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + e.getMessage());
        }
    } 
    
}