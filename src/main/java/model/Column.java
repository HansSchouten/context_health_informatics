package model;

/**
 * This class represents a column, which consist of a name and a characteritic.
 * @author Matthijs
 *
 */
public class Column {

    /**
     * This variable stores the name that is used for the column in the program.
     */
    private String name;

    /**
     * This variable determines whether the data of this column needs to be added to the records.
     */
    private boolean isExcluded = false;

    /**
     * This variable stores whether the column has a characteristic.
     */
    protected ColumnType characteristic;

    /**
     * This method creates an column with a name and a columntype.
     * @param n        - Name of the column.
     * @param type     - Type of the column.
     */
    public Column(String n, ColumnType type) {
        setName(n);
        characteristic = type;
    }

    /**
     * This method let you set the type of the Column.
     * @param ch        - The type of the column.
     */
    public void setType(final ColumnType ch) {
        characteristic = ch;
    }

    /**
     * This method returns the name of the column.
     * @return         - Name of the column.
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the type of this column.
     * @return         - Type of this column.
     */
    public ColumnType getType() {
        return characteristic;
    }

    /**
     * Return whether this column needs to be excluded.
     * @return            - True if this column needs to be excluded.
     */
    public boolean isExcluded() {
        return isExcluded;
    }

    /**
     * Store that this column needs to be excluded.
     */
    public void setExcluded() {
        isExcluded = true;
    }

    /**
     * This method sets the name of the column.
     * @param nm     - Name of the column.
     */
    public void setName(String nm) {
        this.name = nm;
    }
}
