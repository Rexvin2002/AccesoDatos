
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
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

/**
 *
 * @author kgv17
 */
public class XMLReader {
    
    public static void muestraNodo ( Node doc, int level, PrintStream ps ){
        // Indentar según el nivel
        for (int i = 0; i < level; i++) {
            ps.print("  ");
        }

        // Imprimir el nombre del nodo
        ps.print("<" + doc.getNodeName());

        // Imprimir atributos del nodo si los tiene
        NamedNodeMap atributos = doc.getAttributes();   // Obtiene los atributos de cada nodo
        if (atributos != null) {    // Si tiene atributo los imprime sino no
            for (int i = 0; i < atributos.getLength(); i++) {
                Node attr = atributos.item(i); // Coje el atributo en cuestión
                ps.print(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");  // Imprime horizontalmente el nombre del nodo junto su valor
            }
        }

        // Obtener y procesar hijos de los nodos
        NodeList children = doc.getChildNodes();    // Obtiene el numero de hijos total a partir del nodo del documento
        if (children.getLength() == 0) {    // Si el nodo no tiene hijos se cierra
            ps.println("/>");
        } else {
            ps.println(">");
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.getNodeType() == Node.TEXT_NODE) {
                    String content = child.getNodeValue().trim();
                    if (!content.isEmpty()) {
                        for (int j = 0; j < level + 1; j++) {
                            ps.print("  ");
                        }
                        ps.println(content);
                    }
                } else {
                    muestraNodo(child, level + 1, ps);  // Si el nodo no tiene texto de pasa al siguiente
                }
            }
            for (int i = 0; i < level; i++) {
                ps.print("  ");
            }
            ps.println("</" + doc.getNodeName() + ">");
        }
        
    }
    
    public static void main( String [] args){
        /*
        if (args.length == 0) {
            System.err.println("Debe proporcionar el nombre del archivo XML como argumento.");
            return;
        }

        String nomFich = args[0];
        */

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("src/Unidad02/Practica01/BOOKS.xml"));
            muestraNodo(doc, 0, System.out);
        } catch (FileNotFoundException | ParserConfigurationException | SAXException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException e) {
            System.err.println("\nERROR: " + e.getMessage());
        }
        
    }
    
}
