package controller;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;


/**
 * The list item for the list of groups.
 * @author Remi
 *
 */
public class GroupListItem extends CustomListItem {
	/**
	 * The textfield for entering the name of the group list item.
	 */
	protected TextField txtField;

	/**
	 * The combobox for choosing the delimiter.
	 */
	protected ComboBox<String> box = new ComboBox<String>();

	/**
	 * The primary key name for this group.
	 */
	protected String primKey = "File name";
	/**
	 * The list of columns for this group.
	 */
	protected ObservableList<ColumnListItem> columnList = FXCollections.observableArrayList();
	/**
	 * The list of files for this group.
	 */
	protected ObservableList<FileListItem> fileList = FXCollections.observableArrayList();

	/**
	 * The listviews containing the data and information for this group.
	 */
	protected ListView<CustomListItem> columnListView, fileListView;
	/**
	 * A list item for the group list view.
	 * @param cboxOptions The list of delimiters
	 * @param list The parent list of list items
	 * @param lv The list view where the list items are shown
	 */
	public GroupListItem(ListView<CustomListItem> par, ListView<CustomListItem> files,
			ListView<CustomListItem> columns, ObservableList<String> cboxOptions) {
		super(par);

		this.setPadding(new Insets(8));

		fileListView = files;
		columnListView = columns;

		columnList.add(new ColumnListItem(columnListView, this));

		txtField = createTextField("Name");

		box.setItems(cboxOptions);
		box.setValue(cboxOptions.get(0));

		setupRemove(true);
		this.getChildren().addAll(txtField, box, remove);
	}

	/**
	 * Returns a list of column names.
	 * @return The list of column names.
	 */
	public List<String> getColumnNames() {
		return columnList.stream().map(x -> x.txtField.getText()).collect(Collectors.toList());
	}

	@Override
	public void select() {
		parent.getSelectionModel().select(this);
		txtField.requestFocus();
	}

	@Override
	public void selectNext() {
		if (parent.getItems().size() <= parent.getItems().indexOf(this)) {
			parent.getItems().add(new GroupListItem(parent, fileListView, columnListView, box.getItems()));
		}
		int nextIndex = parent.getItems().indexOf(this) + 1;
		parent.getItems().get(nextIndex).select();
	}
}
