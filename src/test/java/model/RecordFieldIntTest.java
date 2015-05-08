package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RecordFieldIntTest {

	@Test
	public void toStringTest() {
		RecordFieldInt fl = new RecordFieldInt(10);
		assertEquals(fl.toString(), "10");
	}

}
