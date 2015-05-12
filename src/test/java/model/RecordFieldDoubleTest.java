package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RecordFieldDoubleTest {

	@Test
	public void toStringTest() {
		RecordFieldDouble fl = new RecordFieldDouble(10.0);
		assertEquals(fl.toString(), "10.0");
	}

}
