package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
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
	protected CheckBox cbSort, use;

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
		use = new CheckBox();

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
		setupDragDrop(txtField);
		this.getChildren().addAll(use, txtField, comboBox, remove);
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

	/**
	 * Sets up drag n drop for a node inside this list item.
	 * @param n The node.
	 */
	public void setupDragDrop(Node n) {
		n.setOnDragDetected(e -> {
			Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();

			// Show a snaphot of the list item
			WritableImage snapshot = this.snapshot(new SnapshotParameters(), null);
			db.setDragView(snapshot, e.getX(), e.getY());

			content.putString("" + parent.getItems().indexOf(this));

			db.setContent(content);
			e.consume();
		});

		n.setOnDragOver(e -> {

			if (parent.getItems().contains(e.getGestureSource()) && e.getGestureSource() != this
					&& e.getDragboard().hasString()) {
				e.acceptTransferModes(TransferMode.MOVE);
			}
			e.consume();
		});

		n.setOnDragEntered(e -> {
			int targetIdx = parent.getItems().indexOf(this);
			parent.getItems().get(targetIdx).getStyleClass().add("highlight");
		});

		n.setOnDragExited(e -> {
			int targetIdx = parent.getItems().indexOf(this);
			parent.getItems().get(targetIdx).getStyleClass().remove("highlight");
		});

		n.setOnDragDropped(e -> {
			Dragboard db = e.getDragboard();

			if (db.hasString()) {
				int targetIdx = parent.getItems().indexOf(this);

				// Replace the item of the source with the target
				ColumnListItem item = (ColumnListItem) parent.getItems().get(
						Integer.parseInt(db.getString()));

				parent.getItems().remove(item);
				((ListView<ColumnListItem>) parent).getItems().add(targetIdx, item);
				parent.getSelectionModel().select(targetIdx);
			}

			e.setDropCompleted(true);
			e.consume();
		});
	}
}
