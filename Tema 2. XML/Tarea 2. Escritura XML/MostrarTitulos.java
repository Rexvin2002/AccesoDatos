
package Unidad02.Practica02;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author kgv17
 */
public class MostrarTitulos {

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Crear DocumentBuilder para leer el XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Leer el archivo XML
            Document doc = builder.parse("BOOKS.xml");
            doc.getDocumentElement().normalize();

            // Obtener todos los elementos "title"
            NodeList nodeList = doc.getElementsByTagName("title");
            System.out.println("Lista de títulos de los libros:");

            // Mostrar los títulos
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element titleElement = (Element) nodeList.item(i);
                System.out.println("- " + titleElement.getTextContent());
            }

        } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
            System.err.println("\nERROR: "+e.getMessage());
        }
    }
    
}
