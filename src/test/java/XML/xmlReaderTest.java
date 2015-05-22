package XML;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import model.Group;

import org.junit.Test;
import org.xml.sax.SAXException;

public class xmlReaderTest {

    @Test
    public void test() throws ParserConfigurationException, SAXException, IOException {
        XMLhandler gm = new XMLhandler();
        ArrayList<Group> groups = gm.readXMLFile("src/main/resources/inputXMLfiles/textxml.xml");
        assertEquals(2, groups.size());
        assertEquals("name", groups.get(0).getName());
        assertEquals("name1", groups.get(1).getName());
    }

}
