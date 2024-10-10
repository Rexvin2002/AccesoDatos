
package Unidad02.Practica01;

import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author kgv17
 */
public class XMLReader2 {
    
    public static void muestraNodo ( Node nodo, int level, PrintStream ps ){
        String n = nodo.getNodeName();
        System.out.println(n);
    }
    
    public static void main( String [] args){
        /*
        if (args.length == 0) {
            System.err.println("Debe proporcionar el nombre del archivo XML como argumento.");
            return;
        }
        */
        
        // Crea la fabrica de constructor de documentos
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);

        try{
            // Crea el constructor de documentos
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Crea un documento
            Document doc = db.parse( new File("src/Unidad02/Practica01/BOOKS.xml") );
            doc.getNodeValue();

            // Método que muestra el nodo
            muestraNodo( doc, 0, System.out );

        }catch( FileNotFoundException | ParserConfigurationException | SAXException e ){
            System.err.println("\nERROR: "+e.getMessage());

        }catch( IOException e ){
            System.err.println("\nERROR: "+e.getMessage());
        }
        
    }
    
}
