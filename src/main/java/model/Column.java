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
	protected String name;
	
	/**
	 * This variable stores whether the column has a characteristic.
	 */
	protected ColumnType characteristic;
	
	public Column(String n) {
		name = n;
		characteristic = ColumnType.STRING;
	}
	
	/**
	 * This method let you set the type of the Column. 
	 * @param ch		- The type of the column.
	 */
	public void setType(ColumnType ch) {
		characteristic = ch;
	}

}