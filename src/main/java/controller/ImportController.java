package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.MainApp.NotificationStyle;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import model.Column;
import model.ColumnType;
import model.DateColumn;
import model.Group;

/**
 * This class controls the view of the import tab of the program.
 * @author Matthijs
 *
 */
public class ImportController extends SubController {
	/**
	 * Variable that stores the addfiles button.
	 */
	@FXML
	private Button addFiles;

	/**
	 * Variable that stores the listview.
	 */
	@FXML
	private ListView<GroupListItem> groupListView;

	/**
	 * Variable that stores the columview.
	 */
	@FXML
	private ListView<ColumnListItem> columnListView;

	/**
	 * Variable that stores the file liste view.
	 */
	@FXML
	private ListView<FileListItem> fileListView;

	/**
	 * A combobox for choosing the primary key for the current group.
	 */
	@FXML
	private ComboBox<String> keyBox;

	/**
	 * The current list of column names to choose the primary key.
	 */
	private ObservableList<String> keyListItems =
	        FXCollections.observableArrayList();

	/**
	 * Variables that stores the observables of the group.
	 */
	private ObservableList<GroupListItem> groupList =
	        FXCollections.observableArrayList();

	/**
	 * 	Variables that stores the obeservers for the string delimiters.
	 */
	private ObservableList<String> delimiterStringList = FXCollections
			.observableArrayList();

	/**
	 * This function constructs an import controller.
	 */
	public ImportController() { }

	/**
	 * This method initializes the GUI.
	 */
	@Override
	protected void initialize() {
		// Set the delimiters
		delimiterStringList.add("Comma delimiter");
		delimiterStringList.add("Tab delimiter");
		delimiterStringList.add("Excel?");

		// Add initial group and select it
		groupListView.setItems(groupList);
		addGroupListItem();

		// Switch to the right files and colums when selecting a group
		groupListView.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Number>() {
					public void changed(
							final ObservableValue<? extends Number> observable,
							final Number oldValue, final Number newValue) {

						selectGroup(groupListView.getSelectionModel().getSelectedItem());
					}
				});

		// Show the columns of the group when selecting the primary key
		keyBox.getSelectionModel().selectedItemProperty().addListener(
		        new ChangeListener<String>() {
        			@Override
        			public void changed(final ObservableValue<? extends String> arg0,
        					final String oldV, final String newV) {
        				groupListView.getSelectionModel().getSelectedItem().primKey = newV;
        			}
		});

