package controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * A list item for selecting an identifier.
 * @author Remi
 *
 */
public class IdentifierListItem extends CustomListItem {

	/**
	 * The parent listview of this list item.
	 */
	@FXML
	private ListView identifierListView;

	/**
	 * This variable contains the label of this listitem.
	 */
	protected Label label;

	/**
	 * This variable contains a checkbox that says whether a row is checked.
	 */
	protected CheckBox check;

	/**
	 * Constructs a identifier list item.
	 * @param par      - listitem that is used.
	 * @param id       - The id of the identifierListItem.
	 */
	public IdentifierListItem(ListView<? extends CustomListItem> par, String id) {
		super(par);

		check = new CheckBox();
		label = new Label(id);

		setupRemove(false);
		this.getChildren().addAll(check, label);
	}

	@Override
	public void select() {
		parent.getSelectionModel().select(parent.getChildrenUnmodifiable().indexOf(this));
	}

	@Override
	public void selectNext() {
		// Not used
	}
}
