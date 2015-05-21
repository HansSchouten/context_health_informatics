package XML;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class GroupManager {
    
    SAXParser parser;

    public GroupManager() throws ParserConfigurationException, SAXException {
        
        parser = SAXParserFactory.newInstance().newSAXParser();
    }
    
    public void readXMLFile(String filename) throws SAXException, IOException {
        parser.parse(filename, new SAXHandler());
    }
    
    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {
        GroupManager gm = new GroupManager();
        gm.readXMLFile("src/main/java/XML/textxml.xml");
    }
    
}
