package analyze.parsing;

import static org.junit.Assert.*;

import org.junit.Test;

import analyze.chunking.ChunkingException;

public class ChunkingParserTest {

    @Test
    public void constructorTest() {
    	ChunkingParser cp = new ChunkingParser();
        assertTrue(cp != null);
    }

    @Test (expected = ChunkingException.class)
    public void noColumnNameTest() throws ChunkingException {
    	ChunkingParser cp = new ChunkingParser();
        String operation = "ON";
        cp.parseOperation(operation, null);
    }

    @Test (expected = ChunkingException.class)
    public void noPeriodTest() throws ChunkingException {
    	ChunkingParser cp = new ChunkingParser();
        String operation = "PER";
        cp.parseOperation(operation, null);
    }

}
