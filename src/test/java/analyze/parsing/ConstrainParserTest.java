package analyze.parsing;

import static org.junit.Assert.*;
import model.DataFieldDouble;
import model.DateUtils;
import model.Record;
import model.SequentialData;

import org.junit.Before;
import org.junit.Test;

public class ConstrainParserTest {

	SequentialData data;
	Record r1, r2, r3;

	@Before
	public void setUp() throws Exception {
		data = new SequentialData();
		r1 = new Record(DateUtils.parseDate("2015/05/18", "yyyy/MM/dd"));
		r1.put("x", new DataFieldDouble(1));
		r2 = new Record(DateUtils.parseDate("2015/05/18", "yyyy/MM/dd"));
		r2.put("x", new DataFieldDouble(2));
		r3 = new Record(DateUtils.parseDate("2015/05/19", "yyyy/MM/dd"));
		r3.put("x", new DataFieldDouble(2));
		data.add(r1);
		data.add(r2);
		data.add(r3);
		System.out.println(data.contains(r1));
	}

	@Test
	public void testParseFilter() {
		Parser p = new Parser(data);
		SequentialData result = p.parseLine("FILTER WHERE COL(x) = 1.0", data);
		assertTrue(result.contains(r1));
	}

	@Test
	public void testParseFilter2() {
		Parser p = new Parser(data);
		SequentialData result = p.parseLine("FILTER WHERE COL(x) = 1.0", data);
		assertFalse(result.contains(r2));
	}

	@Test
	public void testParseFilterOr() {
		Parser p = new Parser(data);
		SequentialData result = p.parseLine("FILTER WHERE ((COL(x) = 1.0) or (COL(x) = 2.0))", data);
		System.out.println(result);
		assertTrue(result.contains(r1));
		assertTrue(result.contains(r2));
	}

}