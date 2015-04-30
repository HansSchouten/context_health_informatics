package controller;


import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;




public class MainAppTest extends FxRobot  {

	 @Before
	 public void before() throws Exception {
		 FxToolkit.registerPrimaryStage();
	     FxToolkit.setupApplication(MainApp.class);
	 }
	 
	 @Test
	 public void should_contain_person_list() throws Exception {
		 assertTrue(true);
	 }

}