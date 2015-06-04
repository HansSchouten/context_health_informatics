package analyze.computation;

import java.util.HashMap;

import model.SequentialData;
import model.Record;
import model.UnsupportedFormatException;
import model.datafield.DataField;

/**
 * This class represents an object that will do computations on the data.
 * @author Elvan
 *
 */
public class Computer {

    /**
     * This variable stores the values of the columns that are evaluated.
     */
    private HashMap<String, DataField> columnValues;

    /**
     * This variable stores the sequential data that needs to be computed.
     */
    private SequentialData userData;

    /**
     * This variable stores the name of the column the computation is called on.
     */
    private String column;

    /**
     * Construct a computation that consists of a string.
     * @param computation     string containing the computation
     * @param columname          name of the column to do the computation on
     * @param data              the data to perform the computation on
     */
    public Computer(String computation, String columname, SequentialData data) {
        column = columname;
        userData = data;
        columnValues = new HashMap<String, DataField>();
    }

    /**
     * This method should evaluate a condition with a given record.
     * @param data               user data to evaluate with
     * @param columname           column name to perform computation on
     */
    public void gatherColumnValues(SequentialData data, String columname) {
        columnValues.clear();
        // zal later vervangen kunnen worden door String patient ID
        int id = 0;

        for (Record record : data) {
            columnValues.put(Integer.toString(id), record.get(columname));
            id = columnValues.size();
        }

    }

    /**
     * This method performs the computation on the sequential data.
     * @param computation        the computation that needs to be done
     * @return        result of the computation
     * @throws UnsupportedFormatException        format is not supported
     */
    public DataField compute(String computation)
            throws UnsupportedFormatException {

            gatherColumnValues(userData, column);

            DataField result;

            switch (computation) {
            case "AVERAGE":
                result = AVG.run(columnValues);
                break;
            case "COUNT":
                result = COUNT.run(columnValues);
                break;
            case "SUM":
                result = SUM.run(columnValues);
                break;
            case "MAX":
                result = MAX.run(columnValues);
                break;
            case "MIN":
                result = MIN.run(columnValues);
                break;
            case "DEVIATION":
                result = DEVIATION.run(columnValues);
                break;
            case "VAR":
                result = VARIANCE.run(columnValues);
                break;
            case "SQUARED":
                result = SQUARED.run(columnValues);
                break;
            default:
                result = COUNT.run(columnValues);
                break;
        }
            return result;
    }

}
