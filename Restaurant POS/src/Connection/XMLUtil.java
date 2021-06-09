package Connection;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class XMLUtil {
    
    private static Document config;
    private static String IpEnvVar;
    private static String LoginEnvVar; 
    private static String LoginPWEnvVar;
    private static final String ConfigXML = "Env.xml";
    
    
    
    private static void getDocument() throws ParserConfigurationException, SAXException {

        File fXmlFile = new File(ConfigXML);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          try {
              config = dBuilder.parse(fXmlFile);
          } catch (IOException ex) {
              Logger.getLogger(XMLUtil.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
    private static void readEnvConfig() {
        doc.getDocumentElement().normalize();
 
          System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
    NodeList nList = doc.getElementsByTagName("staff");
    System.out.println("----------------------------");
    
    
    
    
    
    for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        System.out.println("\nCurrent Element :" + nNode.getNodeName());
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            System.out.println("Staff id : "
                               + eElement.getAttribute("id"));
            System.out.println("First Name : "
                               + eElement.getElementsByTagName("firstname")
                                 .item(0).getTextContent());
            System.out.println("Last Name : "
                               + eElement.getElementsByTagName("lastname")
                                 .item(0).getTextContent());
            System.out.println("Nick Name : "
                               + eElement.getElementsByTagName("nickname")
                                 .item(0).getTextContent());
            System.out.println("Salary : "
                               + eElement.getElementsByTagName("salary")
                                 .item(0).getTextContent());
        }
    }
    } catch (Exception e) {
    e.printStackTrace();
    }
  }


    }


    
    
    
    
    
  
}