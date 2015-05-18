package analyze.condition;

import static org.junit.Assert.*;
import model.DataFieldBoolean;
import model.DataFieldInt;
import model.UnsupportedFormatException;

import org.junit.Test;

public class UnaryOpTermTest {

    @Test
    public void minTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        UnaryOpTerm bn = new UnaryOpTerm(UnaryOperator.getOperator("-"), lt);
        assertTrue(bn.evaluate(null).getIntegerValue() == -10);
    }
    
    @Test (expected = UnsupportedFormatException.class)
    public void minErrorTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldBoolean(true));
        UnaryOpTerm bn = new UnaryOpTerm(UnaryOperator.getOperator("-"), lt);
        assertTrue(bn.evaluate(null).getIntegerValue() == -10);
    }
    
    @Test (expected = UnsupportedFormatException.class)
    public void notErrorTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        UnaryOpTerm bn = new UnaryOpTerm(UnaryOperator.getOperator("not"), lt);
        assertTrue(bn.evaluate(null).getIntegerValue() == -10);
    }
    
    @Test
    public void notTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldBoolean(true));
        UnaryOpTerm bn = new UnaryOpTerm(UnaryOperator.getOperator("not"), lt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test
    public void not1Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldBoolean(false));
        UnaryOpTerm bn = new UnaryOpTerm(UnaryOperator.getOperator("not"), lt);
        assertTrue(bn.evaluate(null).getBooleanValue() == true);
    }
    
    @Test
    public void getPriorityTest() {
        assertEquals(UnaryOperator.getOperator("NOOP").getPriority(), 100);
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void NOOPTest() throws UnsupportedFormatException {
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(true));
        UnaryOperator.getOperator("NOOP").apply(rt, null);
    }
    
    @Test
    public void hasKeyTest() {
        assertEquals(true, UnaryOperator.isSupportedOperator("NOOP"));
    }
    
    @Test
    public void hasKey2Test() {
        assertEquals(false, UnaryOperator.isSupportedOperator("NOOP1"));
    }

}
