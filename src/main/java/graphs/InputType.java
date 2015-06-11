package graphs;

import java.util.ArrayList;

import model.ColumnType;

/**
 * This class stores an inputtype to draw a graph.
 * @author Matthijs
 *
 */
public class InputType {

    /** This variable stores name of the input. */
    protected String inputName;

    /** This variable stores the type of the input. */
    protected ArrayList<ColumnType> inputTypes;

    /**
     * Consstruct a new inputtype.
     * @param name      - Name of the inputtype.
     * @param types      - Type of the inputType.
     */
    public InputType(String name, ArrayList<ColumnType> types) {
        inputName = name;
        inputTypes = types;
    }
}
