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
	 * This function sets the mainApp of this controller.
	 * @param ma       - Mainapp of this controller
	 */
    public void setMainApp(MainApp ma) {
        mainApp = ma;
    }

    /**
     * Checks if the user input is valid.
     * It should notify the user what is wrong with some sort of dialog.
     * @param showPopup Whether a popup notification should be shown if something is invalid.
     * @return Whether the input is valid.
     */
    public abstract boolean validateInput(boolean showPopup);

    /**
     * Gets the data.
     * @return The data.
     */
    public abstract Object getData();

    /**
     * Sets the data.
     * @param o The data.
     */
    public abstract void setData(Object o);
}
