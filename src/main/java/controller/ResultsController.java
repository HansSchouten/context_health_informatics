package controller;


import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import model.Column;
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

		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		// To do: Get chosen file name & write
		Writer writer = new Writer(",");
	
		Column[] columns = data.getColumns();
		for (Column c : columns)
			System.out.println("results: " + c.getName());
		
		try {
			String path = file.getCanonicalPath();
			writer.writeData(data, path, "csv", columns, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
