package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import model.RowData;

public class ImportController extends SubController {
	@FXML
	private Button addFiles;
	@FXML
	private ListView<ImportFileListItem> fileListView;
	@FXML
	private TableView previewTable;
	
	private ObservableList<ImportFileListItem> fileList = FXCollections.observableArrayList();
	private ObservableList<String> delimiterStringList = FXCollections.observableArrayList();
	
	public ImportController() {}
	
	@FXML
	private void initialize() {
		fileListView.setItems(fileList);
		
		delimiterStringList.add("Comma delimiter");
		delimiterStringList.add("Tab delimiter");
		delimiterStringList.add("Excel?");
	}
	
	public ArrayList<RowData> parseFile(String path) {
		// <parse a file>
		return null;
	}
	
	/**
	 * Opens a file chooser to select files to import and adds them in the file list
	 */
	@FXML
	private void selectFiles() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import files");

		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All files (*.*)", "*.*"),
				new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"),
				new FileChooser.ExtensionFilter("Comma delimited files (*.csv)", "*.csv"),
				new FileChooser.ExtensionFilter("Old Excel files (*.xls)", "*.xls"),
				new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx"));
		
		List<File> files = fileChooser.showOpenMultipleDialog(mainApp.getPrimaryStage());
		
		// To do: Check if file is already added
		if (files != null)
			for (File f : files) 
				fileList.add(new ImportFileListItem(f.getName(), delimiterStringList, fileList));
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
	}
	
	/**
	 * The list item for the list of imported files.
	 * @author Remi
	 *
	 */
	public static class ImportFileListItem extends HBox {
        Label label = new Label();
        ComboBox<String> box = new ComboBox<String>();
        Button remove;
        
        ImportFileListItem(String labelText, ObservableList<String> cboxOptions, final ObservableList<ImportFileListItem> list) {
             super();
             
             label.setText(labelText);
             label.setMaxWidth(Double.MAX_VALUE);
             label.setPadding(new Insets(4));
             HBox.setHgrow(label, Priority.ALWAYS);

             box.setItems(cboxOptions);
             box.setValue(cboxOptions.get(0));
             
             // Add button to remove this item from the list
             remove = new Button("x");
             final ImportFileListItem self = this;
             remove.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					list.remove(self);
				}
             });

             this.getChildren().addAll(label, box, remove);
        }
   }
}
