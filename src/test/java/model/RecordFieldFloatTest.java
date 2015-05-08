package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RecordFieldFloatTest {

	@Test
	public void toStringTest() {
		RecordFieldFloat fl = new RecordFieldFloat(10.0);
		assertEquals(fl.toString(), "10.0");
	}

}
