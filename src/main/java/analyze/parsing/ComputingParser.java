package analyze.parsing;
import model.datafield.DataField;
import model.SequentialData;
import model.UnsupportedFormatException;
import analyze.AnalyzeException;
import analyze.computation.Computer;

/**
 * This class represents an object that will parse computing operations.
 * @author Elvan
 *
 */
public class ComputingParser implements SubParser {

    /**
     * This variable stores the column name on which the computation is called.
     */
    protected String colname;

    /**
     * This variable stores the kind of computation.
     */
    protected String computation;

    // This does not yet work for multiple columns!

    @Override
    public DataField parseOperation(String operation, SequentialData data)
            throws UnsupportedFormatException, AnalyzeException {

            String[] splitted = operation.split("\\(", 2);
            computation = splitted[0];
            String column = splitted[1];

            String[] colsplitted = column.split("\\(", 2);
            colname = colsplitted[1];
            colname = colname.substring(0, colname.length() - 2);

            Computer comp = new Computer(colname, data);

            return comp.compute(computation);
    }


    @Override
    public ParseResult parseOperation(String operation, DataField data)
            throws AnalyzeException {
        throw new ParseException("Computations on a single value are not possible");
    }


}
