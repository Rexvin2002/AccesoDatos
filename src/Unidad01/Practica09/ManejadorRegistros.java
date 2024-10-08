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
    private long numRegistros = ManejadorRegistros.registros.size(); 

    public ManejadorRegistros(String p, List<String> c, List<Integer> cl) throws IOException {
        this.campos = c;
        this.camposLength = cl;
        File fi = new File(p);
        if (!fi.exists()) {
            fi.createNewFile();
        }
        this.file = fi;
        
        this.registroLenght = 0;
        for (int i = 0; i < campos.size(); i++) {
            this.registroLenght += camposLength.get(i);
        }
        
        if (file.exists()) {
            this.numRegistros = file.length() / this.registroLenght; 
        }
    }

    public static List<ManejadorRegistros> getRegistros() {
        return ManejadorRegistros.registros;
    }

    public static void setRegistro(List<ManejadorRegistros> registro) {
        ManejadorRegistros.registros = registro;
    }
    
    public void insertarDatos(Map<String, String> record, long posicion) {
        
        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "rws")) {
            
            rndFile.seek(posicion * this.registroLenght);
            
            for (int i = 0; i < campos.size(); i++) {
                
                String nomCampo = campos.get(i);
                Integer campoLenght = camposLength.get(i);
                String valorCampo = record.getOrDefault(nomCampo, ""); 

                String formatoValorCampo = String.format("%1$-" + campoLenght + "s", valorCampo);
                rndFile.write(formatoValorCampo.getBytes("UTF-8"), 0, campoLenght);
            }
            System.out.println();
            
        } catch (Exception ex) {
            System.err.println("\nError: " + ex.getMessage());
        }
        
    }

    public String leerDatosString(long posicion) {
        StringBuilder resultado = new StringBuilder(); 

        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "r")) {
            
            rndFile.seek(posicion * this.registroLenght); 
            System.out.println("REGISTRO "+posicion);
            
            for (int i = 0; i < this.campos.size(); i++) {
                
                Integer campoLength = this.camposLength.get(i); 
                byte[] buffer = new byte[campoLength]; 
                rndFile.read(buffer, 0, campoLength); 
                String valorCampo = new String(buffer, "UTF-8").trim(); 

                resultado.append(valorCampo);

                System.out.println(campos.get(i) + ": " + valorCampo);

                if (i < this.campos.size() - 1) {
                    resultado.append(" | ");
                }
            }
            System.out.println(); 
        } catch (Exception ex) {
            System.err.println("\nError: " + ex.getMessage());
        }

        return resultado.toString(); 
    }

    public void modificar(Map<String, String> registro, long posicion) {
        insertarDatos(registro, posicion); 
    }
    
    public String seleccionarCampo(int numRegistro, String nomCampo) {
        if (numRegistro >= this.numRegistros) {
            return "Error: El número de registro excede la cantidad de registros existentes.\n";
        }

        Map<String, String> datosRegistro = this.selectRowMap(numRegistro);

        if (datosRegistro.containsKey(nomCampo)) {
            return nomCampo + ": " + datosRegistro.get(nomCampo);
        } else {
            return "Error: El campo '" + nomCampo + "' no existe en el registro.\n";
        }
    }

    public List<String> seleccionarColumnas(String nomCampo) throws IOException {
        List<String> valores = new ArrayList<>();
        
        for (int i = 0; i < this.numRegistros; i++) {
            String valorCampo = seleccionarCampo(i, nomCampo);
            valores.add(valorCampo);
        }

        return valores;
    }

    public List<String> selectRowList(int numRegistro) {
        Map<String, String> registro = selectRowMap(numRegistro);
        List<String> datos = new ArrayList<>(); 

        if (registro != null) {
            for (String dato : registro.values()) {
                datos.add(dato);
            }
        }

        return datos; 
    }
    
    public Map<String, String> selectRowMap(long posicion) {
        Map<String, String> datos = new HashMap<>();

        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "r")) {
            rndFile.seek(posicion * this.registroLenght); 

            for (int i = 0; i < this.campos.size(); i++) {
                Integer campoLength = this.camposLength.get(i); 
                byte[] buffer = new byte[campoLength]; 
                rndFile.read(buffer, 0, campoLength); 
                String valorCampo = new String(buffer, "UTF-8").trim(); 

                datos.put(this.campos.get(i), valorCampo); 
            }
        } catch (Exception ex) {
            System.err.println("\nError: " + ex.getMessage());
        }

        return datos;
    }
    
    public void actualizar(long registro, Map<String, String> actualizaciones) {
        
        if (registro < 0 || registro >= this.numRegistros) {
            System.err.println("Error: La fila especificada está fuera de rango.");
            return;
        }

        try {
            Map<String, String> existingRecord = selectRowMap(registro);

            for (String campo : this.campos) {
                if (actualizaciones.containsKey(campo)) {
                    existingRecord.put(campo, actualizaciones.get(campo));
                }
            }

            insertarDatos(existingRecord, registro);

            System.out.println("Registro en la fila " + registro + " actualizado correctamente.");

        } catch (Exception e) {
            System.err.println("Error al actualizar el registro: " + e.getMessage());
        }
    }
    
    public void actualizar(int registro, String campo, String valor) {
        
        if (registro < 0 || registro >= this.numRegistros) {
            System.err.println("Error: La fila especificada está fuera de rango.");
            return;
        }

        if (!this.campos.contains(campo)) {
            System.err.println("Error: El campo '" + campo + "' no existe en el registro.");
            return;
        }

        try {
            Map<String, String> existingRecord = selectRowMap(registro);

            existingRecord.put(campo, valor);

            insertarDatos(existingRecord, registro);

            System.out.println("Registro en la fila " + registro + " actualizado correctamente. Campo: " + campo + " -> " + valor);

        } catch (Exception e) {
            System.err.println("Error al actualizar el registro: " + e.getMessage());
        }
    }
    
    public void delete(int row) {
        if (row < 0 || row >= this.numRegistros) {
            System.err.println("Error: La fila especificada está fuera de rango.");
            return;
        }

        try (RandomAccessFile rndFile = new RandomAccessFile(this.file, "rws")) {
            rndFile.seek(row * this.registroLenght);

            for (int i = 0; i < this.campos.size(); i++) {
                Integer campoLength = this.camposLength.get(i);
                String formatoValorCampo = String.format("%1$-" + campoLength + "s", "");
                rndFile.write(formatoValorCampo.getBytes("UTF-8"), 0, campoLength);
            }

            System.out.println("Registro en la fila " + row + " limpiado correctamente.");
        } catch (Exception e) {
            System.err.println("Error al limpiar el registro: " + e.getMessage());
        }
    }



}
