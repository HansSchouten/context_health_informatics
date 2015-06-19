package controller;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * A list item for selecting an identifier.
 * @author Remi
 *
 */
public class IdentifierListItem extends CustomListItem {

    /**
     * This variable contains the label of this listitem.
     */
    protected Label identifier, info;

    /**
     * This variable contains a radiobutton that says whether a row is checked.
     */
    protected RadioButton check;

    /**
     * Constructs a identifier list item.
     * @param par      - listitem that is used.
     * @param id - The id of the identifierListItem.
     * @param information       - Some information about this identifier.
     * @param tg - The togglegroup so only one item can be checked.
     */
    public IdentifierListItem(ListView<? extends CustomListItem> par, String id, String information, ToggleGroup tg) {
        super(par);

        check = new RadioButton();
        check.setToggleGroup(tg);
        identifier = new Label(id);
        identifier.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
        info = new Label(information);

        identifier.setMaxWidth(parent.getWidth() / 3);
        HBox.setHgrow(identifier, Priority.ALWAYS);

        // Toggle selection on double click
        this.setOnMouseClicked(e -> {
            if (e.getClickCount() % 2 == 0) {
                check.setSelected(!check.isSelected());
            }
        });

        this.getChildren().addAll(check, identifier, info);
    }

    @Override
    public void select() {
        parent.getSelectionModel().select(parent.getChildrenUnmodifiable().indexOf(this));
    }

    @Override
    public void selectNext() {
        // Not used
    }
}
