package model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class GroupTest {

	String name = "Hospital Data";
	String delimiter = ",";
	String[] columns = {"column1", "column2", "column3"};
	String primary = "column1";
	
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
