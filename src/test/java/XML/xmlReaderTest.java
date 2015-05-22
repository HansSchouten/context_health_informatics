package xml;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import model.Group;

import org.junit.Test;
import org.xml.sax.SAXException;

public class xmlReaderTest {

    @Test
    public void readTest() throws ParserConfigurationException, SAXException, IOException {
        XMLhandler gm = new XMLhandler();
        ArrayList<Group> groups = gm.readXMLFile("src/main/resources/inputXMLfiles/textxml.xml");
        assertEquals(2, groups.size());
        assertEquals("name", groups.get(0).getName());
        assertEquals("name1", groups.get(1).getName());
        assertEquals("del", groups.get(0).getDelimiter());
    }
    
    @Test
    public void read1Test() throws ParserConfigurationException, SAXException, IOException {
        XMLhandler gm = new XMLhandler();
        ArrayList<Group> groups = gm.readXMLFile("src/main/resources/inputXMLfiles/textxml.xml");
        assertEquals(2, groups.size());
        assertTrue(groups.get(0).containsKey("src/main/resources/test_input.txt"));
        assertTrue(groups.get(1).containsKey("src/main/resources/test_input.txt"));
    }
    
    @Test (expected = SAXException.class)
    public void invalidAttributesTest() throws ParserConfigurationException, SAXException, IOException {
        XMLhandler gm = new XMLhandler();
        ArrayList<Group> groups = gm.readXMLFile("src/main/resources/inputXMLfiles/invalidAttributesTest.xml");
        assertEquals(2, groups.size());
        assertEquals("name", groups.get(0).getName());
        assertEquals("name1", groups.get(1).getName());
    }
    
    @Test (expected = SAXException.class)
    public void invalidAttributes2Test() throws ParserConfigurationException, SAXException, IOException {
        XMLhandler gm = new XMLhandler();
        ArrayList<Group> groups = gm.readXMLFile("src/main/resources/inputXMLfiles/invalidAttributesTest1.xml");
        assertEquals(2, groups.size());
        assertEquals("name", groups.get(0).getName());
        assertEquals("name1", groups.get(1).getName());
    }
    
    @Test (expected = SAXException.class)
    public void invalidAttributes3Test() throws ParserConfigurationException, SAXException, IOException {
        XMLhandler gm = new XMLhandler();
        ArrayList<Group> groups = gm.readXMLFile("src/main/resources/inputXMLfiles/invalidAttributesTest2.xml");
        assertEquals(2, groups.size());
        assertEquals("name", groups.get(0).getName());
        assertEquals("name1", groups.get(1).getName());
    }
    
    @Test (expected = SAXException.class)
    public void invalidFileTest() throws ParserConfigurationException, SAXException, IOException {
        XMLhandler gm = new XMLhandler();
        ArrayList<Group> groups = gm.readXMLFile("src/main/resources/inputXMLfiles/invalidFileTest.xml");
        assertEquals(2, groups.size());
        assertEquals("name", groups.get(0).getName());
        assertEquals("name1", groups.get(1).getName());
    }
    
    @Test (expected = SAXException.class)
    public void handlerTest() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.startElement(null, null, "groups", null);
    }
    
    @Test (expected = SAXException.class)
    public void handler1Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.startElement(null, null, "group", null);
    }
    
    @Test (expected = SAXException.class)
    public void handler2Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.startElement(null, null, "name", null);
    }
    
    @Test (expected = SAXException.class)
    public void handler3Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.startElement(null, null, "delimiter", null);
    }
    
    @Test (expected = SAXException.class)
    public void handler4Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.startElement(null, null, "primary", null);
    }
    
    @Test (expected = SAXException.class)
    public void handler5Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.startElement(null, null, "columns", null);
    }

    @Test (expected = SAXException.class)
    public void handler6Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.startElement(null, null, "column", null);
    }
    
    @Test (expected = SAXException.class)
    public void handler7Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.startElement(null, null, "files", null);
    }
    
    @Test (expected = SAXException.class)
    public void handler8Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.startElement(null, null, "file", null);
    }
    
    @Test (expected = SAXException.class)
    public void handler9Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.startElement(null, null, "hoi", null);
    }
    
    @Test (expected = SAXException.class)
    public void handlerEndTest() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.endElement(null, null, "hoi");
    }
    
    @Test (expected = SAXException.class)
    public void handlerEnd1Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.endElement(null, null, "columns");
    }
    
    @Test (expected = SAXException.class)
    public void handlerEnd2Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.endElement(null, null, "files");
    }
    
    @Test (expected = SAXException.class)
    public void handlerEnd3Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.endElement(null, null, "group");
    }
    
    @Test (expected = SAXException.class)
    public void handlerEnd4Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.elements.push("hoi");
        sh.endElement(null, null, "groups");
    }
    
    @Test (expected = SAXException.class)
    public void writeTest() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.writeGroup();
    }
    
    @Test (expected = SAXException.class)
    public void write1Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.name = "name";
        sh.writeGroup();
    }
    
    @Test (expected = SAXException.class)
    public void write2Test() throws ParserConfigurationException, SAXException, IOException {
        SAXHandler sh = new SAXHandler();
        sh.name = "name";
        sh.delimiter = "name";
        sh.writeGroup();
    }
}
