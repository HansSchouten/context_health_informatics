package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;

import org.junit.Test;

import controller.importcontroller.ColumnKey;
import controller.importcontroller.PrimaryKey;

public class GroupTest {

    String name = "Hospital Data";
    String delimiter = ",";
    Column[] columns = 
        {new Column("column1", ColumnType.STRING), new Column("column2", ColumnType.STRING), new Column("column3", ColumnType.STRING)};
    PrimaryKey primary = new ColumnKey("column1");
    
    @Test
    public void testConstructor() {
        Group group = new Group(name, delimiter, columns, primary);
        assertNotEquals(group, null);
    }
    
    @Test
    public void testAddFile() throws IOException {
        Group group = new Group(name, delimiter, columns, primary);
        group.addFile("src/main/resources/test_input.txt");
        
        // test group size
        assertEquals(1, group.size());
    }
    
    @Test
    public void testAddFiles() throws IOException {
        Group group = new Group(name, delimiter, columns, primary);
        group.addFile("src/main/resources/test_input_metadata.txt");
        group.addFile("src/main/resources/test_input.txt");
        
        // test group size
        assertEquals(2, group.size());
    }

}
