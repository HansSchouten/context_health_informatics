package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DateColumnTest {

	DateColumn column;
	@Before
	public void setUp() throws Exception {
		column = new DateColumn("test", "HHmm", true);
	}

	@Test
	public void sortOnThisFieldTest() {
		assertTrue(column.sortOnThisField());
	}

	@Test
	public void getDateFormatTest() {
		assertEquals(column.getDateFormat(), "HHmm");
	}

}
