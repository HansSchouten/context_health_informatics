package controller.importcontroller;

/**
 * This class represents a primary key, constisting of a column.
 * @author Matthijs
 *
 */
public class ColumnKey implements PrimaryKey {

    /** This variable stores the name of the key. */
    protected String name;

    /**
     * Construct a columnKey.
     * @param nm        - Name of the column.
     */
    public ColumnKey(String nm) {
        name = nm;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean isFileNameKey() {
        return false;
    }

    @Override
    public boolean isNoKey() {
        return false;
    }

}
