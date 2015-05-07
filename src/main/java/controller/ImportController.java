package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import model.Column;
import model.ColumnCharacteristics;
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
	 * A combobox for choosing the primary key for the current group
	 */
	@FXML
	private ComboBox<String> keyBox;
	
	/**
	 * The current list of column names to choose the primary key
	 */
	private ObservableList<String> keyListItems = FXCollections.observableArrayList();	

	/**
	 * Variables that stores the observables of the group.
	 */
	private ObservableList<GroupListItem> groupList = FXCollections.observableArrayList();	

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
							ObservableValue<? extends Number> observable,
							Number oldValue, Number newValue) {
						
						selectGroup(groupListView.getSelectionModel().getSelectedItem());
					}
				});
		
		// Show the columns of the group when selecting the primary key
		keyBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String oldV, String newV) {
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
				String[] colNames = groupListView.getSelectionModel().getSelectedItem().columnList.stream()
						.map(x -> x.txtField.getText()).collect(Collectors.toList()).toArray(new String[0]);
				String primKey = keyBox.getValue();
				keyListItems.clear();
				keyListItems.add("File name");
				keyListItems.addAll(colNames);
				keyBox.setValue(primKey);
			}
		});
	}
	
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
	 * Opens a file chooser to select files to import and adds them in the file
	 * list
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
		if (files != null)
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

	/**
	 * Converts the list of group, files and columns to an arraylist of Group
	 * objects.
	 * 
	 * @return
	 */
	public ArrayList<Group> getGroups() {
		ArrayList<Group> res = new ArrayList<Group>();
		for (GroupListItem gli : groupList) {

			Column[] colNames = gli.columnList.stream()
					.map(x -> new Column( x.txtField.getText().toString(), ColumnCharacteristics.NONE))
					.collect(Collectors.toList())
					.toArray(new Column[gli.columnList.size()]);
			
			
			int i = 0;
			for(ColumnListItem item: gli.columnList) {
				if(item.date.isSelected())
					colNames[i].setCharactersitic(ColumnCharacteristics.DATE);
				else if(item.comment.isSelected())
					colNames[i].setCharactersitic(ColumnCharacteristics.COMMENT);
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

	/**
	 * This method checks whether the input is valid, and allows to the next tab.
	 * @return		- True if the data is valid.
	 */
	public boolean correctCheck() {
		// To do:
		// - Dialogs instead of prints
		// - Check for duplicate files
		// - Check for duplicate names
		// - Check for invalid names (spaces, etc.)

		// Alert alert = new Alert(AlertType.WARNING);
		// alert.setHeaderText("Oh no, something's wrong!");
		// alert.setHeaderText("Cannot advance to the Linking phase:");

		// Check if there is an empty group name
		for (GroupListItem gli : groupList) {
			if (gli.txtField.getText().equals("")) {
				System.out.println("There is an group with no name");

				return false;
			}
			// Check if every group has files
			if (gli.fileList.isEmpty()) {
				System.out.println("The Group '" + gli.txtField.getText()
						+ "' doesn't contain any files.");
				return false;
			}
			// Check if every group has at least one column
			for (ColumnListItem cli : gli.columnList) {
				if (cli.txtField.getText().equals("")) {
					System.out.println("The Group '" + gli.txtField.getText()
							+ "' contains a column with no name.");
					return false;
				}
			}
			// Check if it has a primary key
			if (gli.primKey == null) {
				System.out.println("The Group '" + gli.txtField.getText()
						+ "' doesn't have a column as primary key.");
				return false;
			}
		}
		return true;
	}

	/**
	 * The list item for the list of imported files.
	 * 
	 * @author Remi
	 *
	 */
	public static class FileListItem extends HBox {
		/**
		 * This variable stores a label
		 */
		private Label label = new Label();
		
		/**
		 * This variable stores the remove button
		 */
		private Button remove;
		
		/**
		 * This variable stores the path to the file
		 */
		private String path;

		FileListItem(String labelText, String path,
				final ObservableList<FileListItem> list) {
			super();
			this.path = path;

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
	 * 
	 * @author Remi
	 *
	 */
	public static class GroupListItem extends HBox {
		/**
		 * The textfield for entering the name of the group list item
		 */
		TextField txtField = new TextField();
		
		/**
		 * The combobox for choosing the delimiter
		 */
		ComboBox<String> box = new ComboBox<String>();
		/**
		 * The button for removing this item
		 */
		Button remove;
		/**
		 * The primary key name for this group
		 */
		String primKey = "File name";

		ObservableList<ColumnListItem> columnList = FXCollections
				.observableArrayList();
		ObservableList<FileListItem> fileList = FXCollections
				.observableArrayList();
		
		/**
		 * A list item for the group list view
		 * @param cboxOptions The list of delimiters
		 * @param list The parent list of list items
		 * @param lv The list view where the list items are shown
		 */
		GroupListItem(final ObservableList<String> cboxOptions,
				final ObservableList<GroupListItem> list,
				final ListView<GroupListItem> lv) {
			super();
			this.setPadding(new Insets(8));
			final GroupListItem self = this;

			columnList.add(new ColumnListItem(columnList, this));

			txtField.setPromptText("Name");
			txtField.setMaxWidth(Double.MAX_VALUE);
			txtField.setPadding(new Insets(4));
			HBox.setHgrow(txtField, Priority.ALWAYS);
			txtField.setOnKeyReleased(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent e) {
					// Focus on next text field when pressing ENTER
					if (e.getCode().equals(KeyCode.ENTER)) {
						// If there is no next field, create one
						if (list.size() - 1 <= list.indexOf(self))
							list.add(new GroupListItem(cboxOptions, list, lv));
						int nextIndex = list.indexOf(self) + 1;
						list.get(nextIndex).txtField.requestFocus();
						lv.getSelectionModel().select(nextIndex);
					}
				}
			});
			// Focus on list item when clicking on text field
			txtField.focusedProperty().addListener(
					new ChangeListener<Boolean>() {
						public void changed(
								ObservableValue<? extends Boolean> arg0,
								Boolean oldV, Boolean newV) {
							if (newV)
								lv.getSelectionModel().select(
										list.indexOf(self));
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
		
		/**
		 * Returns a list of column names
		 * @return
		 */
		public List<String> getColumnNames() {
			return columnList.stream().map(x -> x.txtField.getText()).collect(Collectors.toList());
		}
	}

	/**
	 * The list item for the list of groups
	 * 
	 * @author Remi
	 *
	 */
	public static class ColumnListItem extends HBox {
		TextField txtField = new TextField();
		Button remove;
		
		/**
		 * This variable is used to store the comment button.
		 */
		ToggleButton comment;
		
		/**
		 * This variable is used to store the data select button. 
		 */
		ToggleButton date;

		ColumnListItem(final ObservableList<ColumnListItem> list,
				  final GroupListItem gli) {
			super();
			final ColumnListItem self = this;

			txtField.setPromptText("Name");
			txtField.setMaxWidth(Double.MAX_VALUE);
			txtField.setPadding(new Insets(4));
			HBox.setHgrow(txtField, Priority.ALWAYS);
			txtField.setOnKeyReleased(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent e) {
					// Focus on next text field when pressing ENTER
					if (e.getCode().equals(KeyCode.ENTER)) {
						// If there is no next field, create one
						if (list.size() - 1 <= list.indexOf(self))
							list.add(new ColumnListItem(list, gli));
						list.get(list.indexOf(self) + 1).txtField
								.requestFocus();
					}
				}
			});

			comment = new ToggleButton("Comment");
			date = new ToggleButton("Date");

			// Add button to remove this item from the list
			remove = new Button("x");
			remove.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					if (list.size() > 1)
						list.remove(self);
				}
			});

			this.getChildren().addAll(txtField, comment, date, remove);
		}
	}
}
