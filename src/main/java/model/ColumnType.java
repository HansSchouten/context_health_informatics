package model;

import java.util.EnumSet;

/**
 * This enum stores the types of data that the user can use.
 * @author Matthijs
 *
 */
public enum ColumnType {

    /**
     * These are all the possible datatypes of a column.
     */
	STRING, INT, DOUBLE, DATE, TIME, DATEandTIME, COMMENT;

	/**
	 * These are all datatypes that are a Datedatatype.
	 * @return returns a enum set that only contains Datedatatypes.
	 */
	public static EnumSet<ColumnType> getDateTypes() {
        return EnumSet.of(DATE, TIME, DATEandTIME);
    }

}
