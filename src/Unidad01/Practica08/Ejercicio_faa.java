
package Unidad01.Practica08;

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

public class Ejercicio_faa {

    private final File f;
    private final List<String> campos;
    private final List<Integer> bytesCampos;
    private long bytesLinea;       // Bytes por linea
    private long numFilas = 0;    // Número de filas 
    
    public Ejercicio_faa(String path, List<String> campos, List<Integer> bytesCampos) throws IOException {
        
        // Crea el archivo
        this.f = new File(path);
        if (!this.f.exists()) {
            this.f.createNewFile();
        }
        
        this.campos = campos;
        this.bytesCampos = bytesCampos;
        
        this.bytesLinea = 0;
        
        // Muestra los bytes por campo en la terminal
        System.out.println("\nBYTES POR CAMPO / COLUMNA");
        
        // Muestra todos los campos con su longitud e incrementa bytesLinea según la longitud de cada campo
        for (int i = 0; i < campos.size(); i++) {
            
            String campo = campos.get(i);
            int longitud = bytesCampos.get(i);

            System.out.print(campo + ": " + longitud + "\n");
            this.bytesLinea += longitud; // 
            
        }
        
        System.out.println("\n");

        // Establece el número de filas dividiendo el length total del archivo entre el número de bytes por línea
        if (f.exists() && bytesLinea > 0) {
            this.numFilas = (int) (f.length() / this.bytesLinea);
        }
    }
    
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
            Ejercicio_faa faa = new Ejercicio_faa("C:/Users/kgv17/OneDrive/Escritorio/archivo.dat", campos, bytesCampos );
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
        }
        catch( IOException e ){
            System.err.print("ERROR: " + e.getMessage() );
        }
        catch( Exception e ){
            System.err.print("ERROR:" + e.getMessage() );
        }
    }
}
