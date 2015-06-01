package controller;

import static org.junit.Assert.*;
import model.importcontroller.FileNameKey;
import model.importcontroller.KeyFactory;
import model.importcontroller.NoKey;
import model.importcontroller.ColumnKey;
import model.importcontroller.PrimaryKey;

import org.junit.Test;

public class KeyFactoryTest {

    @Test
    public void singletonTest() {
        assertEquals(KeyFactory.getInstance(), KeyFactory.getInstance());
    }
    
    @Test
    public void getPrimaryKeyTest() {
        PrimaryKey pk = KeyFactory.getInstance().getNewKey("No primary key");
        assertTrue(pk instanceof NoKey);
        assertEquals("No primary key", pk.toString());
    }
    
    @Test
    public void getPrimaryKey1Test() {
        PrimaryKey pk = KeyFactory.getInstance().getNewKey("File name");
        assertTrue(pk instanceof FileNameKey);
        assertEquals("File name", pk.toString());
    }
    
    @Test
    public void getPrimaryKey2Test() {
        PrimaryKey pk = KeyFactory.getInstance().getNewKey("col");
        assertTrue(pk instanceof ColumnKey);
        assertEquals("col", pk.toString());
    }


}
