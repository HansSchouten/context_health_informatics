package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Group;
import model.Linker;

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

	@Override
	public void setData(Object o) {
		groups = (ArrayList<Group>) o;
		groupListItems.clear();
		for (Group g : groups) {
			groupListItems.add(g);
		}
		removeAll();
		addLink();
	}

	/**
	 * This method adds a link to the link list view.
	 */
	@FXML
	public void addLink() {
		linkListItems.add(new LinkListItem(linkListView, groupListItems));
	}

	/**
	 * This method removes all the links from the link list view.
	 */
	@FXML
	public void removeAll() {
		linkListItems.clear();
	}

	@Override
	public boolean validateInput(boolean showPopup) {
		return true;
	}

	@Override
	public Object getData() {
		Linker linker = new Linker();
		return linker.link(groups);
	}
}
