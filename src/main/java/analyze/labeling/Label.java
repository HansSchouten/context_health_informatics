package analyze.labeling;
/**
 * This class represents a label, that you can give to a record.
 * @author Matthijs
 *
 */
public class Label {

    /**
     * This variables stores the name of the label.
     */
    protected String name;

    /**
     * This variable stores the number associated with the label.
     */
    protected int number;

    /**
     * This method contstructs a new label.
     * @param label     - The name of the label
     * @param num       - The number associated with the label.
     */
    public Label(String label, int num) {
        name = label;
        number = num;
    }
}
