package graphs;

import model.ColumnType;

/**
 * This class stores an inputtype to draw a graph.
 * @author Matthijs
 *
 */
public class InputType {
    
    /** This variable stores name of the input. */
    protected String inputName;
    
    /** This variable stores the type of the input */
    protected ColumnType inputType;
    
    /**
     * Consstruct a new inputtype.
     * @param name      - Name of the inputtype.
     * @param type      - Type of the inputType.
     */
    public InputType(String name, ColumnType type) {
        inputName = name;
        inputType = type;
    }

}
