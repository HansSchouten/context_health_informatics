package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * The list item for the list of groups.
 *
 */
public class ColumnListItem extends HBox {
	/**
	 * The textfield for entering the column name.
	 */
	protected TextField txtField = new TextField();
	/**
	 * The button for removing this item.
	 */
	private Button remove;

	/**
	 * This variable is used to store the combobox for the kind of data.
	 */
	protected ComboBox<String> comboBox;

	/**
	 * This variable is used to store a secondcombobox that can be used
	 * to get extra options.
	 */
	protected ComboBox<String> secondBox;

	/**
	 * This variable is used to store a checkbox that determines if a
	 * column must be used for sortin.
	 */
	protected CheckBox cbSort;

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
			@Override
			public void handle(KeyEvent e) {
				// Focus on next text field when pressing ENTER
				if (e.getCode().equals(KeyCode.ENTER)) {
					// If there is no next field, create one
					if (list.size() - 1 <= list.indexOf(self)) {
						list.add(new ColumnListItem(list, gli));
					}
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

		comboBox = new ComboBox<String>(options);
		comboBox.setValue(options.get(0));
		comboBox.setOnAction((event) -> onChange(event));

		// Add button to remove this item from the list
		remove = new Button("x");
		remove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (list.size() > 1) {
					list.remove(self);
				}
			}
		});
		this.getChildren().addAll(comboBox, txtField, remove);
	}

	/**
	 * Method handles an event on combobox.
	 * @param event the event that was fired
	 */
	private void onChange(ActionEvent event) {
		deleteExtraOptions();
		// Determine current state of combobox
		switch (comboBox.getSelectionModel().getSelectedItem().toString()) {
			case "Date/Time":
				setSecondBox(new String[]{"dd-MM-yyyy  HH:mm:ss", "Excel epoch"});
				break;
			case "Date":
				setSecondBox(new String[]{"Excel epoch", "yyMMdd"});
				break;
			case "Time":
				setSecondBox(new String[]{"HH:mm", "HHmm"});
				break;
			default:
				break;
		}
	}

	/**
	 * This method deletes extra optionfields.
	 */
	private void deleteExtraOptions() {
		if (secondBox != null) {
			this.getChildren().remove(secondBox);
			secondBox = null;
		}
		if (cbSort != null) {
			this.getChildren().remove(cbSort);
			cbSort = null;
		}
	}
	/**
	 * This method add a secondbox after.
	 * @param options The list with all options
	 */
	private void setSecondBox(String[] options) {
		ObservableList<String> oOptions = FXCollections.observableArrayList(options);
		secondBox = new ComboBox<String>(oOptions);
		secondBox.setValue(options[0]);

		// This can be enhanced, maybe we can add functionality that we make a button unclickable
		// if there is already another field sort pressed.
		cbSort = new CheckBox();
		cbSort.setText("Sort");

		this.getChildren().add(1, secondBox);
		this.getChildren().add(1, cbSort);
	}
}
