package analyze.labeling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.Record;

import org.junit.Test;

import analyze.labeling.Label;

public class LabelTest {

    @Test
    public void labelConstructorTest() {
        Label l = new Label("hoi", 1);
        assertTrue(l.number == 1);
        assertEquals(l.getName(), "hoi");
    }

    @Test
    public void addLabelToRecordTest() {
        Label l = new Label("hoi", 1);
        Record col = new Record(null);
        col.addLabel(l.number);
        col.containsLabel(1);
    }
}
