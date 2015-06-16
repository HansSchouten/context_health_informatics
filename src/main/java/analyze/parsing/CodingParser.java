package analyze.parsing;

import analyze.AnalyzeException;
import analyze.labeling.Labeler;
import analyze.labeling.LabelingException;
import model.datafield.DataField;
import model.SequentialData;

/**
 * This class represents a coding parser, which parses the label construct.
 * @author Matthijs
 *
 */
public class CodingParser implements SubParser {

    /**
     * This variable stores a label.
     */
    protected String label;

    /**
     * This variable stores a condition.
     */
    protected String condition;

    /**
     * This variable stores a pattern.
     */
    protected String pattern;

    /**
     * Constructs a new coding parser.
     */
    public CodingParser() {
        label = null;
        condition = null;
        pattern = null;
    }

    @Override
    public SequentialData parseOperation(String operation, SequentialData data)
            throws AnalyzeException {

        translateOperation(operation);

        Labeler labeler = new Labeler();
        labeler.label(label, condition, pattern, data);
        return data;
    }

    /**
     * This method parses the operation to get a label and a condition.
     * @param operation             - Operation to parse.
     * @throws AnalyzeException     - Thrown when the operation cannot be parsed.
     */
    protected void translateOperation(String operation) throws AnalyzeException {

        if (operation == null || operation.isEmpty()) {
            throw new LabelingException("You are not giving any parameters to the operation.");
        }

        operation = operation.replaceAll("WITH ", "#1");
        operation = operation.replaceAll("with ", "#1");
        operation = operation.replaceAll("WHERE ", "#2");
        operation = operation.replaceAll("where ", "#2");
        operation = operation.replaceAll("AFTER PATTERN ", "#3");
        operation = operation.replaceAll("after pattern ", "#3");

        String[] parts = operation.split("#");

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];

            if (part.isEmpty()) {
                continue;
            } else if (part.charAt(0) == '1') {
                setLabel(part.substring(1));
            } else if (part.charAt(0) == '2') {
                setCondition(part.substring(1));
            } else if (part.charAt(0) == '3') {
                setPattern(part.substring(1));
            } else {
                throw new LabelingException("You are using a # in your code which is not allowed.");
            }
        }
    }

    /**
     * This method sets the label.
     * @param lbl                   - Part of the operation that sets the label.
     * @throws LabelingException    - Thrown when the label is already set.
     */
    protected void setLabel(String lbl) throws LabelingException {
        lbl = lbl.trim();

        if (label == null) {
            label = lbl;
        } else {
            throw new LabelingException("You have defined a label twice.");
        }
    }

    /**
     * This method sets the condition.
     * @param cnd                   - The condition to set
     * @throws LabelingException    - Thrown when a condition is already set.
     */
    protected void setCondition(String cnd) throws LabelingException {
        cnd = cnd.trim();
        if (condition == null) {
            condition = cnd;
        } else {
            throw new LabelingException("You have defined a condition twice.");
        }
    }

    /**
     * This method sets the pattern.
     * @param pttrn               - The pattern to find
     * @throws LabelingException  - Thrown when the pattern is already set
     */
    protected void setPattern(String pttrn) throws LabelingException {
        pttrn = pttrn.trim();
        if (pattern == null) {
            pattern = pttrn;
        } else {
            throw new LabelingException("You have defined a pattern twice.");
        }
    }

    @Override
    public ParseResult parseOperation(String operation, DataField data)
            throws AnalyzeException {
        throw new LabelingException("Labeling on a single value is not possible");
    }

}



