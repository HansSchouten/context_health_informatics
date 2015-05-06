package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import controller.ImportController.FileListItem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

/**
 * This class represents a controller for the results tap of the view.
 * @author Matthijs
 *
 */
public class ResultsController extends SubController {
	
	/**
	 * This function contstructs a ResultController
	 */
	public ResultsController() {}
	
	@Override
	protected void initialize() {
		
	}
	
	/**
	 * Opens a FileChooser to save the file
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
}
