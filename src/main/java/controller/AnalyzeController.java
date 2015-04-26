package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;

public class AnalyzeController extends SubController {
	@FXML
	private ProgressIndicator progress;
	@FXML
	private Button analyzeButton;
	
	public AnalyzeController() {}
	
	@FXML
	private void initialize() {
		
	}

	@Override
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void startAnalyzing() {
		progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
	}

}
