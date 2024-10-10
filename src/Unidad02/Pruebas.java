
package Unidad02;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author kgv17
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Crear el documento XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Elemento raíz
            Element rootElement = doc.createElement("bookstore");
            doc.appendChild(rootElement);

            // Elemento hijo
            Element bookElement = doc.createElement("book");
            rootElement.appendChild(bookElement);

            // Atributo
            bookElement.setAttribute("category", "fiction");

            // Elementos dentro del libro
            Element titleElement = doc.createElement("title");
            titleElement.appendChild(doc.createTextNode("Harry Potter"));
            bookElement.appendChild(titleElement);

            Element authorElement = doc.createElement("author");
            authorElement.appendChild(doc.createTextNode("J.K. Rowling"));
            bookElement.appendChild(authorElement);

            Element priceElement = doc.createElement("price");
            priceElement.appendChild(doc.createTextNode("29.99"));
            bookElement.appendChild(priceElement);

            // Transformar el documento XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:/Users/kgv17/Desktop/EjemploXML.xml"));

            // Escribir el archivo XML
            transformer.transform(source, result);

            System.out.println("Archivo XML creado correctamente.");

        } catch (ParserConfigurationException | TransformerException | DOMException e) {
            System.err.println("\nERROR: "+e.getMessage());
        }
    }
    
}
