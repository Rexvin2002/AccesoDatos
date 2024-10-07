
package Unidad01.Practica09;

/**
 *
 * @author kgv17
 */
import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistroBinarioAvanzado {

    private final File f;
    private final List<String> campos;
    private final List<Integer> bytesCampos;
    private long bytesLinea;       // Bytes por linea
    private long numFilas = 0;    // Número de filas 
    
    public RegistroBinarioAvanzado(String path, List<String> campos, List<Integer> bytesCampos) throws IOException {
        this.campos = campos;
        this.bytesCampos = bytesCampos;
        this.f = new File(path);
        this.f.createNewFile();
        this.bytesLinea = 0;
        
        // Muestra los bytes por campo en la terminal
        System.out.println("\nBYTES POR CAMPO / COLUMNA");
        for (int i = 0; i < campos.size(); i++) {
            String campo = campos.get(i);
            int longitud = bytesCampos.get(i);

            System.out.print(campo + ": " + longitud + "\n");
            this.bytesLinea += longitud; // Incrementa bytesLinea según la longitud de cada campo
        }
        System.out.println("\n");

        // Establece el número de filas dividiendo el length total del archivo entre el número de bytes por línea
        if (f.exists() && bytesLinea > 0) {
            this.numFilas = (int) (f.length() / this.bytesLinea);
        }
    }

    public long numFilas(){ return numFilas; }
    
    int r = 1;
    
    public void insertar( Map<String,String> datos, long pos ){
        // ABRIR ARCHIVO BINARIO
        try( RandomAccessFile rndFile = new RandomAccessFile( this.f, "rws" ) ) {
            System.out.println("REGISTRO: "+ r++);
            // POSICIONARNOS PARA ESCRIBIR
            rndFile.seek( pos * this.bytesLinea );
            
            
            // Muestra los bytes por campo en la terminal
            for (int i = 0; i < campos.size(); i++) {
                String campo = campos.get(i);
                int longitud = bytesCampos.get(i);
                // VALOR Columna
                String valorCampo = datos.get(campo);

                if( valorCampo == null ) {
                    valorCampo = "";
                }
                
                 String valorCampoForm = String.format("%1$-" + longitud + "s", valorCampo);
                
                System.out.print(campo+": "+valorCampoForm + "\n");
                
                rndFile.write(valorCampoForm.getBytes("UTF-8"),0, longitud);
            }
            System.out.println("\n");
        }
        catch( Exception ex ){
            System.out.println("ERROR: "+ex.getMessage());
        }
    }
    
    public void selectCampo(int numFila, String nomColumna) {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "r")) {
            // Verificar si numFila es válido
            if (numFila < 0 || numFila >= this.numFilas) {
                throw new IllegalArgumentException("Número de fila fuera de rango");
            }

            // Buscar el índice de la columna por el nombre
            int indexColumna = this.campos.indexOf(nomColumna);
            if (indexColumna == -1) {
                throw new IllegalArgumentException("Columna no encontrada");
            }

            // Calcular la posición dentro del archivo
            long pos = numFila * this.bytesLinea;
            for (int i = 0; i < indexColumna; i++) {
                pos += this.bytesCampos.get(i);
            }

            // Posicionarse en el archivo y leer el valor
            rndFile.seek(pos);
            byte[] buffer = new byte[this.bytesCampos.get(indexColumna)];
            rndFile.read(buffer);
            String valor = new String(buffer, "UTF-8").trim();

            // Mostrar el valor en la consola
            System.out.println("Valor en la fila " + numFila + ", columna '" + nomColumna + "': " + valor);
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public void selectColumna(String nomColumna){
        for (int i = 0; i < this.numFilas; i++) {
            selectCampo(i, nomColumna);
        }
    }
    
    public List<String> selectRowList(int numRegistro) {
        List<String> rowValues = new ArrayList<>();

        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "r")) {
            // Posicionarse en la fila indicada
            rndFile.seek(numRegistro * this.bytesLinea);

            // Leer todos los campos en la fila
            for (int i = 0; i < campos.size(); i++) {
                int longitud = bytesCampos.get(i);
                byte[] bytes = new byte[longitud];
                rndFile.read(bytes);
                String valor = new String(bytes, "UTF-8").trim(); 
                rowValues.add(valor);
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        return rowValues;
    }

    public Map<String, String> selectRowMap(int numRegistro) {
        Map<String, String> rowMap = new HashMap<>();

        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "r")) {
            // Posicionarse en la fila indicada
            rndFile.seek(numRegistro * this.bytesLinea);

            // Leer todos los campos en la fila y agregarlos al mapa
            for (int i = 0; i < campos.size(); i++) {
                String campo = campos.get(i);
                int longitud = bytesCampos.get(i);
                byte[] bytes = new byte[longitud];
                rndFile.read(bytes);
                String valor = new String(bytes, "UTF-8").trim(); // Leer el valor y quitar espacios en blanco
                rowMap.put(campo, valor);
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        return rowMap;
    }

    public void update(int row, Map<String, String> m) {
        // Verificar si la fila es válida
        if (row < 0 || row >= this.numFilas) {
            throw new IllegalArgumentException("Número de fila fuera de rango");
        }

        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "rws")) {
            // Posicionarnos para escribir en la fila correspondiente
            rndFile.seek(row * this.bytesLinea);

            // Actualizar cada campo en la fila con los valores del mapa
            for (int i = 0; i < campos.size(); i++) {
                String campo = campos.get(i);
                int longitud = bytesCampos.get(i);
                String valorCampo = m.getOrDefault(campo, ""); // Obtener el nuevo valor, o vacío si no se encuentra

                // Formatear el valor a la longitud correspondiente
                String valorCampoForm = String.format("%1$-" + longitud + "s", valorCampo);
                rndFile.write(valorCampoForm.getBytes("UTF-8"), 0, longitud);
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public void update(int row, String campo, String valor) {
        // Verificar si la fila es válida
        if (row < 0 || row >= this.numFilas) {
            throw new IllegalArgumentException("Número de fila fuera de rango");
        }

        // Verificar si el campo existe
        int indexColumna = this.campos.indexOf(campo);
        if (indexColumna == -1) {
            throw new IllegalArgumentException("Columna no encontrada");
        }

        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "rws")) {
            // Posicionarnos para escribir en la fila correspondiente
            rndFile.seek(row * this.bytesLinea);

            // Leer y actualizar todos los campos en la fila
            for (int i = 0; i < campos.size(); i++) {
                String campoActual = campos.get(i);
                int longitud = bytesCampos.get(i);
                String valorCampo;

                // Si es el campo que se va a actualizar, usamos el nuevo valor
                if (i == indexColumna) {
                    valorCampo = valor;
                } else {
                    // Si no es el campo a actualizar, leemos el valor actual
                    byte[] bytes = new byte[longitud];
                    rndFile.read(bytes);
                    valorCampo = new String(bytes, "UTF-8").trim(); // Mantener el valor actual
                }

                // Formatear el valor a la longitud correspondiente
                String valorCampoForm = String.format("%1$-" + longitud + "s", valorCampo);
                rndFile.write(valorCampoForm.getBytes("UTF-8"), 0, longitud);
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public void delete(int row) {
        // Verificar si la fila es válida
        if (row < 0 || row >= this.numFilas) {
            throw new IllegalArgumentException("Número de fila fuera de rango");
        }

        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "rw")) {
            // Posicionarnos para sobrescribir la fila correspondiente
            rndFile.seek(row * this.bytesLinea);

            // Crear un registro vacío (o un marcador) para sobrescribir la fila
            String registroEliminado = String.format("%1$-" + this.bytesLinea + "s", "REGISTRO ELIMINADO");
            rndFile.write(registroEliminado.getBytes("UTF-8"));

            System.out.println("Registro en la fila " + row + " ha sido eliminado.");
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    
    public static void main(String args[]){
        List campos = new ArrayList();
        List bytesCampos = new ArrayList();
        
        campos.add( "DNI" );
        campos.add( "NOMBRE" );
        campos.add( "DIRECCION" );
        campos.add( "CP" );
        
        bytesCampos.add( 9 );
        bytesCampos.add( 32 );
        bytesCampos.add( 32 );
        bytesCampos.add( 5 );
        
        try {
            RegistroBinarioAvanzado faa = new RegistroBinarioAvanzado("src/Unidad01/Practica08/archivo_binario_2.dat", campos, bytesCampos );
            Map datos = new HashMap();
            
            // PRIMER REGISTRO
            datos.put("DNI", "11111111A");
            datos.put("NOMBRE", "Nombre y Apellidos 1");            
            datos.put("DIRECCION", "Calle Principal Nº 7, Planta4, Letra J");
            datos.put("CP", "543210");
            faa.insertar(datos,0);
            datos.clear();
            
            // SEGUNDO REGISTRO
            datos.put("DNI", "22222222B");
            datos.put("NOMBRE", "Nombre2");
            datos.put("CP", "123456");
            datos.put("DIRECCION", "Calle Principal Nº 7, Planta4, Letra J");
            faa.insertar(datos,1);
            datos.clear();
            
            // TERCER REGISTRO
            datos.put("DNI", "333333B");
            datos.put("NOMBRE", "Nombre3");
            datos.put("CP", "56789");
            datos.put("DIRECCION", "DIrECCION");
            faa.insertar(datos,2);
            datos.clear();
            
            Map datos2 = new HashMap();
            
            System.out.println("VALOR A CAMBIAR:");
            faa.selectCampo(0, "DNI");
            
            System.out.println("\nCOLUMNA DEL VALOR:");
            faa.selectColumna("DNI");
            
            System.out.println("\nFILA DEL VALOR:");
            String fila = faa.selectRowList(0).toString();
            System.out.println(fila);
            
            System.out.println("\nVALOR ACTUALIZADO:");
            // PRIMER REGISTRO
            datos2.put("DNI", "48134118V");
            datos2.put("NOMBRE", "Kevin Gómez");            
            datos2.put("DIRECCION", "Calle Acacias Nº 6, Planta5, Letra i");
            datos2.put("CP", "18003");
            faa.update(0, datos2);
            datos2.clear();
            
            System.out.println("\nVALOR ELIMINADO:");
            faa.delete(0);
            faa.selectCampo(0, "DNI");
            
        }
        catch( IOException e ){
            System.err.print("ERROR: " + e.getMessage() );
        }
        catch( Exception e ){
            System.err.print("ERROR:" + e.getMessage() );
        }
    }
}
