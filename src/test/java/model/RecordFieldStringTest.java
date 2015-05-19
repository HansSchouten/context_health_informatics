package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RecordFieldStringTest {

	@Test
	public void toStringTest() {
	    DataFieldString fl = new DataFieldString("hoi");
		assertEquals(fl.toString(), "hoi");
	}
}
