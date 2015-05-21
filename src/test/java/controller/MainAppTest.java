package controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.matcher.base.NodeMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class MainAppTest extends FxRobot  {

	@Before
    public void before() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainApp.class);
    }

	@Test
	public void test() throws Exception {
		assertTrue(true);
	}

	

}
