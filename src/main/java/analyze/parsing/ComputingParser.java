package analyze.parsing;
import java.util.Stack;

import computation.ComputationTypeException;
import computation.Computer;
import analyze.condition.ConditionParseException;
import model.DataField;
import model.SequentialData;
import model.UnsupportedFormatException;

/**
 * This class represents an object that will parse computing operations.
 * @author Elvan
 *
 */
public class ComputingParser implements SubParser {
	
	/**
	 * This variable stores the data in a sequential data object.
	 */
	protected SequentialData data;
	
	/**
	 * This variable stores the column name on which the computation is called.
	 */
	public String colname;
	
	/**
	 * This variable stores the kind of computation.
	 */
	public String computation;
	
	// This does not yet work for multiple columns!
	
	@Override
	public void parseComputation(String operation, SequentialData data) throws ComputationTypeException, UnsupportedFormatException {
		String[] splitted = operation.split("\\(", 2);
		computation = splitted[0];
		String column = splitted[1];
		
		String[] colsplitted = column.split("\\(", 2);
		colname = colsplitted[1];
		colname = colname.substring(0, colname.length() - 2);
		
		//Computer comp = new Computer(computation, colname, data);
		
		//return comp.compute(computation);
	}

	@Override
	public SequentialData parseOperation(String operation, SequentialData data) {
		// TODO Auto-generated method stub
		return null;
	}

}