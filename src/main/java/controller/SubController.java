package controller;

import javafx.fxml.FXML;

/**
 * The abstract SubController class contains the elements necessary.
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
	 * This function sets the mainApp of this controller
	 * @param ma       - Mainapp of this controller     
	 */
    public void setMainApp(MainApp ma) {
        mainApp = ma;
    }
}
