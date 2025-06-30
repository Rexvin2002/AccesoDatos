
package Unidad02.Practica03;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

/**
 *
 * @author kgv17
 */
public class ConsultasXPath {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Cargar el archivo XML
            File inputFile = new File("BOOKS.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();
            
            // Crear objeto XPath
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            
            // 1. Consulta: Seleccionar todos los títulos de los libros
            XPathExpression expr1 = xpath.compile("/catalog/book/title");
            NodeList titles = (NodeList) expr1.evaluate(doc, XPathConstants.NODESET);
            System.out.println("\nConsulta 1 - Títulos de todos los libros:");
            for (int i = 0; i < titles.getLength(); i++) {
                System.out.println(titles.item(i).getTextContent());
            }
            
            // 2. Consulta: Seleccionar el autor del libro con ID 'bk102'
            XPathExpression expr2 = xpath.compile("/catalog/book[@id='bk102']/author");
            String author = (String) expr2.evaluate(doc, XPathConstants.STRING);
            System.out.println("\nConsulta 2 - Autor del libro con id 'bk102': \n" + author);
            
            // 3. Consulta: Seleccionar todos los libros con precio superior a 30
            XPathExpression expr3 = xpath.compile("/catalog/book[price>30]/title");
            NodeList expensiveBooks = (NodeList) expr3.evaluate(doc, XPathConstants.NODESET);
            System.out.println("\nConsulta 3 - Libros con precio superior a 30:");
            for (int i = 0; i < expensiveBooks.getLength(); i++) {
                System.out.println(expensiveBooks.item(i).getTextContent());
            }
            
            // 4. Consulta: Seleccionar las fechas de publicación de todos los libros
            XPathExpression expr4 = xpath.compile("/catalog/book/publish_date");
            NodeList publishDates = (NodeList) expr4.evaluate(doc, XPathConstants.NODESET);
            System.out.println("\nConsulta 4 - Fechas de publicación de los libros:");
            for (int i = 0; i < publishDates.getLength(); i++) {
                System.out.println(publishDates.item(i).getTextContent());
            }
            
            // 5. Consulta: Seleccionar el primer libro en la lista
            XPathExpression expr5 = xpath.compile("/catalog/book[1]/title");
            String firstBookTitle = (String) expr5.evaluate(doc, XPathConstants.STRING);
            System.out.println("\nConsulta 5 - Título del primer libro: \n" + firstBookTitle);
            
        } catch (IOException | ParserConfigurationException | XPathExpressionException | DOMException | SAXException e) {
            System.err.println("\nERROR: "+e.getMessage());
        }
    }
    
}
