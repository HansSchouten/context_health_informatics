package controller;


import javafx.fxml.FXML;
import javafx.stage.FileChooser;

/**
 * This class represents a controller for the results tap of the view.
 * @author Matthijs
 *
 */
public class ResultsController extends SubController {

	/**
	 * This function contstructs a ResultController.
	 */
	public ResultsController() { }

	@Override
	protected void initialize() {

	}

	/**
	 * Opens a FileChooser to save the file.
	 */
	@FXML
	public void saveFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file");

		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Text file (*.txt)", "*.txt"),
				new FileChooser.ExtensionFilter("Comma delimited file (*.csv)", "*.csv"));

		fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		// To do: Get chosen file name & write
	}

	@Override
	public boolean validateInput() {
		// TODO Auto-generated method stub
		return false;
	}
}
