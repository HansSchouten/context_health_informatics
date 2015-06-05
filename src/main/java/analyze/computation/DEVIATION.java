package analyze.computation;

import java.util.HashMap;

import model.UnsupportedFormatException;
import model.datafield.DataField;
import model.datafield.DataFieldDouble;

/** This class calculates the average deviation of a list of column values.
 * @author Elvan
 *
 */
public abstract class DEVIATION {

    /**
     * Run the computation.
     * @param columnValues                    the values to run the computation on
     * @return                                the result of the computation
     * @throws UnsupportedFormatException    format is not supported
     */
    public static DataField run(HashMap<String, DataField> columnValues) throws UnsupportedFormatException {
        double sum = 0;
        for (DataField value : columnValues.values()) {
            sum = sum + value.getDoubleValue();
        }

        int count = columnValues.size();
        double avg = sum / count;

        double difference = 0;
        for (DataField value : columnValues.values()) {
            difference = difference + Math.abs(value.getDoubleValue() - avg);
        }

        double deviation = difference / count;

        return new DataFieldDouble(deviation);
    }

}
