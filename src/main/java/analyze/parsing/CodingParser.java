package analyze.parsing;

import analyze.AnalyzeException;
import analyze.labeling.Labeler;
import analyze.labeling.LabelingException;
import model.SequentialData;

public class CodingParser implements SubParser {
    
    String label;
    String condition;

    @Override
    public SequentialData parseOperation(String operation, SequentialData data)
            throws AnalyzeException {

        parseOperation(operation);

        Labeler labeler = new Labeler();
        labeler.label(label, condition, data);
        return data;
    }

    /**
     * This method parses the operation to get a label and a condition.
     * @param operation             - Operation to parse.
     * @throws AnalyzeException     - Thrown when the operation cannot be parsed.
     */
    protected void parseOperation(String operation) throws AnalyzeException{
        
        label = null;
        condition = null;
        
        operation.replaceAll("WITH", "#1");
        operation.replaceAll("WHERE", "#2");
        
        String[] parts = operation.split("#");
        
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            
            if (part.charAt(0) == '1' ) {
                setLabel(part.substring(1));               
            }
            else if (part.charAt(0) == '2') {
                setCondition(part.substring(1));
            }
            else {
                throw new LabelingException("You are using an the # in your code which is not allowed.");
            }
        }
    }

    /**
     * This method sets the label.
     * @param lbl                   - Part of the operation that sets the label 
     * @throws LabelingException    - Thrown when the label is already set.
     */
    protected void setLabel(String lbl) throws LabelingException {
        lbl = lbl.trim();
        
        if ( lbl == null) {
            label = lbl;
        }
        else {
            throw new LabelingException("You have defined a label twice");
        }
    }

    /**
     * This method sets the condition.
     * @param cnd                   - The condition to set
     * @throws LabelingException    - Thrown when a condition is already set.
     */
    protected void setCondition(String cnd) throws LabelingException {
        cnd = cnd.trim();
        
        if ( cnd == null) {
            condition = cnd;
        }
        else {
            throw new LabelingException("You have defined a condition twice");
        }
    }
}
