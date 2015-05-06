package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RecordTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void compareToTest() {
		Record c1 = new Record(100);
		Record c2 = new Record(101);
		assertEquals(c1.compareTo(c2), -1);
	}
	
	@Test
	public void compareToTest2() {
		Record c1 = new Record(101);
		Record c2 = new Record(100);
		assertEquals(c1.compareTo(c2), 1);
	}

}
