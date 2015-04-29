package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import model.RowData;

public class ImportController extends SubController {
	@FXML
	private Button addFiles;
	
	@FXML
	private ListView<GroupListItem> groupListView;
	@FXML
	private ListView<ColumnListItem> columnListView;
	@FXML
	private ListView<FileListItem> fileListView;
	
	private ObservableList<GroupListItem> groupList = FXCollections.observableArrayList();
	private ObservableList<String> delimiterStringList = FXCollections.observableArrayList();
	
	
	public ImportController() {}
	
	@FXML
	private void initialize() {
		// Create new toggle group for toggle buttons as primary key indicator
		
		
		// Set the delimiters
		delimiterStringList.add("Comma delimiter");
		delimiterStringList.add("Tab delimiter");
		delimiterStringList.add("Excel?");
		
		// Add initial group and select it
		groupListView.setItems(groupList);
		groupList.add(new GroupListItem(delimiterStringList, groupList, groupListView));

		// Switch to the right files and colums when selecting a group
		groupListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				columnListView.setItems(groupListView.getSelectionModel().getSelectedItem().columnList);
				fileListView.setItems(groupListView.getSelectionModel().getSelectedItem().fileList);
			}
		});
		groupListView.getSelectionModel().select(0);

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
		ObservableList<FileListItem> selected = groupListView.getSelectionModel().getSelectedItem().fileList;
		if (files != null)
			for (File f : files) 
				selected.add(new FileListItem(f.getName(), selected));
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
	}
	
	/**
	 * The list item for the list of imported files.
	 * @author Remi
	 *
	 */
	public static class FileListItem extends HBox {
        Label label = new Label();
        Button remove;
        
        FileListItem(String labelText, final ObservableList<FileListItem> list) {
             super();
             
             label.setText(labelText);
             label.setMaxWidth(Double.MAX_VALUE);
             label.setPadding(new Insets(4));
             HBox.setHgrow(label, Priority.ALWAYS);
             
             // Add button to remove this item from the list
             remove = new Button("x");
             final FileListItem self = this;
             remove.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					list.remove(self);
				}
             });

             this.getChildren().addAll(label, remove);
        }
   }
	/**
	 * The list item for the list of groups
	 * @author Remi
	 *
	 */
	public static class GroupListItem extends HBox {
		TextField txtField = new TextField();
		ComboBox<String> box = new ComboBox<String>();
		Button remove;
		
		ObservableList<ColumnListItem> columnList = FXCollections.observableArrayList();
		ObservableList<FileListItem> fileList = FXCollections.observableArrayList();
		
		private ToggleGroup toggleGroup; 
		
		GroupListItem(final ObservableList<String> cboxOptions, final ObservableList<GroupListItem> list, final ListView lv) {
			super();
			this.setPadding(new Insets(8));
			final GroupListItem self = this;
			
			toggleGroup = new ToggleGroup();
			columnList.add(new ColumnListItem(columnList, toggleGroup));
			
			txtField.setPromptText("Name");
			txtField.setMaxWidth(Double.MAX_VALUE);
			txtField.setPadding(new Insets(4));
			HBox.setHgrow(txtField, Priority.ALWAYS);
			txtField.setOnKeyReleased(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent e) {
					// Add new item to list automatically
					if (!list.get(list.size()-1).txtField.getText().equals(""))
						list.add(new GroupListItem(cboxOptions, list, lv));
					// Focus on next text field when pressing ENTER
					if (e.getCode().equals(KeyCode.ENTER) && list.size() -1 > list.indexOf(self)) {
						int nextIndex = list.indexOf(self) + 1;
						list.get(nextIndex).txtField.requestFocus();
						lv.getSelectionModel().select(nextIndex);
					}
				}
			});
			// Focus on list item when clicking on text field
			txtField.focusedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldV, Boolean newV) {
					if (newV)
						lv.getSelectionModel().select(list.indexOf(self));
				}
			});
			
			box.setItems(cboxOptions);
			box.setValue(cboxOptions.get(0));
			
			// Add button to remove this item from the list
			remove = new Button("x");
			remove.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					if (list.size() > 1)
						list.remove(self);
				}
			});
			
			this.getChildren().addAll(txtField, box, remove);
		}
	}
	/**
	 * The list item for the list of groups
	 * @author Remi
	 *
	 */
	public static class ColumnListItem extends HBox {
		TextField txtField = new TextField();
		ToggleButton key;
		Button remove;
		
		ColumnListItem(final ObservableList<ColumnListItem> list, final ToggleGroup tg) {
			super();
			final ColumnListItem self = this;
			
			txtField.setPromptText("Name");
			txtField.setMaxWidth(Double.MAX_VALUE);
			txtField.setPadding(new Insets(4));
			HBox.setHgrow(txtField, Priority.ALWAYS);
			txtField.setOnKeyReleased(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent e) {
					// Add new item to list automatically
					if (!list.get(list.size()-1).txtField.getText().equals(""))
						list.add(new ColumnListItem(list, tg));
					// Focus on next text field when pressing ENTER
					if (e.getCode().equals(KeyCode.ENTER) && list.size() -1 > list.indexOf(self))
						list.get(list.indexOf(self) + 1).txtField.requestFocus();
					}
			});
			
			key = new ToggleButton("Prim. Key");
			key.setToggleGroup(tg);
			
			// Add button to remove this item from the list
			remove = new Button("x");
			remove.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					if (list.size() > 1)
						list.remove(self);
				}
			});
			
			this.getChildren().addAll(txtField, key, remove);
		}
	}
}
