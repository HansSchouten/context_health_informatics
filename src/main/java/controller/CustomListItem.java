package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * A custom list item for a ListView.
 * @author Remi
 *
 */
public abstract class CustomListItem extends HBox {

    /**
     * The button for removing this item.
     */
    protected Button remove;
    /**
     * The ListView which contains this list item.
     */
    protected ListView<? extends CustomListItem> parent;

    /**
     * Initializes this custom list item.
     * @param par The parent list which contains thist list item.
     */
    public CustomListItem(ListView<? extends CustomListItem> par) {
        super();
        parent = par;
    }

    /**
     * Sets up the remove button.
     * @param leaveOne Whether the last list item can not be removed.
     */
    public void setupRemove(boolean leaveOne) {
        remove = new Button();
        remove.getStyleClass().add("remove");

        remove.setOnAction(event -> remove());

        // If this is the last item of the list, disable the remove button
        if (leaveOne) {
            remove.setDisable(true);

            parent.getItems().addListener(new ListChangeListener<CustomListItem>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends CustomListItem> c) {
                    if (parent.getItems().size() == 1) {
                        remove.setDisable(true);
                    } else {
                        remove.setDisable(false);
                    }
                }
            });
        }
    }

    /**
     * Selects this item in the ListView and requests focus of the main element.
     */
    public abstract void select();

    /**
     * Selects the next item in the list and creates a new item if there is no next item.
     */
    public abstract void selectNext();

    /**
     * Removes this item from the ListView.
     */
    public void remove() {
        // Select next item, in order to update other tabs.
        int index = parent.getItems().indexOf(this);
        parent.getSelectionModel().select(index + 1);
        parent.getItems().remove(this);
    }

    /**
     * Creates a text field which always grows horizontally and selects the next item when
     * pressing enter.
     * @param prompt The prompt text for the text field.
     * @return Returns a text field as described above.
     */
    public TextField createTextField(String prompt) {
        TextField txtField = new TextField();

        txtField.setPromptText(prompt);
        txtField.setMaxWidth(Double.MAX_VALUE);
        txtField.setPadding(new Insets(4));
        HBox.setHgrow(txtField, Priority.ALWAYS);

        txtField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                // Focus on next text field when pressing ENTER
                if (e.getCode().equals(KeyCode.ENTER)) {
                    selectNext();
                }
            }
        });

        // Focus on list item when clicking on text field
        txtField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldV,
                    Boolean newV) {
                if (newV) {
                    select();
                }
            }
        });

        return txtField;
    }
}

