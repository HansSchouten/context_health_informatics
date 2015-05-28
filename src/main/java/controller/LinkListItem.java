package controller;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.Group;

/**
 * This class represents a link list item.
 * It contains comboboxes to select the group you want to link.
 * @author Matthijs
 *
 */
public class LinkListItem extends CustomListItem {
	/**
	 * A combobox for selecting a group.
	 */
	private ComboBox<String> groupCbox1, groupCbox2;

	/**
	 * The groups that can be selected.
	 */
	private ObservableList<Group> groups;

	/**
	 * Constructs a new link list item.
	 * @param par The parent listview.
	 * @param grps The groups that can be selected.
	 */
	public LinkListItem(ListView<? extends CustomListItem> par, ObservableList<Group> grps) {
		super(par);
		groups = grps;

		// Create a list of the group names and column names
		ObservableList<String> groupNames = FXCollections.observableArrayList();
		groupNames.addAll(groups.stream().map(x -> x.getName()).collect(Collectors.toList()));

		groupCbox1 = setupComboBox(groupNames, "Group 1");
		groupCbox2 = setupComboBox(groupNames, "Group 2");

		Separator sep = new Separator(Orientation.VERTICAL);
		sep.setPadding(new Insets(2));

		setupRemove(false);
		this.getChildren().addAll(groupCbox1, sep, groupCbox2, remove);
	}

	/**
	 * This method creates a combobox in the list item.
	 * @param l			- Observables of the combobox
	 * @param prompt	- String containing initial text
	 * @return			- Combobox
	 */
	private ComboBox<String> setupComboBox(ObservableList<String> l, String prompt) {
		ComboBox<String> cb = new ComboBox<String>();
		cb.setItems(l);
		HBox.setHgrow(cb, Priority.ALWAYS);
		cb.setMaxWidth(375);
		cb.setPromptText(prompt);
		cb.setEditable(true);
		return cb;
	}

	@Override
	public void select() {
		parent.getSelectionModel().select(parent.getItems().indexOf(this));
	}

	@Override
	public void selectNext() {
		// Not used
	}
}
