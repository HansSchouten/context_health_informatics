package model;

import java.util.ArrayList;

public class ConstraintList extends ArrayList<Constraint> {
	
	protected ArrayList<Constraint> cons;
	
	/**
	 * Constructs a new ConstraintList
	 */
	public ConstraintList() {
		cons = new ArrayList<Constraint>();
	}
	
	/**
	 * Adds a constraint to the ConstraintList
	 */
	public void addConstraint(String operator, String column, Object value) {
		cons.add(new Constraint(operator, column, value));
	}
	
	/**
	 * Returns constraint on given index of ConstraintList
	 */
	public Constraint getConstraint(int index) {
		return this.get(index);
	}
	
	/**
	 * Filters the records on the given (multiple) constraints
	 */
	public  RecordList filterBinary(RecordList input, String binop) {

		RecordList result = new RecordList(input.columns);
		
		for(int i=0; i < input.size(); i++) {
			switch (binop) {
			case "AND":
				if(this.filterAND(input.get(i)))
					result.add(input.get(i));
				break;
			case "OR":
				if(this.filterOR(input.get(i)))
					result.add(input.get(i));
				break;
			default:
				for(int j=0; j < this.size(); j++) {
					result.retainAll(this.get(j).filter(input));
				}	
		}
		}
		return result;
	}
	
	/**
	 * Filters the records on binary AND operator
	 */
	public Boolean filterAND(Record input) {
			
			Boolean evaluation = true;
			
			for(int i=0; i < this.size(); i++) {
				
				evaluation = (evaluation && this.get(i).evaluateConstraint(input)); 
			}
			
			return evaluation;
		}
	
	/**
	 * Filters the records on binary OR operator
	 */
	public Boolean filterOR(Record input) {
			
			Boolean evaluation = false;
			
			for(int i=0; i < this.size(); i++) {
				
				evaluation = (evaluation || this.get(i).evaluateConstraint(input)); 
	
			}
			
			return evaluation;
		}

}
