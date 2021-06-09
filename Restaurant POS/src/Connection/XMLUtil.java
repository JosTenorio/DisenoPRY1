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
    private static final String configXML = "Env.xml";
    
    
    
    private static void getDocument() throws ParserConfigurationException, SAXException {

        File fXmlFile = new File(configXML);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          try {
              config = dBuilder.parse(fXmlFile);
          } catch (IOException ex) {
              Logger.getLogger(XMLUtil.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
    public static void readEnvConfig() {
        try {
            getDocument();
        } catch (ParserConfigurationException | SAXException ex) {
            System.out.println(ex.getMessage());
        }
        config.getDocumentElement().normalize();
        NodeList nList = config.getElementsByTagName("databaseLogin");
        Node DBLoginNode = nList.item(0);
        Element DBLoginElement = (Element) DBLoginNode;
        String ip = DBLoginElement.getElementsByTagName("ip").item(0).getTextContent();
        String port = DBLoginElement.getElementsByTagName("port").item(0).getTextContent();
        String database = DBLoginElement.getElementsByTagName("database").item(0).getTextContent();
        String login = DBLoginElement.getElementsByTagName("username").item(0).getTextContent();
        String password = DBLoginElement.getElementsByTagName("password").item(0).getTextContent();
        String proxyInit = DBLoginElement.getElementsByTagName("proxyInit").item(0).getTextContent();
        Proxy.setProxyInit(proxyInit);
        ConnectionManager.setConnString(ip, port, database);
        ConnectionManager.setLogIn(login, password);
    }
}