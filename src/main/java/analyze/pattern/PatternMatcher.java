package analyze.pattern;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import analyze.labeling.Label;
import analyze.labeling.LabelFactory;
import model.Record;
import model.RecordList;
import model.SequentialData;

/**
 * This class represents a pattern matcher on the sequential data.
 * @author Hans Schouten
 *
 */
public class PatternMatcher {

    /**
     * Find all occurrences of the given pattern.
     * @param pattern                       - The string containing the pattern.
     * @param data                          - The data in which patterns need to be found.
     * @return                              - A list of all matches.
     * @throws PatternMatcherException      - Something went wrong while finding patterns.
     */
    public ArrayList<SequentialData> match(String pattern, SequentialData data) throws PatternMatcherException {
        ArrayList<Object> sequence = getSequence(pattern);
        ArrayList<SequentialData> matches = new ArrayList<SequentialData>();

        RecordList records = data.toRecordList();
        for (int i = 0; i < records.size(); i++) {
            RecordList match = findMatch(records.subList(i, records.size() - 1), sequence);
            if (match != null) {
                matches.add(match.toSequentialData());
            }
        }

        return matches;
    }

    /**
     * Find a match of the pattern that starts at the first record of the given list.
     * @param records           - The list of records.
     * @param sequence          - The sequence to find.
     * @return                  - The records that matches the matching pattern.
     */
    protected RecordList findMatch(List<Record> records, ArrayList<Object> sequence) {
        Iterator<Record> recordIterator = records.iterator();
        if (!recordIterator.hasNext()) {
            return null;
        }
        long lastDay = (recordIterator.next().getTimeStamp().toEpochSecond(ZoneOffset.UTC) / 86400) - 1;
        for (Object step : sequence) {
            
        }
        return null;
    }

    /**
     * Return the label sequence this comparison needs to look for.
     * @param operation                 - The string containing the labels.
     * @return                          - The label sequence.
     * @throws PatternMatcherException  - Something went wrong while reading the sequence.
     */
    protected ArrayList<Object> getSequence(String operation) throws PatternMatcherException {
        ArrayList<Object> sequence = new ArrayList<Object>();
        sequence.add(new Within(1));

        String[] parts = operation.split(" ");
        Object last = -1;
        for (String part : parts) {
            Label label = getLabel(part);
            Within within = getWithin(part);

            if (last instanceof Integer && label == null) {
                throw new PatternMatcherException("A pattern should start with a label.");
            }
            if (last instanceof Within && label == null) {
                throw new PatternMatcherException("After a WITHIN a new label should be specified.");
            }

            if (within != null) {
                sequence.add(within);
            }
            if (label != null) {
                sequence.add(label);
            }
            last = sequence.get(sequence.size() - 1);
        }
        if (!(last instanceof Label)) {
            throw new PatternMatcherException("A pattern should end with a label.");
        }

        return sequence;
    }

    protected Label getLabel(String operation) throws PatternMatcherException {
        if (operation.contains("LABEL(")) {
            String labelName = operation.split("LABEL\\(", 2)[1].replaceAll("\\)", "");
            return LabelFactory.getInstance().getNewLabel(labelName);
        }
        return null;
    }

    protected Within getWithin(String operation) throws PatternMatcherException {
        if (operation.contains("WITHIN(")) {
            String strValue = operation.split("WITHIN\\(", 2)[1].replaceAll("[^\\d]", "");
            try {
                return new Within(Integer.parseInt(strValue));
            } catch (Exception ex) {
                throw new PatternMatcherException("Invalid number of days in WITHIN specified.");
            }
        }
        return null;
    }

}
