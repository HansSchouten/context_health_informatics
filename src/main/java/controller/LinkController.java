package controller;

import java.util.ArrayList;
import java.util.stream.Collectors;

import model.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * This class represents the controller for the link view.
 * @author Matthijs
 *
 */
public class LinkController extends SubController {
	/**
	 * This variable stores a listview.
	 */
	@FXML
	private ListView<LinkListItem> linkListView;

	/**
	 * This variable stores all the observers for the ink list items.
	 */
	private ObservableList<LinkListItem> linkListItems = FXCollections.observableArrayList();

	/**
	 * this variables stores the observables for the group list items.
	 */
	private ObservableList<Group> groupListItems = FXCollections.observableArrayList();

	/**
	 * This variable stores the groups that are created.
	 */
	private ArrayList<Group> groups;

	/**
	 * Construct a new LinkController.
	 */
	public LinkController() { }

	@Override
	protected void initialize() {
		linkListView.setItems(linkListItems);
	}

	/**
	 * Sets the groups inside the link controller and adds an initial link to the view.
	 * @param l The list of groups.
	 */
	public void setGroups(ArrayList<Group> l) {
		groups = l;
		groupListItems.clear();
		for (Group g : groups)
			groupListItems.add(g);
		removeAll();
		addLink();
	}

	/**
	 * This method adds a link to the link list view.
	 */
	@FXML
	public void addLink() {
		linkListItems.add(new LinkListItem(linkListItems, groupListItems));
	}

	/**
	 * This method removes all the links from the link list view.
	 */
	@FXML
	public void removeAll() {
		linkListItems.clear();
	}

	@Override
	public boolean validateInput() {
		return true;
	}

	/**
	 * This class represents a link list item.
	 * It contains comboboxes to select the group you want to link.
	 * @author Matthijs
	 *
	 */
	public static class LinkListItem extends HBox {
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
}


