package model;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;

import analyze.labeling.Label;
import analyze.labeling.LabelFactory;

public class RecordTest {
    
    @Test
    public void compareToTest() throws ParseException {
        Record c1 = new Record(
                DateUtils.parseDate(
                        "1995/08/04",
                        "yyyy/mm/DD"));
        Record c2 = new Record(
                DateUtils.parseDate(
                        "1995/08/05",
                        "yyyy/mm/DD"));
        assertEquals(c1.compareTo(c2), -1);
    }
    
    @Test
    public void compareToTest2() throws ParseException {
        Record c1 = new Record(DateUtils.parseDate("1995/08/05", "yyyy/mm/DD"));
        Record c2 = new Record(DateUtils.parseDate("1995/08/04", "yyyy/mm/DD"));
        assertEquals(c1.compareTo(c2), 1);
    }
    
    @Test
    public void printLabelTest() throws ParseException {
        Record c1 = new Record(DateUtils.parseDate("1995/08/05", "yyyy/mm/DD"));
        Label lbl = LabelFactory.getInstance().getNewLabel("hoi");
        c1.addLabel(lbl.getNumber());
        assertEquals("hoi", c1.printLabels(","));
    }
    
    @Test
    public void printLabel1Test() throws ParseException {
        Record c1 = new Record(DateUtils.parseDate("1995/08/05", "yyyy/mm/DD"));
        Label lbl = LabelFactory.getInstance().getNewLabel("hoi");
        Label lbl1 = LabelFactory.getInstance().getNewLabel("hoi1");
        c1.addLabel(lbl.getNumber());
        c1.addLabel(lbl1.getNumber());
        assertEquals("hoi,hoi1", c1.printLabels(","));
    }

}
