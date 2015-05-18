package analyze.condition;

import static org.junit.Assert.*;
import model.DataFieldBoolean;
import model.DataFieldInt;
import model.UnsupportedFormatException;

import org.junit.Test;

public class BinaryOpTermTest {

    @Test
    public void minTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldInt(20));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("-"), lt, rt);
        assertTrue(bn.evaluate(null).getDoubleValue() == -10);
    }
    
    @Test
    public void plusTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldInt(20));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("+"), lt, rt);
        assertTrue(bn.evaluate(null).getDoubleValue() == 30);
    }
    
    @Test
    public void andTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldBoolean(true));
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(true));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("and"), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == true);
    }
    
    @Test
    public void andWrongTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldBoolean(true));
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(false));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("and"), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test
    public void orTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldBoolean(true));
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(false));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("or"), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == true);
    }
    
    @Test
    public void orWrongTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldBoolean(false));
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(false));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("or"), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test (expected = UnsupportedFormatException.class)
    public void orErrorTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldBoolean(false));
        LiteralTerm rt = new LiteralTerm(new DataFieldInt(10));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("or"), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test (expected = UnsupportedFormatException.class)
    public void andErrorTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(false));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("and"), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }

}
