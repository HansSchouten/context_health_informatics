package model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

public class RecordListTest {
	Column[] columns = 
		{new Column("column1"), new Column("column2"), new Column("column3"), new Column("column4")};
	RecordList list;
	Record c1;
	
	@Before
	public void setup() throws IOException, ParseException {
		
		c1 = new Record(DateUtils.parseDate("1995/08/04","yyyy/mm/DD"));
		c1.put("column1", new DataFieldInt(3));
		c1.put("column2", new DataFieldString("test"));
		
		list = new RecordList(columns);
		
	}
	
	
	/**
	 * Test if a record is added correctly to a record list
	 */
	@Test
	public void testaddRecord() throws IOException, ParseException {
	
		list.addRecord(c1);
		
		assertEquals("[{column1=3, column2=test}]", list.toString());
	
	}
	
	/**
	 * Test if the right record is returned with getRecord
	 */
	@Test
	public void testgetRecord() throws IOException, ParseException {
	
		list.add(c1);
		assertEquals(c1, list.getRecord(0));
	
	}
	
	/**
	 * Test if the property of the record list is correctly set
	 */
	@Test
	public void testsetProperty() throws IOException, ParseException {
	
		list.add(c1);
		list.setProperty("prop1", new Integer(1));
		assertEquals("1", list.properties.get("prop1").toString());
	
	}
	
	/**
	 * Test if the property of the record list is correctly returned
	 */
	@Test
	public void testgetProperty() throws IOException, ParseException {
	
		list.add(c1);
		list.setProperty("prop1", new Integer(1));
		assertEquals("1", list.getProperty("prop1").toString());
	
	}
	
	
}

