package analyze;

import static org.junit.Assert.*;

import org.junit.Test;

public class LabelTest {

    @Test
    public void labelConstructorTest() {
        Label l = new Label("hoi", 1);
        assertTrue(l.number == 1);
        assertEquals(l.name, "hoi");
    }

}
