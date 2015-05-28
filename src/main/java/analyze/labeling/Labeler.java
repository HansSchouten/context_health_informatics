package analyze.labeling;

import java.util.Iterator;

import analyze.AnalyzeException;
import analyze.EmptyDataSetException;
import analyze.condition.Condition;
import model.Record;
import model.SequentialData;

/**
 * This class should label the data where a given condition is true.
 * @author Matthijs
 *
 */
public class Labeler {

    /**
     * This variable stores the labelfactory for fast usage.
     */
    protected LabelFactory lf;

    /**
     * This class constructs a labeler and sets the labelfactory.
     */
    public Labeler() {
        lf = LabelFactory.getInstance();
    }

    /**
     * This method sets labels to the right records.
     * @param label         - String containing the label to set.
     * @param condition     - String containing the condition of when to set.
     * @param data          - The data to do the check on.
     * @throws AnalyzeException - thrown when the condition is invalid.
     */
    public void label(String label, String condition, SequentialData data) throws AnalyzeException {

        Condition con = new Condition(condition);
        int labelNumber = lf.getNewLabel(label).getNumber();

        if (data == null) {
            throw new EmptyDataSetException();
        }
        if (label == null || label.isEmpty()) {
            throw new LabelingException("Your label is not set");
        }

        for (Iterator<Record> iterator = data.iterator(); iterator.hasNext();) {
            Record record = iterator.next();
            if (con.evaluateWithRecord(record)) {
                record.addLabel(labelNumber);
            }
        }
    }
}
