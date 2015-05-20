package model;

import java.util.ArrayList;

/**
 * This class consist of a list of contrainst that can be applied.
 * @author Matthijs
 *
 */
public class ConstraintList extends ArrayList<Constraint> {

	/**
     * Serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Arraylist that stores the contstraints.
     */
    protected ArrayList<Constraint> cons;

	/**
	 * Constructs a new ConstraintList.
	 */
	public ConstraintList() {
		cons = new ArrayList<Constraint>();
	}

	/**
	 * Adds a constraint to the ConstraintList.
	 * @param operator     - Operator to add
	 * @param column       - Column to do the filter on.
	 * @param value        - Value of the constraint.
	 */
	public void addConstraint(String operator, String column, Object value) {
		cons.add(new Constraint(operator, column, value));
	}

	/**
	 * Returns constraint on given index of ConstraintList.
	 * @param index - Index of the constraint.
	 * @return      - Constraint on that index.
	 */
	public Constraint getConstraint(int index) {
		return this.get(index);
	}

    /**
     * Filters the records on the given (multiple) constraints.
     * @param input     - Input for the filter.
     * @param binop     - String that contains the binop.
     * @return          - Recordlist that is the result of the filter.
     */
	public  RecordList filterBinary(RecordList input, String binop) {

		RecordList result = new RecordList(input.columns);

		for (int i = 0; i < input.size(); i++) {
			switch (binop) {
			case "AND":
				if (this.filterAND(input.get(i)))
					result.add(input.get(i));
				break;
			case "OR":
				if (this.filterOR(input.get(i)))
					result.add(input.get(i));
				break;
			default:
				for (int j = 0; j < this.size(); j++) {
					result.retainAll(this.get(j).filter(input));
				}
			}
		}
		return result;
	}

	/**
	 * Filters the records on binary AND operator.
	 * @param input    - Record to and.
	 * @return         - True if the AND is right, false otherwise.
	 */
	public Boolean filterAND(Record input) {

			Boolean evaluation = true;

			for (int i = 0; i < this.size(); i++) {
				evaluation = evaluation && this.get(i).evaluateConstraint(input);
			}
			return evaluation;
	}

	/**
	 * Filters the records on binary OR operator.
	 * @param input    - Record to or.
	 * @return         - true if the OR is right, false otherwise.
	 */
	public Boolean filterOR(Record input) {

			Boolean evaluation = false;

			for (int i = 0; i < this.size(); i++) {
				evaluation = evaluation || this.get(i).evaluateConstraint(input);
			}

			return evaluation;
		}
}
