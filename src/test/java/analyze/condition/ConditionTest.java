package analyze.condition;

import static org.junit.Assert.assertEquals;
import model.Record;
import model.datafield.DataFieldBoolean;
import model.datafield.DataFieldInt;

import org.junit.Test;

import analyze.AnalyzeException;
import analyze.labeling.Label;
import analyze.labeling.LabelFactory;


public class ConditionTest {
    
    @Test (expected=ConditionParseException.class)
    public void emptyTest() throws ConditionParseException {
        String expression = "";
        Condition condition = new Condition(expression);
        assertEquals(false, condition.evaluateWithRecord(null));
    }

    @Test
    public void plusTest() throws ConditionParseException {
        String expression = "(10 + 20) < 10";
        Condition condition = new Condition(expression);
        assertEquals(false, condition.evaluateWithRecord(null));
    }
    
    @Test
    public void condition1Test() throws ConditionParseException {
        String expression = "(10 + 20) > 10";
        Condition condition = new Condition(expression);
        assertEquals(true, condition.evaluateWithRecord(null));
    }
    
    @Test
    public void condition2Test() throws ConditionParseException {
        String expression = "10 >= 10";
        Condition condition = new Condition(expression);
        assertEquals(true, condition.evaluateWithRecord(null));
    }
    
    @Test
    public void condition3Test() throws ConditionParseException {
        String expression = "(true and false)";
        Condition condition = new Condition(expression);
        assertEquals(false, condition.evaluateWithRecord(null));
    }
    
    @Test
    public void condition4Test() throws ConditionParseException {
        String expression = "(true and false) or true";
        Condition condition = new Condition(expression);
        assertEquals(true, condition.evaluateWithRecord(null));
    }
    
    @Test
    public void condition5Test() throws ConditionParseException {
        String expression = "true and false or true";
        Condition condition = new Condition(expression);
        assertEquals(true, condition.evaluateWithRecord(null));
    }
    
    @Test
    public void condition6Test() throws ConditionParseException {
        String expression = "10 + 1 = 11";
        Condition condition = new Condition(expression);
        assertEquals(true, condition.evaluateWithRecord(null));
    }
    
    @Test
    public void condition7Test() throws ConditionParseException {
        String expression = "not (true and false)";
        Condition condition = new Condition(expression);
        assertEquals(true, condition.evaluateWithRecord(null));
    }
    
    @Test
    public void condition8Test() throws ConditionParseException {
        String expression = "10 - -10 = 20";
        Condition condition = new Condition(expression);
        assertEquals(true, condition.evaluateWithRecord(null));
    }
    
    @Test
    public void condition9Test() throws ConditionParseException {
        String expression = "not not (true and false)";
        Condition condition = new Condition(expression);
        assertEquals(false, condition.evaluateWithRecord(null));
    }
    
    @Test
    public void condition10Test() throws ConditionParseException {
        String expression = "(( 10 + 12 ) + 10) = 32";
        Condition condition = new Condition(expression);
        assertEquals(true, condition.evaluateWithRecord(null));
    }
    
    @Test
    public void condition11Test() throws ConditionParseException {
        Record record = new Record(null);
        record.put("hoi", new DataFieldBoolean(true));
        String expression = "COL(hoi)";
        Condition condition = new Condition(expression);
        assertEquals(true, condition.evaluateWithRecord(record));
    }
    
    @Test
    public void condition12Test() throws ConditionParseException {
        Record record = new Record(null);
        record.put("hoi", new DataFieldInt(10));
        String expression = "COL(hoi)  = 10";
        Condition condition = new Condition(expression);
        assertEquals(true, condition.evaluateWithRecord(record));
    }
    
    @Test
    public void condition13Test() throws ConditionParseException {
        Record record = new Record(null);
        record.put("hoi", new DataFieldInt(10));
        String expression = "COL(hoi) + 14  = 24";
        Condition condition = new Condition(expression);
        assertEquals(true, condition.evaluateWithRecord(record));
    }
    
    @Test
    public void condition14Test() throws ConditionParseException {
        Label label = LabelFactory.getInstance().getNewLabel("hoi");
        Record record = new Record(null);
        record.addLabel(label.getNumber());
        String expression = "LABELED(hoi)";
        Condition condition = new Condition(expression);
        assertEquals(true, condition.evaluateWithRecord(record));
    }
    
    @Test
    public void condition16Test() throws ConditionParseException {
        Record record = new Record(null);
        record.put("hoi", new DataFieldInt(10));
        String expression = "COL(hoi) + 10 = 10";
        Condition condition = new Condition(expression);
        assertEquals(false, condition.evaluateWithRecord(record));
    }
    
    @Test
    public void condition15Test() throws ConditionParseException {
        Label label = LabelFactory.getInstance().getNewLabel("hoi5");
        Label label1 = LabelFactory.getInstance().getNewLabel("doei");
        Record record = new Record(null);
        record.addLabel(label.getNumber());
        record.addLabel(label1.getNumber());
        String expression = "LABELED(hoi5) and not not LABELED(doei)";
        Condition condition = new Condition(expression);
        assertEquals(true, condition.evaluateWithRecord(record));
    }
    
    @Test
    public void condition17Test() throws ConditionParseException {
        String expression = "true and true";
        String expression1 = "true AND true";
        Condition condition = new Condition(expression);
        Condition condition1 = new Condition(expression1);
        assertEquals(true, condition.evaluateWithRecord(null));
        assertEquals(true, condition1.evaluateWithRecord(null));
    }
    
    @Test (expected = ConditionParseException.class)
    public void ToManyParenthesesTest() throws ConditionParseException {
        String expression = "((10 + 12)";
        new Condition(expression);
    }
    
    @Test (expected = ConditionParseException.class)
    public void InvalidOutcomeTest() throws ConditionParseException {
        String expression = "(10 + 12)";
        Condition c = new Condition(expression);
        assertEquals(false, c.evaluateWithRecord(null));
    }
    
    @Test
    public void conditionParseExceptionTest() throws ConditionParseException {
        try {
            throw new ConditionParseException("hoi");
        } catch (AnalyzeException e) {
            assertEquals("Condition Parse Error: hoi", e.getMessage());
        }
    }
}
