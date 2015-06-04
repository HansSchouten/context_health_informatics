package analyze.parsing;

import java.util.ArrayList;

import model.Column;
import model.DataField;
import model.DataFieldInt;
import model.Record;
import model.SequentialData;
import analyze.AnalyzeException;
import analyze.comparing.Comparer;
import analyze.labeling.LabelFactory;

/**
 * This class parses Compare constructs.
 * @author Hans Schouten
 *
 */
public class ComparisonParser implements SubParser {

    @Override
    public ParseResult parseOperation(String operation, SequentialData data) throws AnalyzeException {

        ParseResult result = new SequentialData();

        if (operation.startsWith("PATTERN")) {
            int occurenceCount = 0;
            ArrayList<Integer> labelSequence = getLabels(operation);
            int currentLabelIndex = 0;
            for (Record record : data) {
                if (record.containsLabel(labelSequence.get(currentLabelIndex))) {
                    if (currentLabelIndex == labelSequence.size() - 1) {
                        occurenceCount++;
                        currentLabelIndex = 0;
                    } else {
                        currentLabelIndex++;
                    }
                }
            }

            result = new DataFieldInt(occurenceCount);


        } else {
            //String columnNames = operation.replace("TIME BETWEEN ", "");
            String[] splitted = operation.split(" AND ", 2);

            String date1 = splitted[0];
            String date2 = splitted[1];

            Column column1 = data.getColumn(date1);
            Column column2 = data.getColumn(date2);

            Comparer comparer = new Comparer(data, column1, column2);

            result = comparer.compare();
        }
        return result;
    }

    /**
     * Return the label sequence this comparison needs to look for.
     * @param operation        - the string containing the labels.
     * @return                - the label sequence.
     */
    public ArrayList<Integer> getLabels(String operation) {
        LabelFactory labelFactory = LabelFactory.getInstance();
        ArrayList<Integer> labelSequence = new ArrayList<Integer>();
        while (operation.contains("(")) {
            String[] labelSplitted = operation.split("\\(", 2);
            String label = labelSplitted[1].split("\\)", 2)[0];
            int labelNumber = labelFactory.getNumberOfLabel(label);
            labelSequence.add(labelNumber);
            operation = labelSplitted[1];
        }
        return labelSequence;
    }

    @Override
    public ParseResult parseOperation(String operation, DataField data)
            throws AnalyzeException {
        throw new ParseException("Comparing on a single value is not possible");
    }

}
