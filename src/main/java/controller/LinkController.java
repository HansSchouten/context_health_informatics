package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class LinkController extends SubController {
	@FXML
	private ListView<LinkListItem> linkListView;
	
	private ObservableList<LinkListItem> linkListItems = FXCollections.observableArrayList();
	private ObservableList<String> groupNames = FXCollections.observableArrayList();
	private ObservableList<String> colNames = FXCollections.observableArrayList();
	
	public LinkController() {}
	
	@FXML
	private void initialize() {
		groupNames.addAll("Group foo", "Group bar");
		colNames.addAll("Column foo", "Column bar");
		
		// Get file names etc from MainApp
		
		
		linkListView.setItems(linkListItems);
		addLink();
	}

	@Override
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void addLink() {
		linkListItems.add(new LinkListItem(linkListItems, groupNames, colNames));
	}
	
	@FXML
	public void removeAll() {
		linkListItems.clear();
	}
	
	public static class LinkListItem extends HBox {
		ComboBox<String> groupCbox1, groupCbox2, colCbox1, colCbox2;
		
		Button remove;
		
		LinkListItem(final ObservableList<LinkListItem> list, ObservableList<String> groupNames, ObservableList<String> colNames) {
			super();
			final LinkListItem self = this;
			
			groupCbox1 = setupComboBox(groupNames, "Group 1");
			groupCbox2 = setupComboBox(groupNames, "Column of group 1");
			colCbox1 = setupComboBox(colNames, "Group 2");
			colCbox2 = setupComboBox(colNames, "Column of group 2");
			
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


