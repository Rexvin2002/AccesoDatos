
package Unidad01.Practica10;

/**
 * Kevin Gómez Valderas           2ºDAM
 */

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

public class XMLReader {

    private static final String INDENT_CHAR = " ";
    
    public static void muestraNodo ( Node nodo, int level, PrintStream ps ){
        // Indentar el contenido según el nivel
        for (int i = 0; i < level; i++) {
            ps.print(INDENT_CHAR);
        }

        // Imprimir el nombre del nodo
        ps.print("<" + nodo.getNodeName());

        // Si el nodo tiene atributos, los imprimimos
        NamedNodeMap atributos = nodo.getAttributes();
        if (atributos != null) {
            for (int i = 0; i < atributos.getLength(); i++) {
                Node attr = atributos.item(i);
                ps.print(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
            }
        }

        // Obtener el contenido del nodo y sus hijos
        NodeList children = nodo.getChildNodes();
        if (children.getLength() == 0) {
            // Si el nodo no tiene hijos, cerramos la etiqueta en la misma línea
            ps.println("/>");
        } else {
            ps.println(">");

            // Recorremos los hijos del nodo
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);

                // Si el hijo es un nodo de texto, imprimimos su contenido
                if (child.getNodeType() == Node.TEXT_NODE) {
                    String content = child.getNodeValue().trim();
                    if (!content.isEmpty()) {
                        for (int j = 0; j < level + 1; j++) {
                            ps.print(INDENT_CHAR);
                        }
                        ps.println(content);
                    }
                } else {
                    // Si es un nodo de otro tipo, lo mostramos recursivamente
                    muestraNodo(child, level + 1, ps);
                }
            }

            // Indentamos y cerramos la etiqueta
            for (int i = 0; i < level; i++) {
                ps.print(INDENT_CHAR);
            }
            ps.println("</" + nodo.getNodeName() + ">");
        }
        
    }
    
    public static void main( String [] args){
        if (args.length == 0) {
            System.err.println("Debe proporcionar el nombre del archivo XML como argumento.");
            return;
        }

        String nomFich = args[0];

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(nomFich));
            muestraNodo(doc, 0, System.out);
        } catch (FileNotFoundException | ParserConfigurationException | SAXException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException e) {
            System.err.println("\nERROR: " + e.getMessage());
        }
        
    }
    
}
