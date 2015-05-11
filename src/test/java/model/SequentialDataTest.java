package model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class SequentialDataTest {

	Record record1, record2;
	SequentialData sq;
	
	@Before
	public void setUp() throws Exception {
		record1 = new Record(LocalDateTime.ofEpochSecond(1430909359, 0, ZoneOffset.UTC));
		record2 = new Record(LocalDateTime.ofEpochSecond(1430909365, 0, ZoneOffset.UTC));
		sq = new SequentialData();
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
