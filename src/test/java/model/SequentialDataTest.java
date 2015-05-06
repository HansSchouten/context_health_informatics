package model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class SequentialDataTest {

	Record record1, record2;
	SequentialData sq;
	
	@Before
	public void setUp() throws Exception {
		record1 = new Record(1430909359);
		record2 = new Record(1430909365);
		sq = new SequentialData();
		record1.put("test", 54334);
		record2.put("test", 54335);
		sq.add(record1);
		sq.add(record2);
	}

	@Test
	public void testSequential1() {
		assertEquals(sq.iterator().next(), record1);
	}
	
	@Test
	public void testSequential2() {
		Iterator<Record> iterator = sq.iterator();
		iterator.next();
		assertEquals(iterator.next(), record2);
	}

}
