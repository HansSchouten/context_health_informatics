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
	protected ColumnCharacteristics characteristic;
	
	public Column(String n, ColumnCharacteristics ch) {
		name = n;
		characteristic = ch;
	}
	
	/**
	 * This method let you set the characteristic of the Column. 
	 * @param ch		- The new characteritic of the column.
	 */
	public void setCharactersitic(ColumnCharacteristics ch) {
		characteristic = ch;
	}

}
