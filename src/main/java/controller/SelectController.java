package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.Group;
import model.Linker;
import model.SequentialData;
import controller.MainApp.NotificationStyle;

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

    /** The togglegroup which allows only one radiobutton to be selected.*/
    private ToggleGroup toggleGroup;

    /** This variable stores the pipeline number of this controller. */
    private int pipelineNumber = 2;

    /** The label indicating what the currently selected key is. */
    @FXML
    private Label currentlySelectedLabel;

    @Override
    protected void initialize() {
        searchField.textProperty().addListener((obs, oldV, newV) -> {
            if (newV.length() == 0) {
                filteredData.setPredicate(s -> true);
            } else {
                filteredData.setPredicate(x -> x.identifier.getText().contains(newV));
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
                key = ili.identifier.getText();
            }
        }
        return linkedGroups.get(key);
    }

    @Override
    public void setData(Object o) {
        if (o == null) {
            return;
        }

        @SuppressWarnings("unchecked")
        ArrayList<Group> groups = (ArrayList<Group>) o;

        Linker linker = new Linker();
        setLinkedGroups(linker.link(groups));

        searchField.setText("");
    }

    /**
     * Sets the linked groups.
     * @param lg The linked groups.
     */
    public void setLinkedGroups(HashMap<String, SequentialData> lg) {
        linkedGroups = lg;

        allItems = FXCollections.observableArrayList();

        currentlySelectedLabel.setText("None");
        toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                for (IdentifierListItem ili : identifierListView.getItems()) {
                    if (ili.check.isSelected()) {
                        currentlySelectedLabel.setText(ili.identifier.getText());
                        break;
                    }
                }
            }
        });

        // Sort the input
        List<String> sortedItems = linkedGroups.keySet().stream().sorted().collect(Collectors.toList());
        for (String s : sortedItems) {
            IdentifierListItem ili = new IdentifierListItem(identifierListView, s,
                    "Rows: " + linkedGroups.get(s).size() + ", Columns: " + linkedGroups.get(s).getColumns().length,
                    toggleGroup);
            allItems.add(ili);
        }

        // Remember the previously selected identifier
        String key = null;
        for (IdentifierListItem ili : allItems) {
            if (ili.check.isSelected()) {
                key = ili.identifier.getText();
                break;
            }
        }

        // Create filtered list
        filteredData = new FilteredList<>(allItems, x -> true);
        identifierListView.setItems(filteredData);

        // Restore the selected item if it exists
        if (key != null) {
            for (IdentifierListItem ili : identifierListView.getItems()) {
                if (ili.identifier.getText().equals(key)) {
                    ili.check.setSelected(true);
                    break;
                }
            }
        }
    }

    @Override
    protected int getPipelineNumber() {
        return pipelineNumber;
    }
}
