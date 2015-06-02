package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RecentFilesControllerTest {
    RecentFilesController rfc;

    @After
    public void clear() {
        rfc.clear();
    }

    @Test
    public void testSize() {
        rfc = new RecentFilesController("test", 5);
        assertTrue(rfc.getFiles().size() == 0);
    }

    @Test
    public void testAddInvalid() {
        rfc = new RecentFilesController("test", 5);
        rfc.add(new File("test"));
        assertTrue(rfc.getFiles().size() == 0);
    }

    @Test
    public void testAdd() {
        rfc = new RecentFilesController("test", 5);
        File f = new File("src/test/resources/test1.xml");
        rfc.add(f);
        assertTrue(rfc.getFiles().size() == 1);
    }

    @Test
    public void testAddOverflow() {
        // Adding 4 files event though the limit is 3
        rfc = new RecentFilesController("test", 3);
        for (int i = 1; i < 5; i++) {
            File f = new File("src/test/resources/test" + i + ".xml");
            rfc.add(f);
        }
        assertTrue(rfc.getFiles().size() == 3);
    }

    @Test
    public void testAddDuplicate() {
        rfc = new RecentFilesController("test", 3);

        File f = new File("src/test/resources/test1.xml");
        rfc.add(f);
        rfc.add(f);

        assertTrue(rfc.getFiles().size() == 1);
    }
}
