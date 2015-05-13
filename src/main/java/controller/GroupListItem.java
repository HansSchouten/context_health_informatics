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
public class GroupListItem extends HBox {
	/**
	 * The textfield for entering the name of the group list item.
	 */
	private TextField txtField = new TextField();

	/**
	 * The combobox for choosing the delimiter.
	 */
	private ComboBox<String> box = new ComboBox<String>();
	/**
	 * The button for removing this item.
	 */
	private Button remove;
	/**
	 * The primary key name for this group.
	 */
	private String primKey = "File name";
	/**
	 * The list of columns for this group.
	 */
	private ObservableList<ColumnListItem> columnList = FXCollections.observableArrayList();
	/**
	 * The list of files for this group.
	 */
	private ObservableList<FileListItem> fileList = FXCollections.observableArrayList();

	/**
	 * A list item for the group list view.
	 * @param cboxOptions The list of delimiters
	 * @param list The parent list of list items
	 * @param lv The list view where the list items are shown
	 */
	GroupListItem(final ObservableList<String> cboxOptions, final ObservableList<GroupListItem> list,
			final ListView<GroupListItem> lv) {
		super();
		this.setPadding(new Insets(8));
		final GroupListItem self = this;

		getColumnList().add(new ColumnListItem(getColumnList(), this));

		getTextField().setPromptText("Name");
		getTextField().setMaxWidth(Double.MAX_VALUE);
		getTextField().setPadding(new Insets(4));
		HBox.setHgrow(getTextField(), Priority.ALWAYS);
		getTextField().setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				// Focus on next text field when pressing ENTER
				if (e.getCode().equals(KeyCode.ENTER)) {
					// If there is no next field, create one
					if (list.size() - 1 <= list.indexOf(self))
						list.add(new GroupListItem(cboxOptions, list, lv));
					int nextIndex = list.indexOf(self) + 1;
					list.get(nextIndex).getTextField().requestFocus();
					lv.getSelectionModel().select(nextIndex);
				}
			}
		});
		// Focus on list item when clicking on text field
		getTextField().focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldV,
					Boolean newV) {
				if (newV)
					lv.getSelectionModel().select(list.indexOf(self));
			}
		});

		getBox().setItems(cboxOptions);
		getBox().setValue(cboxOptions.get(0));

		// Add button to remove this item from the list
		remove = new Button("x");
		remove.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if (list.size() > 1)
					list.remove(self);
			}
		});

		this.getChildren().addAll(getTextField(), getBox(), remove);
	}

	/**
	 * Returns a list of column names.
	 * @return The list of column names.
	 */
	public List<String> getColumnNames() {
		return getColumnList().stream().map(x -> x.getTextField().getText()).collect(Collectors.toList());
	}

	/**
	 * @return the txtField
	 */
	public TextField getTextField() {
		return txtField;
	}

	/**
	 * @return the fileList
	 */
	public ObservableList<FileListItem> getFileList() {
		return fileList;
	}

	/**
	 * @return the columnList
	 */
	public ObservableList<ColumnListItem> getColumnList() {
		return columnList;
	}

	/**
	 * @return The primary key
	 */
	public String getPrimKey() {
		return primKey;
	}

	/**
	 * @param primKey the primary key to set
	 */
	public void setPrimKey(String primKey) {
		this.primKey = primKey;
	}

	/**
	 * @return The combobox for the delimiter
	 */
	public ComboBox<String> getBox() {
		return box;
	}
}