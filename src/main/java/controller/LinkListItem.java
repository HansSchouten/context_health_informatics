package controller;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class LinkListItem extends HBox {
	/**
	 * A combobox for selecting a group.
	 */
	private ComboBox<String> groupCbox1, groupCbox2;
	/**
	 * The button for removing this item.
	 */
	private Button remove;

	/**
	 * Constructs a new link list item.
	 * @param list The parent list.
	 * @param groups The groups that can be selected.
	 */
	LinkListItem(final ObservableList<LinkListItem> list, ObservableList<Group> groups) {
		super();
		final LinkListItem self = this;

		// Create a list of the group names and column names
		ObservableList<String> groupNames = FXCollections.observableArrayList();
		groupNames.addAll(groups.stream().map(x -> x.getName()).collect(Collectors.toList()));

		groupCbox1 = setupComboBox(groupNames, "Group 1");
		groupCbox2 = setupComboBox(groupNames, "Group 2");


		// Add button to remove this item from the list
		remove = new Button("x");
		remove.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				list.remove(self);
			}
		});

		Separator sep = new Separator(Orientation.VERTICAL);
		sep.setPadding(new Insets(2));

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
}
