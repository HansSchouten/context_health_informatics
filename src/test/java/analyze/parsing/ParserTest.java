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
		Parser parser = new Parser();
		assertTrue(parser instanceof Parser);
	}

	@Test
	public void testParse() throws AnalyzeException {
		Parser parser = new Parser();
		SequentialData result = parser.parse("CHUNK ON parameters", data);
		assertTrue(result instanceof SequentialData);
	}

}
