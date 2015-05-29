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
	
	protected Label label;
	
	protected CheckBox check;

	/**
	 * Constructs a identifier list item.
	 * @param par
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
