package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class DataFlowControllerTest {
    
    ImportController ic;
    SelectController sec;
    SpecifyController spc;
    ResultsController rc;
    
    
    @Before
    public void setup() {
        ic = new ImportController();
        sec = new SelectController();
        spc = new SpecifyController();
        rc = new ResultsController();
    }
    
    @Test
    public void dataflowControllerConstructorTest() throws ControllerSetupException {
        ArrayList<SubController> sc = new ArrayList<SubController>();
        sc.add(ic);
        sc.add(sec);
        sc.add(spc);
        sc.add(rc);
        DataFlowController dfc = new DataFlowController(sc);
        assertTrue(ic == dfc.importcontroller);
        assertTrue(sec == dfc.selectcontroller);
        assertTrue(spc == dfc.specifycontroller);
        assertTrue(rc == dfc.resultcontroller);
    }

    @Test (expected = ControllerSetupException.class)
    public void dataflowControllerConstructor1Test() throws ControllerSetupException {
        ArrayList<SubController> sc = new ArrayList<SubController>();
        sc.add(sec);
        sc.add(spc);
        sc.add(rc);
        new DataFlowController(sc);
    }
    
    @Test (expected = ControllerSetupException.class)
    public void dataflowControllerConstructor2Test() throws ControllerSetupException {
        ArrayList<SubController> sc = new ArrayList<SubController>();
        sc.add(ic);
        sc.add(sec);
        sc.add(spc);
        sc.add(new TestController());
        new DataFlowController(sc);
    }
    
    @Test
    public void getImportControllerTest() throws ControllerSetupException {
        ArrayList<SubController> sc = new ArrayList<SubController>();
        sc.add(ic);
        sc.add(sec);
        sc.add(spc);
        sc.add(rc);
        DataFlowController dfc = new DataFlowController(sc);
        assertEquals(null, dfc.getImportController(new TestController()));
    }
    
    @Test
    public void getImportController1Test() throws ControllerSetupException {
        ArrayList<SubController> sc = new ArrayList<SubController>();
        sc.add(ic);
        sc.add(sec);
        sc.add(spc);
        sc.add(rc);
        DataFlowController dfc = new DataFlowController(sc);
        assertEquals(ic, dfc.getImportController(sec));
    }
    
    @Test
    public void getSelectControllerTest() throws ControllerSetupException {
        ArrayList<SubController> sc = new ArrayList<SubController>();
        sc.add(ic);
        sc.add(sec);
        sc.add(spc);
        sc.add(rc);
        DataFlowController dfc = new DataFlowController(sc);
        assertEquals(sec, dfc.getSelectController(spc));
    }
    
    @Test
    public void getSelectController1Test() throws ControllerSetupException {
        ArrayList<SubController> sc = new ArrayList<SubController>();
        sc.add(ic);
        sc.add(sec);
        sc.add(spc);
        sc.add(rc);
        DataFlowController dfc = new DataFlowController(sc);
        assertEquals(null, dfc.getSelectController(ic));
    }
    
    @Test
    public void getSpecifyControllerTest() throws ControllerSetupException {
        ArrayList<SubController> sc = new ArrayList<SubController>();
        sc.add(ic);
        sc.add(sec);
        sc.add(spc);
        sc.add(rc);
        DataFlowController dfc = new DataFlowController(sc);
        assertEquals(spc, dfc.getSpecifyController(rc));
    }
    
    @Test
    public void getSpecifyController1Test() throws ControllerSetupException {
        ArrayList<SubController> sc = new ArrayList<SubController>();
        sc.add(ic);
        sc.add(sec);
        sc.add(spc);
        sc.add(rc);
        DataFlowController dfc = new DataFlowController(sc);
        assertEquals(null, dfc.getSpecifyController(sec));
    }
    
    @Test
    public void getResultsControllerTest() throws ControllerSetupException {
        ArrayList<SubController> sc = new ArrayList<SubController>();
        sc.add(ic);
        sc.add(sec);
        sc.add(spc);
        sc.add(rc);
        DataFlowController dfc = new DataFlowController(sc);
        TestController tc = new TestController();
        tc.pipelineNumber = 5;
        assertEquals(rc, dfc.getResultController(tc));
    }
    
    @Test
    public void getResultsController1Test() throws ControllerSetupException {
        ArrayList<SubController> sc = new ArrayList<SubController>();
        sc.add(ic);
        sc.add(sec);
        sc.add(spc);
        sc.add(rc);
        DataFlowController dfc = new DataFlowController(sc);
        assertEquals(null, dfc.getResultController(spc));
    }
}
