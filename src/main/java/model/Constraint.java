package model;

/**
 * This class represents the constraints added to the record.
 * @author Elvan
 *
 */
public class Constraint {

	/**
	 * Variable to store all the records that satisfy the given constraint.
	 */
	protected RecordList result;

	/**
	 * Variable that stores the columns of the recordlist.
	 */
	protected Column[] columns;

	/**
	 * String that stores the name of the column to constraint on.
	 */
	protected String column;

	/**
	 * String that stores the operator to do.
	 */
	protected String operator;

	/**
	 * Object that stores the returnvalue.
	 */
	protected Object value;

	/**
	 * Construct a new constraints object.
	 * @param operation - Operator that the constraint does.
	 * @param cols      - Column to do the operator on.
	 * @param val       - Value of the constraint.
	 */
	public Constraint(String operation, String cols, Object val) {

		operator = operation;
		column = cols;
		value = val;
	}

    /**
     * Returns the evaluation of the constraint on the given record.
     * @param input     - Input record for the contstraint.
     * @return          - Result of the operation.
     */
	public RecordList filter(RecordList input) {

	// operator: =, >, <, >=, <=, and, or

		Boolean evaluation;
		RecordList tempresult = new RecordList(this.columns);

		for (int i = 0; i < input.size(); i++) {

			Object actual = input.getRecord(i).get(column);

			evaluation = checkEvaluation(operator, actual);

			if (evaluation) {
			    tempresult.add(input.getRecord(i));
			}
		}

		return tempresult;
	}

	/**
	 * Returns the evaluation of the constraint on the given record.
	 * @param input    - Input record for the contstraint.
	 * @return          - Result of the operation.
	 */
	public boolean evaluateConstraint(Record input) {
		Boolean evaluation;
		Object actual = input.get(this.column);
		evaluation = checkEvaluation(operator, actual);
		return evaluation;
	}

	/**
	 * Returns the evaluation of the operator on the value.
	 * @param operation - Operator to apply.
	 * @param actual    - Actual value.
	 * @return          - True if right, false otherwise.
	 */
	private boolean checkEvaluation(String operation, Object actual) {

		Boolean evaluation;

		switch (operation) {
		case "=":
			evaluation = Integer.parseInt(actual.toString()) == (Integer) value;
			break;
		case ">":
			evaluation = Integer.parseInt(actual.toString()) > (Integer) value;
			break;
		case "<":
			evaluation = Integer.parseInt(actual.toString()) < (Integer) value;
			break;
		case "<=":
			evaluation = Integer.parseInt(actual.toString()) <= (Integer) value;
			break;
		case ">=":
			evaluation = Integer.parseInt(actual.toString()) >= (Integer) value;
			break;
		case "not":
			evaluation = Integer.parseInt(actual.toString()) != (Integer) value;
			break;
		default:
			evaluation = Integer.parseInt(actual.toString()) == (Integer) value;
		}

		return evaluation;
	}
}

