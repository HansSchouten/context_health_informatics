package analyze.condition;

import static org.junit.Assert.*;

import model.DataFieldInt;
import model.UnsupportedFormatException;

import org.junit.Test;

public class BinaryOpTermTest {

    @Test
    public void minTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(10));
        BinaryOpTerm bn = new BinaryOpTerm(BinaryOperator.getOperator("-"), lt);
        assertTrue(bn.evaluate(null).getIntegerValue() == -10);
    }

}
