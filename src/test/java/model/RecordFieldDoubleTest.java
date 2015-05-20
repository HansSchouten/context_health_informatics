package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RecordFieldDoubleTest {

	@Test
	public void toStringTest() {
	    DataFieldDouble fl = new DataFieldDouble(10.0);
		assertEquals(fl.toString(), "10.0");
	}

}
