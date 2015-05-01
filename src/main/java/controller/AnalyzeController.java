package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;

/**
 * This class controls the view of the analyze tab of the program.
 * @author Matthijs
 *
 */
public class AnalyzeController extends SubController {

	/**
	 * This variable shows the progress of the file.
	 */
	@FXML
	private ProgressIndicator progress;
	/**
	 * This variable contains the analyze button.
	 */
	@FXML
	private Button analyzeButton;

	/**
	 * Constructs a new analyse controller.
	 */
	public AnalyzeController() { }

	/**
	 * This function starts analysing the data.
	 */
	@FXML
	public void startAnalyzing() {
		progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub	
	}

}
