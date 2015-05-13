package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class ColumnListItem extends HBox {
	/**
	 * The textfield for entering the column name.
	 */
	private TextField txtField = new TextField();
	/**
	 * The button for removing this item.
	 */
	private Button remove;

	/**
	 * This variable is used to store the combobox for the kind of data.
	 */
	private ComboBox<String> comboBox;

	/**
	 * Constructs a column list item.
	 * @param list The parent list
	 * @param gli The group list item which contains this item
	 */
	ColumnListItem(final ObservableList<ColumnListItem> list, final GroupListItem gli) {
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
					list.get(list.indexOf(self) + 1).txtField.requestFocus();
				}
			}
		});

		ObservableList<String> options =
			    FXCollections.observableArrayList(
			        "String",
			        "Int",
			        "Double",
			        "Time",
			        "Date",
			        "Date/Time",
			        "Comment"
			    );

		setComboBox(new ComboBox<String>(options));
		getComboBox().setValue("String");

		// Add button to remove this item from the list
		remove = new Button("x");
		remove.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if (list.size() > 1)
					list.remove(self);
			}
		});

		this.getChildren().addAll(txtField, getComboBox(), remove);
	}

	/**
	 * Returns the textfield.
	 * @return The textfield.
	 */
	public TextField getTextField() {
		return txtField;
	}

	/**
	 * @return the comboBox
	 */
	public ComboBox<String> getComboBox() {
		return comboBox;
	}

	/**
	 * @param comboBox the comboBox to set
	 */
	public void setComboBox(ComboBox<String> comboBox) {
		this.comboBox = comboBox;
	}
}
