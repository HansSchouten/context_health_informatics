package analyze.parsing;

import static org.junit.Assert.*;
import model.SequentialData;

import org.junit.Before;
import org.junit.Test;

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
	public void testParse() {
		Parser parser = new Parser(data);
		SequentialData result = parser.parse("CHUNK parameters");
		assertTrue(result instanceof SequentialData);
	}

}
