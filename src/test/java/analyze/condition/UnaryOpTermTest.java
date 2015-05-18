package analyze.condition;

import static org.junit.Assert.*;
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

}
