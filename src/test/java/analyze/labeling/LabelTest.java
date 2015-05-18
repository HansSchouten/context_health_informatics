package analyze.labeling;

import static org.junit.Assert.*;
import model.Record;

import org.junit.Test;

import analyze.labeling.Label;

public class LabelTest {

    @Test
    public void labelConstructorTest() {
        Label l = new Label("hoi", 1);
        assertTrue(l.number == 1);
        assertEquals(l.name, "hoi");
    }

    @Test
    public void addLabelToRecordTest() {
        Label l = new Label("hoi", 1);
        Record col = new Record(null);
        col.addLabel(l.number);
        col.containsLabel(1);
    }
    
}
