package analyze.parsing;

import model.Record;
import model.SequentialData;
import model.datafield.DataField;
import analyze.AnalyzeException;
import analyze.constraining.Constrainer;
import analyze.converting.Converter;

/**
 * This class parses the Conversion operation.
 *
 */
public class ConversionParser implements SubParser {

    @Override
    public SequentialData parseOperation(String operation, SequentialData data) throws AnalyzeException {
        if (operation.startsWith("SECOND MEASUREMENT(")) {
            String columnName = operation.substring(19, operation.length() - 1);
            Converter.checkSecondMeasurement(data, columnName);
            return data;
        } else if (operation.startsWith("REMEASUREMENT")) {
            String[] splitted = operation.split(" ", 2);
            String columnName = splitted[1];
            Converter converter = new Converter(data, columnName);
            converter.convert();

            for (Record rec : data) {
                Converter.checkRemeasurement(rec);
            }
            return data;
            } else {
            Converter converter = new Converter(data, operation);
            return converter.convert();
        }
    }

    @Override
    public ParseResult parseOperation(String operation, DataField data)
            throws AnalyzeException {
        throw new ParseException("Constraining on a single value is not possible");
    }

}
