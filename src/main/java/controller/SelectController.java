package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import controller.MainApp.NotificationStyle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import model.Group;
import model.Linker;
import model.SequentialData;

/**
 * The SelectController allows the user to select which imported item(s) to analyse.
 * @author Remi
 *
 */
public class SelectController extends SubController {
    /** The listview containing the filtered data of all items. */
    @FXML
    private ListView<IdentifierListItem> identifierListView;

    /** A list of all possible items to check. */
    private ObservableList<IdentifierListItem> allItems = FXCollections.observableArrayList();

    /** This variables stores all the result that are searched.*/
    private FilteredList<IdentifierListItem> filteredData;

    /** The linked groups. */
    private HashMap<String, SequentialData> linkedGroups;

    /** The textfield for entering a search query. */
    @FXML
    private TextField searchField;

    /** This variable stores the pipeline number of this controller. */
    private int pipelineNumber = 2;

    @Override
    protected void initialize() {
        // Allow multi selection
        identifierListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        searchField.textProperty().addListener((obs, oldV, newV) -> {
            if (newV.length() == 0) {
                filteredData.setPredicate(s -> true);
            } else {
                filteredData.setPredicate(x -> x.label.getText().contains(newV));
            }
        });
    }

    @Override
    public boolean validateInput(boolean showPopup) {
        // If there is at least one item checked, it's OK!
        for (IdentifierListItem ili : allItems) {
            if (ili.check.isSelected()) {
                return true;
            }
        }

        if (showPopup) {
            mainApp.showNotification("You need to check at least one item to analyse.",
                    NotificationStyle.WARNING);
        }
        return false;
    }

    @Override
    public Object getData() {
        // Return the selected item
        String key = "";
        for (IdentifierListItem ili : identifierListView.getItems()) {
            if (ili.check.isSelected()) {
                key = ili.label.getText();
            }
        }
        System.out.println(linkedGroups.keySet());
        return linkedGroups.get(key);
    }

    @Override
    public void setData(Object o) {
        @SuppressWarnings("unchecked")
        ArrayList<Group> groups = (ArrayList<Group>) o;

        Linker linker = new Linker();
        linkedGroups = linker.link(groups);

        allItems = FXCollections.observableArrayList();

        // Sort the input
        List<String> sortedItems = linkedGroups.keySet().stream().sorted().collect(Collectors.toList());
        for (String s : sortedItems) {
            IdentifierListItem ili = new IdentifierListItem(identifierListView, s);
            allItems.add(ili);
        }

        // Create filtered list
        filteredData = new FilteredList<>(allItems, x -> true);
        identifierListView.setItems(filteredData);

        searchField.setText("");
    }

    /** Checks all items. */
    @FXML
    public void checkAll() {
        allItems.forEach(x -> x.check.setSelected(true));
    }
    /** Unchecks all items. */
    @FXML
    public void uncheckAll() {
        allItems.forEach(x -> x.check.setSelected(false));
    }
    /** Checks all searched items. */
    @FXML
    public void checkSearched() {
        identifierListView.getItems().forEach(x -> x.check.setSelected(true));
    }
    /** Unchecks all searched items. */
    @FXML
    public void uncheckSearched() {
        identifierListView.getItems().forEach(x -> x.check.setSelected(false));
    }
    /** Checks all selected items. */
    @FXML
    public void checkSelected() {
        identifierListView.getSelectionModel().getSelectedItems().forEach(x -> x.check.setSelected(true));
    }
    /** Unchecks all selected items. */
    @FXML
    public void uncheckSelected() {
        identifierListView.getSelectionModel().getSelectedItems().forEach(x -> x.check.setSelected(false));
    }

    @Override
    protected int getPipelineNumber() {
        return pipelineNumber;
    }
}
