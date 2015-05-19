package analyze;
/**
 * This class represents a label, that you can give to a record.
 * @author Matthijs
 *
 */
public class Label {

    /**
     * This variables stores the name of the label.
     */
    private String name;

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

    /**
     * This method returns the number of the label.
     * @return      - number of the label.
     */
    public int getNumber() {
        return number;
    }

    /**
     * This method returns the name of the label.
     * @return      - Label of the name.
     */
    public String getName() {
        return name;
    }
}
