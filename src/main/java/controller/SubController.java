package controller;

/**
 * The abstract SubController class contains the elements necessary for the subcontrollers of the main application.
 * @author Remi
 *
 */
public abstract class SubController {
	protected MainApp mainApp;
	
	public abstract void setMainApp(MainApp mainApp);
}
