package controller;


import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import model.SequentialData;
import model.Writer;

/**
 * This class represents a controller for the results tap of the view.
 * @author Matthijs
 *
 */
public class ResultsController extends SubController {
	
	private SequentialData data;

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
		Writer writer = new Writer(",");
		
//		writer.writeData(records, fileName, extension);
	}

	@Override
	public boolean validateInput(boolean showPopup) {
		return false;
	}

	@Override
	public Object getData() {
		// Not used
		return null;
	}

	@Override
	public void setData(Object o) {
		data = (SequentialData) o;
	}
}
