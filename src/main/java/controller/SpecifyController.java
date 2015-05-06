package controller;

/**
 * This method represent a controller for the specify tab of the main view.
 * @author Matthijs
 *
 */
public class SpecifyController extends SubController {

	/**
	 * Construct a SpecifyController
	 */
	public SpecifyController() { }
	
	@Override
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	protected void initialize() {
	}
}
