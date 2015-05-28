package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
		assertEquals(sq.iterator().next().getTimeStamp(), record1.getTimeStamp());
	}
	
	@Test
	public void testSequential2() {
		Iterator<Record> iterator = sq.iterator();
		iterator.next();
		assertEquals(iterator.next().getTimeStamp(), record2.getTimeStamp());
	}
	
	@Test
	public void testSequentialNegative() {
		Iterator<Record> iterator = sq.iterator();
		iterator.next();
		assertNotEquals(iterator.next().getTimeStamp(), record1.getTimeStamp());
	}
	
	/**
	 * This test tests concatenating adding a RecordList to a sequential datastructure.
	 */
	@Test
	public void testConcatToSequentialDataStructures() {
		Record record3 = new Record(LocalDateTime.ofEpochSecond(1430909362, 0, ZoneOffset.UTC));
		Column[] cl = {new Column("test", ColumnType.STRING)};
		RecordList recordList = new RecordList(cl);
		recordList.add(record3);
		
		sq.addRecordList(recordList);
		Iterator<Record> iterator = sq.iterator();
		iterator.next();
		assertEquals(iterator.next().getTimeStamp(), record3.getTimeStamp());
	}

}
