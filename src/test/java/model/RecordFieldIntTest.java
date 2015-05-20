package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RecordFieldIntTest {

	@Test
	public void toStringTest() {
	    DataFieldInt fl = new DataFieldInt(10);
		assertEquals(fl.toString(), "10");
	}

}
