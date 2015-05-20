package analyze.labeling;

import java.util.Iterator;

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
     */
    public void label(String label, String condition, SequentialData data) {
        
        for (Iterator<Record> iterator = data.iterator(); iterator.hasNext();) {
            Record record = iterator.next();
            System.out.println("dosomething");
        }
    }
    //TODO: Test conditions with ""

}
