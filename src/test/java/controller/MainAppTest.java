package controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import static org.testfx.api.FxAssert.verifyThat;

public class MainAppTest extends FxRobot  {

	@Before
    public void before() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainApp.class);
    }

	@Test
	public void testSelectingOtherTab() throws Exception {
		clickOn("2. Select");
	}
	
	@After
	public void after() throws Exception {
	    FxToolkit.cleanupStages();
	    FxToolkit.hideStage();
	}


}
