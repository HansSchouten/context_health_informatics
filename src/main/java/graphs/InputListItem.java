package graphs;

import model.Column;
import controller.CustomListItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;

/**
 * This class represents an inputlistItem that gets input for a graph.
 * @author Matthijs
 *
 */
public class InputListItem extends CustomListItem {

    /**
     * Used to select what kind of data this column contains.
     */
    protected ComboBox<String> inputs;

    /** stores the name of this inputlistitem. */
    protected String name;

    /**
     * Construct an inputlistItem.
     * @param par           - Parent of this item.
     * @param type          - Type the selected column should have.
     * @param cols          - Columns that are available
     * @param deleteable    - Whether the column is deletable.
     */
    public InputListItem(ListView<? extends CustomListItem> par, InputType type,
            Column[] cols, boolean deleteable) {
        super(par);

        addColumnNames(cols, type);
        name = type.inputName;
        Label nameLabel = new Label(type.inputName);
        nameLabel.setMaxWidth(Double.MAX_VALUE);

        this.setHgrow(nameLabel, Priority.ALWAYS);
        this.getChildren().addAll(nameLabel, inputs);

        if (deleteable) {
            setupRemove(true);
        }
    }

    /**
     * This method adds the columnNames to this inputListItem, if conversion is possible.
     * @param cols      - columms to add.
     * @param type      - type the to add columns should match.
     */
    protected void addColumnNames(Column[] cols, InputType type) {
        inputs = new ComboBox<String>();

        for (Column col : cols) {
            if (type.inputTypes.contains(col.getType())) {
                inputs.getItems().add(col.getName());
                //TODO: this should work on conversions, possibly make a list of allowed values.
            }
        }
        inputs.getSelectionModel().select(0);
    }

    /**
     * This method returns the label of this listItem.
     * @return      - name of this listitem
     */
    public String getinputName() {
        return name;
    }

    /**
     * This method should return the column that is currently selected.
     * @return      - Name of the selected column.
     */
    public String getSelectedColumn() {
        return inputs.getSelectionModel().getSelectedItem();
    }

    @Override
    public void select() {
        // TODO Auto-generated method stub
    }

    @Override
    public void selectNext() {
        // TODO Auto-generated method stub
    }
}
