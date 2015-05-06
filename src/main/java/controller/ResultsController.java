package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import controller.ImportController.FileListItem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

public class ResultsController extends SubController {
	
	public ResultsController() {}
	
	@FXML
	private void initialize() {
		
	}

	@Override
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void saveFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file");

		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Text file (*.txt)", "*.txt"),
				new FileChooser.ExtensionFilter("Comma delimited file (*.csv)", "*.csv"));

		fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		
		// Get chosen file name & write
		
	}

}
