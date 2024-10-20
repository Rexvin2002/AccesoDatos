package Unidad02.Practica03;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathExamples {

    public static void main(String[] args) {
        
        try {
            // Definir las expresiones XPath
            String[] expressions = {
                "//book[publish_date > '2001-01-01']/title/text()", 
                "//book[price < 8]/title/text()",
                "//book[1]/title/text()",
                "//book/author/text()",
                "count(//book/title)",
                "//book[starts-with(author, 'Neal')]/title/text()",
                "//book[contains(author, 'Niven')]/title/text()",
                "count(//book[author = 'Neal Stephenson'])",
                "//book[author = 'Neal Stephenson']/title/text()"
            };
            
            // Configurar el DocumentBuilder para leer el XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("src/Unidad02/Practica02/BOOKS.xml");

            // Depuración: verificar que el documento no es nulo
            if (doc == null) {
                System.out.println("El documento no se pudo cargar.");
                return;
            }

            // Total de libros en el documento
            NodeList allBooks = doc.getElementsByTagName("book");
            System.out.println("Total de libros en el documento: " + allBooks.getLength());

            // Crear un objeto XPath
            XPath xpath = XPathFactory.newInstance().newXPath();

            // Evaluar cada expresión XPath
            for (String expression : expressions) {
                System.out.println("\nEvaluando expresión: " + expression);
                
                if (expression.startsWith("count(")) {
                    // Para las expresiones de conteo, se evalúa como un número
                    Number count = (Number) xpath.evaluate(expression, doc, XPathConstants.NUMBER);
                    System.out.println("Resultado: " + count);
                } else {
                    // Para las demás expresiones, se evalúa como un NodeList
                    NodeList result = (NodeList) xpath.compile(expression).evaluate(doc, XPathConstants.NODESET);
                    
                    if (result.getLength() == 0) {
                        System.out.println("No se encontraron resultados.");
                    } else {
                        System.out.println("Resultados:");
                        for (int i = 0; i < result.getLength(); i++) {
                            System.out.println("- " + result.item(i).getNodeValue());
                        }
                    }
                }
            }

        } catch (IOException | ParserConfigurationException | XPathExpressionException | SAXException e) {
            System.err.println("\nERROR: " + e.getMessage());
        }
    }
}
