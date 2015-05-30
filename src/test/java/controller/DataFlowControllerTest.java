package controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataFlowControllerTest {
    @Test
    public void test() {
        ImportController ip = new ImportController();
        assertTrue(ip.getPipelineNumber() == 1);
    }

}
