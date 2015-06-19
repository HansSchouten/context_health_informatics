package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

/**
 * The list item for the list of groups.
 *
 */
public class ColumnListItem extends CustomListItem {
    /**
     * The textfield for entering the column name.
     */
    protected TextField txtField;

    /**
     * Used to select what kind of data this column contains.
     */
    protected ComboBox<String> comboBox, secondBox;

   /**
    * A checkbox to indicate whether to include this column for the analysis.
    */
    protected CheckBox use;

    /**
     * This variable is used to store a radio button that determines if a
     * column must be used for sorting.
     */
    protected RadioButton cbSort;

    /**
     * An array of toggle groups for the different date options.
     */
    protected ToggleGroup[] toggleGroups;

    /**
     * The grouplistitem that contains this columnlistitem.
     */
    protected GroupListItem groupLI;

    /**
     * Constructs a column list item.
     * @param par The parent listview.
     * @param gli The group list item which contains this item.
     * @param groups The togglegroups for the Date, Time and Date/Time options.
     */
    ColumnListItem(ListView<? extends CustomListItem> par, GroupListItem gli, ToggleGroup[] groups) {
        super(par);
        groupLI = gli;
        toggleGroups = groups;

        txtField = createTextField("Name");

        ObservableList<String> options =
                FXCollections.observableArrayList(
                    "String",
                    "Int",
                    "Double",
                    "Time",
                    "Date",
                    "Date/Time",
                    "Comment"
                );

        comboBox = new ComboBox<String>(options);
        comboBox.setValue(options.get(0));
        comboBox.setOnAction((event) -> onChange());

        use = new CheckBox();
        use.setSelected(true);
        use.setTooltip(new Tooltip("Whether to include this column when importing the file."));

        setupRemove(true);
        setupDragDrop(txtField);
        this.getChildren().addAll(use, txtField, comboBox, remove);
    }

    /**
     * Method handles an event on combobox.
     */
    private void onChange() {
        deleteExtraOptions();
        addDateOptions(comboBox.getSelectionModel().getSelectedItem().toString());
    }

    /**
     * This method adds date options the columnlist if necessary.
     * @param type         - Type of this column.
     */
    protected void addDateOptions(String type) {
           // Determine current state of combobox
        switch (type) {
            case "Date/Time":
                setSecondBox(new String[]{
                        "dd-MM-yyyy HH:mm", "dd-MM-yyyy HH:mm:ss",
                        "dd/MM/yyyy HH:mm", "dd/MM/yyyy HH:mm:ss",
                        "Excel epoch"});
                cbSort.setToggleGroup(toggleGroups[2]);
                break;
            case "Date":
                setSecondBox(new String[]{"dd/MM/yyyy", "dd/MM/yy",
                        "dd-MM-yyyy", "dd-MM-yy", "yyMMdd", "Excel epoch", "d/M/yy"});
                cbSort.setToggleGroup(toggleGroups[0]);

                break;
            case "Time":
                setSecondBox(new String[]{"HH:mm", "HHmm"});
                cbSort.setToggleGroup(toggleGroups[1]);
                break;
            default:
                break;
        }
    }

    /**
     * This method deletes extra optionfields.
     */
    private void deleteExtraOptions() {
        if (secondBox != null) {
            this.getChildren().remove(secondBox);
            secondBox = null;
        }
        if (cbSort != null) {
            this.getChildren().remove(cbSort);
            cbSort = null;
        }
    }
    /**
     * This method adds a second combobox for more options.
     * @param options The list with all options
     */
    private void setSecondBox(String[] options) {
        ObservableList<String> oOptions = FXCollections.observableArrayList(options);
        secondBox = new ComboBox<String>(oOptions);
        secondBox.setValue(options[0]);

        // This can be enhanced, maybe we can add functionality that we make a button unclickable
        // if there is already another field sort pressed.
        cbSort = new RadioButton();
        cbSort.setText("Sort");
        HBox.setMargin(cbSort, new Insets(4));

        this.getChildren().add(2, secondBox);
        this.getChildren().add(3, cbSort);
    }

    @Override
    public void select() {
        parent.getSelectionModel().select(parent.getItems().indexOf(this));
        txtField.requestFocus();
    }

    @Override
    public void selectNext() {
        int idx = parent.getItems().indexOf(this);
        if (parent.getItems().size() - 1 <= idx) {
            // If this is the last item, add a new one.
            @SuppressWarnings("unchecked")
            ObservableList<ColumnListItem> list = (ObservableList<ColumnListItem>) parent.getItems();
            list.add(new ColumnListItem(parent, groupLI, toggleGroups));
        }
        parent.getItems().get(idx + 1).select();
    }

    /**
     * Sets up drag n drop for a node inside this list item.
     * @param n The node.
     */
    @SuppressWarnings("unchecked")
    public void setupDragDrop(Node n) {
        n.setOnDragDetected(e -> {
            Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();

            // Show a snaphot of the list item
            WritableImage snapshot = this.snapshot(new SnapshotParameters(), null);
            db.setDragView(snapshot, e.getX(), e.getY());

            content.putString("" + parent.getItems().indexOf(this));

            db.setContent(content);
            e.consume();
        });

        n.setOnDragOver(e -> {

            if (parent.getItems().contains(e.getGestureSource()) && e.getGestureSource() != this
                    && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });

        n.setOnDragEntered(e -> {
            int targetIdx = parent.getItems().indexOf(this);
            parent.getItems().get(targetIdx).getStyleClass().add("highlight");
        });

        n.setOnDragExited(e -> {
            int targetIdx = parent.getItems().indexOf(this);
            parent.getItems().get(targetIdx).getStyleClass().remove("highlight");
        });

        n.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();

            if (db.hasString()) {
                int targetIdx = parent.getItems().indexOf(this);

                // Replace the item of the source with the target
                ColumnListItem item = (ColumnListItem) parent.getItems().get(
                        Integer.parseInt(db.getString()));

                parent.getItems().remove(item);
                ((ListView<ColumnListItem>) parent).getItems().add(targetIdx, item);
                parent.getSelectionModel().select(targetIdx);
            }

            e.setDropCompleted(true);
            e.consume();
        });
    }

    @Override
    public void remove() {
        super.remove();
        // Remove this column from the toggle group to know that this column
        // is not selected after it is deleted.
        if (cbSort != null && cbSort.getToggleGroup() != null) {
            cbSort.getToggleGroup().getToggles().remove(cbSort);
        }
    }
}
