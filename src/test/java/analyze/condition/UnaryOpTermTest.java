package analyze.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import model.Column;
import model.ColumnType;
import model.DataFieldBoolean;
import model.DataFieldDouble;
import model.DataFieldInt;
import model.DataFieldString;
import model.Reader;
import model.Record;
import model.RecordList;
import model.UnsupportedFormatException;

import org.junit.Test;

import analyze.labeling.Label;
import analyze.labeling.LabelFactory;

public class UnaryOpTermTest {

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
    public void notToStringTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldBoolean(false));
        UnaryOpTerm bn = new UnaryOpTerm(UnaryOperator.getOperator("not"), lt);
        assertEquals("UnaryOp(not, false)", bn.toString());
    }
    
    @Test
    public void negativeTest() throws UnsupportedFormatException {
        LiteralTerm lt = new LiteralTerm(new DataFieldInt(2));
        UnaryOpTerm bn = new UnaryOpTerm(UnaryOperator.getOperator("NEG"), lt);
        assertTrue(bn.evaluate(null).getDoubleValue() == -2.0);
    }
    
    @Test
    public void labelTest() throws UnsupportedFormatException {
    	 Label l = LabelFactory.getInstance().getNewLabel("test");
         Record col = new Record(null);
         col.addLabel(l.getNumber());
         
         LiteralTerm lt = new LiteralTerm(new DataFieldString("test"));
         UnaryOpTerm bn = new UnaryOpTerm(UnaryOperator.getOperator("LABELED"), lt );
         assertEquals(col.containsLabel(l.getNumber()), bn.evaluate(col).getBooleanValue());
    }
    
    @Test
    public void colTest() throws UnsupportedFormatException, IOException {
        Column[] columns = 
            {new Column("column1", ColumnType.STRING), new Column("column2", ColumnType.STRING), new Column("column3", ColumnType.STRING)};
    	 Reader reader = new Reader(columns, ",");
		 RecordList recordList = reader.read("src/main/resources/test_input.txt", false);
         Record rec = recordList.get(0);
        
         LiteralTerm lt = new LiteralTerm(new DataFieldString("column1"));
         UnaryOpTerm bn = new UnaryOpTerm(UnaryOperator.getOperator("COL"), lt );
         assertEquals(rec.get("column1"), bn.evaluate(rec));
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
    
    @Test
    public void maxLenght() {
        assertEquals(4, BinaryOperator.maxOperatorLength());
    }

}
