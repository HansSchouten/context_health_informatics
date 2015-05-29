package controller;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * The list item for the list of imported files.
 * @author Remi
 *
 */
public  class FileListItem extends CustomListItem {
    /**
     * This variable stores a label.
     */
    private Label label = new Label();

    /**
     * This variable stores the path to the file.
     */
    protected String path;

    /**
     * Constructs a file list item.
     * @param par The reference to the parent list
     * @param labelText The text on the label (file name)
     * @param filePath The path to the file
     */
    FileListItem(ListView<? extends CustomListItem> par, String labelText, String filePath) {
        super(par);
        this.path = filePath;

        label.setText(labelText);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setPadding(new Insets(4));
        HBox.setHgrow(label, Priority.ALWAYS);

        setupRemove(false);
        this.getChildren().addAll(label, remove);
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
