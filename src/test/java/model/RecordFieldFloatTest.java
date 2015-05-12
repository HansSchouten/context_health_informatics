package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RecordFieldFloatTest {

	@Test
	public void toStringTest() {
		RecordField f2 = new RecordFieldFloat(10.0);
		RecordFieldFloat fl = new RecordFieldFloat(10.0);
		assertEquals(fl.toString(), "10.0");
	}

}
