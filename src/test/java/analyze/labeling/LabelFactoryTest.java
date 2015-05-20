package analyze.labeling;

import static org.junit.Assert.*;

import org.junit.Test;

import analyze.labeling.Label;
import analyze.labeling.LabelFactory;

public class LabelFactoryTest {

    @Test
    public void getInstanceTest() {
        assertTrue(LabelFactory.getInstance() == LabelFactory.getInstance());
    }
    
    @Test
    public void getNewLabelTest() {
        Label l = LabelFactory.getInstance().getNewLabel("hoi");
        assertEquals(l.name,  "hoi");
    }
    
    @Test
    public void getNewLabel2Test() {
        Label l = LabelFactory.getInstance().getNewLabel("hoi");
        assertEquals(l.name,  "hoi");
    }
    
    @Test
    public void getLabelAscending2Test() {
        Label l = LabelFactory.getInstance().getNewLabel("hoi");
        Label l2 = LabelFactory.getInstance().getNewLabel("hoi1");
        Label l3 = LabelFactory.getInstance().getNewLabel("hoi2");
        assertTrue(l.number + 1 == l2.number);
        assertTrue(l2.number + 1 == l3.number);
    }

    @Test
    public void getLabelWithSameNameTest() {
        Label l = LabelFactory.getInstance().getNewLabel("hoi");
        Label l2 = LabelFactory.getInstance().getNewLabel("hoi");
        Label l3 = LabelFactory.getInstance().getNewLabel("hoi");
        assertTrue(l.number == l2.number);
        assertTrue(l2.number == l3.number);
    }
    
    @Test
    public void getNumberOfLabelTest(){
        Label l = LabelFactory.getInstance().getNewLabel("string");
        int number = LabelFactory.getInstance().getNumberOfLabel("string");
        assertTrue(l.number == number);
        LabelFactory.getInstance().getNewLabel("string");
        assertTrue(l.number == number);
    }
    
    @Test
    public void getNumberOfLabelNotTest(){
        
        assertTrue(LabelFactory.getInstance().getNumberOfLabel("notused") == -1);
    }
}
