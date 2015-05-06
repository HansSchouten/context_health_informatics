package controller;

import javafx.fxml.FXML;

/**
 * The abstract SubController class contains the elements necessary 
 * for the subcontrollers of the main application.
 * @author Remi
 *
 */
public abstract class SubController {

	/**
	 * This variable stores the main app of the view.
	 */
	protected MainApp mainApp;

	/**
	 * This method initializes the controller for the view.
	 */
	@FXML
	protected abstract void initialize();
	
	/**
	 * This mehtod sets a reference for the main app in this subcontroller.
	 * @param mainApp		- The main contrainer of the view.
	 */
	public void setMainApp(MainApp ma) {
		mainApp = ma;
	}
}
