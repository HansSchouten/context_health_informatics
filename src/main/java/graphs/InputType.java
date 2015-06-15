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

    /** This variable stores whether an label is allowed as input. */
    protected boolean labelAllowed;

    /** This variable stores whether an timeStamp is allowed as input. */
    protected boolean timestampAllowed;
    
    /**
     * Consstruct a new inputtype.
     * @param name      - Name of the inputtype.
     * @param types      - Type of the inputType.
     * @param la        - True if a label is allowed as input.
     * @param ta        - True if a timestamp is allowed as input.
     */
    public InputType(String name, ArrayList<ColumnType> types, boolean la, boolean ta) {
        inputName = name;
        inputTypes = types;
        labelAllowed = la;
        timestampAllowed = ta;
    }
}
