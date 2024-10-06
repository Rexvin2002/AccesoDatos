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
    private final List<Integer> camposLength; // V2
    private long longReg;       // Bytes por registro >>> LINEA
    private long numReg = 0;    // Número de registros dentro del fichero
    
    Ejercicio_faa( String path, List<String> campos, List<Integer> camposLength ) throws IOException
    {
        this.campos = campos;
        this.camposLength = camposLength;
      
        this.f = new File( path );
        this.longReg = 0;
        
        for( Integer campo: camposLength ){
            System.out.print("*"+campo+"*");
            this.longReg += campo;
        }
        
        if( f.exists() ){
            this.numReg = f.length() / this.longReg;
        }
    }
    
    public long getNumReg()
    {
        return numReg;
    }
    
    public void insertar( Map<String,String> reg ) throws IOException
    {
        insertar( reg, this.numReg++);        
    }
    
    public void insertar( Map<String,String> reg, long pos )
    {
        // ABRIR ARCHIVO BINARIO
        try( RandomAccessFile rndFile = new RandomAccessFile( this.f, "rws" ) ) {
            
            // POSICIONARNOS PARA ESCRIBIR
            rndFile.seek( pos * this.longReg );
            
            int total = campos.size();
            for( int i =0; i<total; i++ )
            // for( String campo : campos )
            {
                // Nombre Columna
                String nomCampo = campos.get(i);
                
                // Tamaño Columna
                Integer longCampo = camposLength.get(i);
                
                // VALOR Columna
                String valorCampo = reg.get(nomCampo);
                        
                if( valorCampo == null )
                {
                    valorCampo = "";
                }
                
                String valorCampoForm = String.format("%1$-" + longCampo + "s", valorCampo );
                
                System.out.print(valorCampoForm);
                
                rndFile.write(valorCampoForm.getBytes("UTF-8"),0, longCampo);
            }  
        }
        catch( Exception ex )
        {
            System.err.println("\nError: "+ex.getMessage());
        }
    }
    
    public static void main(String args[])
    {
                
        List campos = new ArrayList();
        List camposLength = new ArrayList();
        
        campos.add( "DNI" );
        campos.add( "NOMBRE" );
        campos.add( "DIRECCION" );
        campos.add( "CP" );
        
        camposLength.add( 9 );
        camposLength.add( 32 );
        camposLength.add( 32 );
        camposLength.add( 5 );
        
        try {
            
            Ejercicio_faa faa = new Ejercicio_faa("src/Unidad01/Practica08/archivo_binario_3.datt", campos, camposLength );
            
            Map reg = new HashMap();
            
            // PRIMER REGISTRO
            reg.put("DNI", "11111111A");
            reg.put("NOMBRE", "Nombre y Apellidos 1");            
            reg.put("DIRECCION", "Calle Principal Nº 7, Planta4, Letra J");
            reg.put("CP", "543210");
            faa.insertar(reg,10);
            reg.clear();
            
            // SEGUNDO REGISTRO
            reg.put("DNI", "22222222B");
            reg.put("NOMBRE", "Nombre2");
            reg.put("CP", "123456");
            reg.put("DIRECCION", "Calle Principal Nº 7, Planta4, Letra J");
            faa.insertar(reg,9);
            reg.clear();
            
            // TERCER REGISTRO
            reg.put("DNI", "333333B");
            reg.put("NOMBRE", "Nombre3");
            reg.put("CP", "56789");
            reg.put("DIRECCION", "DIrECCION");
            faa.insertar(reg,0);
            reg.clear();
                        
            
        }
        catch( IOException e )
        {
            System.err.print("EXCE" + e.getMessage() );
        }
        catch( Exception e )
        {
            System.err.print("EXCE" + e.getMessage() );
        }
    }
        
}
