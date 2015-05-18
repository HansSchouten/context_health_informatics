package analyze.condition;

import static org.junit.Assert.*;
import model.DataFieldBoolean;
import model.DataFieldDouble;
import model.DataFieldInt;
import model.DataFieldString;
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
    
    @Test
    public void equalTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldInt(10));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == true);
    }
    
    @Test
    public void equal1Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldInt(11));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test
    public void equal2Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldString("hoi"));
        LiteralTerm rt = new LiteralTerm(new DataFieldString("hoi"));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == true);
    }
    
    @Test
    public void equal3Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldString("hoi"));
        LiteralTerm rt = new LiteralTerm(new DataFieldString("hoi1"));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }

    @Test
    public void equal4Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldBoolean(true));
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(false));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test
    public void equal5Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldBoolean(true));
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(true));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == true);
    }
    
    @Test
    public void beqTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(12));
        LiteralTerm rt = new LiteralTerm(new DataFieldInt(11));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator(">="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == true);
    }
    
    @Test
    public void beq1Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldDouble(11.0));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator(">="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test (expected = UnsupportedFormatException.class)
    public void beq2Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(true));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator(">="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test
    public void seqTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(12));
        LiteralTerm rt = new LiteralTerm(new DataFieldInt(11));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("<="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test
    public void seq1Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldDouble(11.0));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("<="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == true);
    }
    
    @Test (expected = UnsupportedFormatException.class)
    public void seq2Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(true));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("<="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test
    public void smallerTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(12));
        LiteralTerm rt = new LiteralTerm(new DataFieldInt(11));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("<"), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test
    public void smaller1Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldDouble(11.0));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("<"), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == true);
    }
    
    @Test (expected = UnsupportedFormatException.class)
    public void smaller2Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(true));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("<"), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test
    public void biggerTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(12));
        LiteralTerm rt = new LiteralTerm(new DataFieldInt(11));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator(">"), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == true);
    }
    
    @Test
    public void bigger1Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldDouble(11.0));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator(">"), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test (expected = UnsupportedFormatException.class)
    public void bigger2Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(true));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator(">"), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test
    public void neqTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(12));
        LiteralTerm rt = new LiteralTerm(new DataFieldInt(11));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("!="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == true);
    }
    
    @Test
    public void neq1Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(11));
        LiteralTerm rt = new LiteralTerm(new DataFieldDouble(11.0));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("!="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == false);
    }
    
    @Test
    public void neq2Test() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(true));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("!="), lt, rt);
        assertTrue(bn.evaluate(null).getBooleanValue() == true);
    }
    
    @Test
    public void getPriorityTest() {
        assertEquals(BinaryOperator.getOperator("!=").getPriority(), 10);
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void NOOPTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        LiteralTerm rt = new LiteralTerm(new DataFieldBoolean(true));
        BinaryOperator.getOperator("NOOP").apply(lt, rt, null);
    }
    
    @Test
    public void hasKeyTest() {
        assertEquals(true, BinaryOperator.isSupportedOperator("NOOP"));
    }
    
    @Test
    public void hasKey2Test() {
        assertEquals(false, BinaryOperator.isSupportedOperator("NOOP1"));
    }
}
