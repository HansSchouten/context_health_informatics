package model;

/**
 * Class that describes a DateColumn.
 *
 */
public class DateColumn extends Column {

	/**
	 * This field describes how the date field is formatted.
	 */
	private String format;

	/**
	 * This field describes of this column must be used for sorting.
	 */
	private Boolean sort;

	/**
	 * Constructor.
	 * @param name specifies the name of the DataColumn.
	 * @param mFormat describes format of time/datestamp.
	 * @param mSort describes if the column must be formatted.
	 */
	public DateColumn(String name, String mFormat, Boolean mSort) {
		super(name);
		this.sort = mSort;
		this.format = mFormat;
	}

	/**
	 * Getter format.
	 * @return String format
	 */
	public String getDateFormat() {
		return format;
	}

	/**
	 * Getter of sort.
	 * @return Boolean sort
	 */
	public Boolean sortOnThisField() {
		return sort;
	}

}
