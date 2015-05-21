package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * The list item for the list of groups.
 *
 */
public class ColumnListItem extends CustomListItem {
	/**
	 * The textfield for entering the column name.
	 */
	protected TextField txtField;

	/**
	 * Used to select what kind of data this column contains.
	 */
	protected ComboBox<String> comboBox, secondBox;

	/**
	 * This variable is used to store a checkbox that determines if a
	 * column must be used for sorting.
	 */
	protected CheckBox cbSort;

	/**
	 * The grouplistitem that contains this columnlistitem.
	 */
	protected GroupListItem groupLI;

	/**
	 * Constructs a column list item.
	 * @param par The parent listview.
	 * @param gli The group list item which contains this item.
	 */
	ColumnListItem(ListView<? extends CustomListItem> par, GroupListItem gli) {
		super(par);
		groupLI = gli;

		txtField = createTextField("Name");

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

		setupRemove(true);
		this.getChildren().addAll(txtField, comboBox, remove);
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
				setSecondBox(new String[]{
						"dd-MM-yyyy HH:mm", "dd-MM-yyyy HH:mm:ss",
						"dd/MM/yyyy HH:mm", "dd/MM/yyyy HH:mm:ss",
					 	"Excel epoch"});
				break;
			case "Date":
				setSecondBox(new String[]{"dd/MM/yyyy",	"dd/MM/yy",
						"dd-MM-yyyy", "dd-MM-yy", "yyMMdd", "Excel epoch"});
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
	 * This method adds a second combobox for more options.
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
		HBox.setMargin(cbSort, new Insets(4));

		this.getChildren().add(2, secondBox);
		this.getChildren().add(3, cbSort);
	}

	@Override
	public void select() {
		parent.getSelectionModel().select(parent.getItems().indexOf(this));
		txtField.requestFocus();
	}

	@Override
	public void selectNext() {
		int idx = parent.getItems().indexOf(this);
		if (parent.getItems().size() - 1 <= idx) {
			// If this is the last item, add a new one.
			@SuppressWarnings("unchecked")
			ObservableList<ColumnListItem> list = (ObservableList<ColumnListItem>) parent.getItems();
			list.add(new ColumnListItem(parent, groupLI));
		}
		parent.getItems().get(idx + 1).select();
	}
}
