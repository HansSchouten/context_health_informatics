package controller;

import java.util.ArrayList;
import java.util.stream.Collectors;

import model.Group;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class LinkController extends SubController {
	@FXML
	private ListView<LinkListItem> linkListView;
	
	private ObservableList<LinkListItem> linkListItems = FXCollections.observableArrayList();
	private ObservableList<Group> groupListItems = FXCollections.observableArrayList();
	
	private ArrayList<Group> groups;
	
	public LinkController() {}
	
	@Override
	protected void initialize() {
		linkListView.setItems(linkListItems);
	}
	
	/**
	 * Sets the groups inside the link controller and adds an initial link to the view.
	 * @param l
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
	 * This method adds a link to the link list view
	 */
	@FXML
	public void addLink() {
		linkListItems.add(new LinkListItem(linkListItems, groupListItems));
	}
	
	/**
	 * This method removes all the links from the link list view
	 */
	@FXML
	public void removeAll() {
		linkListItems.clear();
	}
	
	/**
	 * This class represents a link list item.
	 * It contains comboboxes to select the groupt you want to link.
	 * @author Matthijs
	 *
	 */
	public static class LinkListItem extends HBox {
		ComboBox<String> groupCbox1, groupCbox2, colCbox1, colCbox2;
		
		Button remove;
		
		LinkListItem(final ObservableList<LinkListItem> list, ObservableList<Group> groups) {
			super();
			final LinkListItem self = this;
			
			// Create a list of the group names and column names
			ObservableList<String> groupNames = FXCollections.observableArrayList();
			groupNames.addAll(groups.stream().map(x -> x.getName()).collect(Collectors.toList()));
			ObservableList<String> colNames1 = FXCollections.observableArrayList();
			ObservableList<String> colNames2 = FXCollections.observableArrayList();
			
			groupCbox1 = setupComboBox(groupNames, "Group 1");
			groupCbox2 = setupComboBox(groupNames, "Group 2");
			colCbox1 = setupComboBox(colNames1, "Column of group 1");
			colCbox2 = setupComboBox(colNames2, "Column of group 2");
			
			// When selecting a group, show its columns in the next combo box
			groupCbox1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> arg0,	Number oldV, Number newV) {
					colNames1.clear();
					colNames1.addAll(groups.get(newV.intValue()).getColumns());
				}
			});
			groupCbox2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> arg0,	Number oldV, Number newV) {
					colNames2.clear();
					colNames2.addAll(groups.get(newV.intValue()).getColumns());
				}
			});
			
			// Add button to remove this item from the list
			remove = new Button("x");
			remove.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					list.remove(self);
				}
			});
			
			Separator sep = new Separator(Orientation.VERTICAL);
			sep.setPadding(new Insets(2));
			
			this.getChildren().addAll(groupCbox1, colCbox1, sep, groupCbox2, colCbox2, remove);
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
			cb.setPromptText(prompt);
			cb.setEditable(true);
			return cb;
		}
	}
}