		keyBox.setVisibleRowCount(10);
		keyBox.setItems(keyListItems);
		keyBox.setValue("File name");
		keyBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				// Convert columns to list of strings
				String[] colNames = groupListView.getSelectionModel()
				        .getSelectedItem().columnList.stream()
						.map(x -> x.txtField.getText()).collect(Collectors.toList())
						.toArray(new String[0]);
				String primKey = keyBox.getValue();
				keyListItems.clear();
				keyListItems.add("File name");
				keyListItems.addAll(colNames);
				keyBox.setValue(primKey);
			}
		});
	}

	/**
	 * Selects a group in the GroupListItemView and shows its files and columns.
	 * @param gli The group you want to select
	 */
	private void selectGroup(GroupListItem gli) {
		// Select group
		groupListView.getSelectionModel().select(gli);
		// Show its columns and files
		columnListView.setItems(gli.columnList);
		fileListView.setItems(gli.fileList);
		keyBox.setValue(gli.primKey);
	}

	/**
	 * This method adds a group list item to the the group list view.
	 */
	@FXML
	public void addGroupListItem() {
		GroupListItem gli = new GroupListItem(delimiterStringList, groupList, groupListView);
		groupList.add(gli);
		selectGroup(gli);
	}

	/**
	 * This method adds a column to the column list view.
	 */
	@FXML
	public void addColumnListItem() {
		GroupListItem gli = groupListView.getSelectionModel().getSelectedItem();
		gli.columnList.add(new ColumnListItem(gli.columnList, gli));
	}

	/**
	 * Opens file chooser and add selected files to file list.
	 */
	@FXML
	private void selectFiles() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import files");

		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All files (*.*)", "*.*"),
				new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"),
				new FileChooser.ExtensionFilter(
						"Comma delimited files (*.csv)", "*.csv"),
				new FileChooser.ExtensionFilter("Old Excel files (*.xls)",
						"*.xls"),
				new FileChooser.ExtensionFilter("Excel files (*.xlsx)",
						"*.xlsx"));

		List<File> files = fileChooser.showOpenMultipleDialog(mainApp
				.getPrimaryStage());

		// To do: Check if file is already added
		ObservableList<FileListItem> selected = groupListView
				.getSelectionModel().getSelectedItem().fileList;
		if (files != null) {
			for (File f : files) {
				// Get canonical path to file
				String path = "Path not found";
				try {
					path = f.getCanonicalPath();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Add to view
				selected.add(new FileListItem(f.getName(), path, selected));

			}
		}
	}

	/**
	 * Converts the list of group, files and columns to an arraylist of Groups.
	 * @return     - All the groups that are made.
	 */
	public ArrayList<Group> getGroups() {
		ArrayList<Group> res = new ArrayList<Group>();
		for (GroupListItem gli : groupList) {

			Column[] colNames = gli.columnList.stream()
					.map(x -> new Column(x.txtField.getText().toString()))
					.collect(Collectors.toList())
					.toArray(new Column[gli.columnList.size()]);

			int i = 0;
			for (ColumnListItem item: gli.columnList) {
				switch (item.comboBox.getValue()) {
					case "String":
						break;
					case "Int":
						colNames[i].setType(ColumnType.INT);
						break;
					case "Double":
						colNames[i].setType(ColumnType.DOUBLE);
						break;
					// quick fix, maybe we will refactor the whole code
					case "Time":
						colNames[i] = new DateColumn(colNames[i].getName(),
								item.secondBox.getValue(), item.cbSort.isSelected());
						colNames[i].setType(ColumnType.TIME);
						break;
					case "Date":
						colNames[i] = new DateColumn(colNames[i].getName(),
								item.secondBox.getValue(), item.cbSort.isSelected());
						colNames[i].setType(ColumnType.DATE);
						break;
					case "Date/Time":
						colNames[i] = new DateColumn(colNames[i].getName(),
								item.secondBox.getValue(), item.cbSort.isSelected());
						colNames[i].setType(ColumnType.DATEandTIME);
						break;
					case "Comment":
						colNames[i].setType(ColumnType.COMMENT);
						break;
					default:
					    colNames[i].setType(ColumnType.STRING);
				}
				i++;
			}

			Group g = new Group(gli.txtField.getText(), gli.box
					.getSelectionModel().getSelectedItem(), colNames,
					gli.primKey);

			for (FileListItem fli : gli.fileList) {
				try {
					g.addFile(fli.path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			res.add(g);
		}
		return res;
	}

	@Override
	public boolean validateInput(boolean showPopup) {
		// To do:
		// - Dialogs instead of prints
		// - Check for duplicate files
		// - Check for duplicate names
		// - Check for invalid names (spaces, etc.)

		for (GroupListItem gli : groupList) {
			// Check if there is an empty group name
			if (gli.txtField.getText().equals("")) {
				if (showPopup) {
					mainApp.showNotification("There is a group with no name.",
							NotificationStyle.WARNING);
				}
				return false;
			}
			// Check if every group has files
			if (gli.fileList.isEmpty()) {
				if (showPopup) {
					mainApp.showNotification("The Group '" + gli.txtField.getText()
							+ "' doesn't contain any files.", NotificationStyle.WARNING);
				}
				return false;
			}
			// Check if every group has at least one column
			for (ColumnListItem cli : gli.columnList) {
				if (cli.txtField.getText().equals("")) {
					if (showPopup) {
						mainApp.showNotification("The Group '" + gli.txtField.getText()
						+ "' contains a column with no name.", NotificationStyle.WARNING);
					}
					return false;
				}
			}
		}
		return true;
	}


}
