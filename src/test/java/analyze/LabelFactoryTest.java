package analyze;

import static org.junit.Assert.*;

import org.junit.Test;

public class LabelFactoryTest {

    @Test
    public void getInstanceTest() {
        assertTrue(LabelFactory.getInstance() == LabelFactory.getInstance());
    }
    
    @Test
    public void getNewLabel() {
        Label l = LabelFactory.getInstance().getNewLabel("hoi");
        assertEquals(l.name,  "hoi");
    }
    
    @Test
    public void getNewLabel2() {
        Label l = LabelFactory.getInstance().getNewLabel("hoi");
        assertEquals(l.name,  "hoi");
    }
    
    @Test
    public void getLabelAscending2() {
        Label l = LabelFactory.getInstance().getNewLabel("hoi");
        Label l2 = LabelFactory.getInstance().getNewLabel("hoi1");
        Label l3 = LabelFactory.getInstance().getNewLabel("hoi2");
        assertTrue(l.number + 1 == l2.number);
        assertTrue(l2.number + 1 == l3.number);
    }

    @Test
    public void getLabelWithSameName() {
        Label l = LabelFactory.getInstance().getNewLabel("hoi");
        Label l2 = LabelFactory.getInstance().getNewLabel("hoi");
        Label l3 = LabelFactory.getInstance().getNewLabel("hoi");
        assertTrue(l.number == l2.number);
        assertTrue(l2.number == l3.number);
    }

}
