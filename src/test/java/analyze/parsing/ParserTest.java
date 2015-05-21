package analyze.parsing;

import static org.junit.Assert.*;
import model.SequentialData;

import org.junit.Before;
import org.junit.Test;

import analyze.AnalyzeException;

public class ParserTest {

	SequentialData data;

	@Before
	public void setup() {
		data = new SequentialData();
	}

	@Test
	public void testConstructor() {
		Parser parser = new Parser(data);
		assertTrue(parser instanceof Parser);
	}

	@Test
	public void testParse() throws AnalyzeException {
		Parser parser = new Parser(data);
		SequentialData result = parser.parse("CHUNK parameters");
		assertTrue(result instanceof SequentialData);
	}

	
	@Test
	public void testParseCount() throws AnalyzeException {
		Parser parser = new Parser(data);
		SubParser comp = parser.getSubParser("compute");
		assertEquals("class analyze.parsing.ComputingParser", comp.getClass().toString());
	}
	
	@Test
	public void testInvalidOperator() throws AnalyzeException {
		Parser parser = new Parser(data);
		assertEquals(null, parser.getSubParser("invalid"));
	
	}
}
