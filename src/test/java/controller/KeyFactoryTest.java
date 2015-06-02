package controller;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.importcontroller.ColumnKey;
import controller.importcontroller.FileNameKey;
import controller.importcontroller.KeyFactory;
import controller.importcontroller.NoKey;
import controller.importcontroller.PrimaryKey;

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
