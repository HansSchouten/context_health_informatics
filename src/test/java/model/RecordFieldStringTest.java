package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RecordFieldStringTest {

	@Test
	public void toStringTest() {
		RecordFieldString fl = new RecordFieldString("hoi");
		assertEquals(fl.toString(), "hoi");
	}
}
