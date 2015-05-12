package model;

import java.util.ArrayList;

/**
 * This class represents the constraints added to the record. 
 * @author Elvan
 *
 */
public class Constraint {

	
	/**
	 * Variable to store all the records that satisfy the given constraint
	 */
	protected RecordList result;
	protected Column[] columns;
	protected String column;
	protected String operator;
	protected Object value;
	
	/**
	 * Construct a new constraints object. 
	 */
	public Constraint(String operator, String column, Object value) {
			
		this.operator = operator;
		this.column = column;
		this.value = value;
		
	}
	
	/**
	 * Filters the records on the given constraint
	 */
	public RecordList filter(RecordList input) {
		
	// operator: =, >, <, >=, <=, and, or
		
		Boolean evaluation;
		RecordList result = new RecordList(this.columns);
		
		for(int i=0; i < input.size(); i++) {
			
			Object actual = input.getRecord(i).get(column);
			
			evaluation = checkEvaluation(operator, actual);
		
			if (evaluation) {
				result.add(input.getRecord(i));
			}
		}

		return result;
	}
	
	/**
	 * Returns the evaluation of the constraint on the given record
	 */
	public boolean evaluateConstraint(Record input) {
		Boolean evaluation;
		
		Object actual = input.get(this.column);
		
		evaluation = checkEvaluation(operator, actual);
		
		return evaluation;
	}
	
	/**
	 * Returns the evaluation of the operator on the value
	 */
	private boolean checkEvaluation(String operator, Object actual) {
		
		Boolean evaluation;
		
		switch (operator) {
		case "=":
			evaluation = Integer.valueOf(actual.toString()) == (Integer) value;
			break;
		case ">":
			evaluation = Integer.valueOf(actual.toString()) > (Integer) value;
			break;
		case "<":
			evaluation = Integer.valueOf(actual.toString()) < (Integer) value;
			break;
		case "<=":
			evaluation = Integer.valueOf(actual.toString()) <= (Integer) value;
			break;
		case ">=":
			evaluation = Integer.valueOf(actual.toString()) >= (Integer) value;
			break;
		case "not":
			evaluation = Integer.valueOf(actual.toString()) != (Integer) value;
			break;
		default:
			evaluation = Integer.valueOf(actual.toString()) == (Integer) value;
			
		}
		
		return evaluation;
	}
		
	
	
}
	

