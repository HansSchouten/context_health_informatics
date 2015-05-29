package model;

import java.util.EnumSet;

/**
 * This enum stores the types of data that the user can use.
 * @author Matthijs
 *
 */
public enum ColumnType {

    /**
     * Column type string.
     */
    STRING("String"),

    /**
     * Column type int.
     */
    INT("Int"),

    /**
     * Column type double.
     */
    DOUBLE("Double"),

    /**
     * Column type date.
     */
    DATE("Date"),

    /**
     * Column type time.
     */
    TIME("Time"),

    /**
     * Column type date and time.
     */
    DATEandTIME("Time/Date"),

    /**
     * Column type comment.
     */
    COMMENT("Comment");

    /**
     * This variable stores the name of the columntype.
     */
    private String name;

    /**
     * Construct a new ColumnType with a name.
     * @param nm       - Name of the columnType.
     */
    private ColumnType(String nm) {
        name = nm;
    }

    /**
     * This method returns true if the name of the type is equal to the given string.
     * @param nm       - Name to compare with.
     * @return         - True if nm equals name of the type.
     */
    public boolean equalName(String nm) {
        return name.equals(nm);
    }

    /**
     * This method gets the type of the string.
     * @param nm       - Name of the columntype
     * @return         - columntype with the string, default type: string
     */
    public static ColumnType getTypeOf(String nm) {
        for (ColumnType col: ColumnType.values()) {
            if (col.equalName(nm)) {
                return col;
            }
        }
        return ColumnType.STRING;
    }

    /**
     * These are all datatypes that are a Datedatatype.
     * @return returns a enum set that only contains Datedatatypes.
     */
    public static EnumSet<ColumnType> getDateTypes() {
        return EnumSet.of(DATE, TIME, DATEandTIME);
    }

    @Override
    public String toString() {
        return name;
    }
}
